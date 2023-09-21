package com.isae.drpool.drpool.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAgrupacionesDAO;
import com.isae.drpool.drpool.dao.IAlbercaDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.IEquipobombaDAO;
import com.isae.drpool.drpool.dao.IEquipocalentamientoDAO;
import com.isae.drpool.drpool.dao.IEquipocontroladorDAO;
import com.isae.drpool.drpool.dao.IEquipodosificadorDAO;
import com.isae.drpool.drpool.dao.IEquipofiltradoDAO;
import com.isae.drpool.drpool.dao.IProyectoAlbercaDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Equipobomba;
import com.isae.drpool.drpool.entity.Equipocalentamiento;
import com.isae.drpool.drpool.entity.Equipocontrolador;
import com.isae.drpool.drpool.entity.Equipodosificador;
import com.isae.drpool.drpool.entity.Equipofiltrado;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.ProyectoAlberca;
import com.isae.drpool.drpool.entity.ProyectoSede;
import com.isae.drpool.drpool.entity.Sede;
import com.isae.drpool.drpool.entity.Tipoproyecto;

@RestController
public class ProyectoAlbercaRestController {

	@Autowired
	private IProyectoAlbercaDAO proyectoalberca;

	@Autowired
	private IAlbercaDAO alberca;

	@Autowired
	private IEquipobombaDAO equipobomba;

	@Autowired
	private IEquipocalentamientoDAO equipocalentamiento;

	@Autowired
	private IEquipocontroladorDAO equipocontrolador;

	@Autowired
	private IEquipodosificadorDAO equipodosificador;

	@Autowired
	private IEquipofiltradoDAO equipofiltrado;

	@Autowired
	private ICamposProyectoDAO camposproyecto;
	
	@Autowired
	private IProyectoDAO proyecto;
	
	@Autowired
	private IAgrupacionesDAO agrupacion;

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/proyectosalberca")
	public List<ProyectoAlberca> obtenerAlberca() {
		return this.proyectoalberca.findAll();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/proyectoalberca")
	public String nuevoProyectoAlberca(@RequestBody ProyectoAlberca proyectoAlberca) {
		String respuesta = "se guardo correctamente el proyecto";

		List<Alberca> listaAlbercas = new ArrayList<>();
		List<Equipobomba> listaEquiposBomba = new ArrayList<>();
		List<Equipocalentamiento> listaEquiposCalentamiento = new ArrayList<>();
		List<Equipocontrolador> listaEquiposControlador = new ArrayList<>();
		List<Equipodosificador> listaEquiposDosificador = new ArrayList<>();
		List<Equipofiltrado> listaEquiposFiltrado = new ArrayList<>();
		boolean equipoCompleto = true;
		List<String> albercaSinEquipo = new ArrayList<>();
		List<String> sedeSinAlberca = new ArrayList<>();
		Map<String, Object> albercaEquipos = new HashMap<String, Object>();
		List<Proyecto> listaProyectosAGenerar = new ArrayList<Proyecto>();

		List<ProyectoSede> lista = new ArrayList<>();
		for (int i = 0; i < proyectoAlberca.getProyectoSedes().size(); i++) {
			lista.add(new ProyectoSede(0, proyectoAlberca.getProyectoSedes().get(i).getSede(), proyectoAlberca));
			listaAlbercas.addAll(this.alberca
					.obtenerAlbercaPorSede(proyectoAlberca.getProyectoSedes().get(i).getSede().getIdsede()));
			if (listaAlbercas.isEmpty()) {
				sedeSinAlberca.add(proyectoAlberca.getProyectoSedes().get(i).getSede().getNombre());
			}
		}
		proyectoAlberca.setProyectoSedes(lista);
		System.out.println(proyectoAlberca);
		System.out.println(listaAlbercas);
		if (listaAlbercas.isEmpty()) {
			System.out.print("No hay albercas relacionadas");
			respuesta = "No hay albercas relacionadas," + sedeSinAlberca.toString();
		} else {

			for (Alberca alberca : listaAlbercas) {
				listaEquiposBomba.addAll(this.equipobomba.obtenerEquipoBombaPorAlberca(alberca.getIdalberca()));
				listaEquiposCalentamiento
						.addAll(this.equipocalentamiento.obtenerEquipoCalentamientoPorAlberca(alberca.getIdalberca()));
				listaEquiposControlador
						.addAll(this.equipocontrolador.obtenerEquipoControladorPorAlberca(alberca.getIdalberca()));
				listaEquiposDosificador
						.addAll(this.equipodosificador.obtenerEquipoDosificadorPorAlberca(alberca.getIdalberca()));
				listaEquiposFiltrado
						.addAll(this.equipofiltrado.obtenerEquipoFiltradoPorAlberca(alberca.getIdalberca()));

				if (listaEquiposBomba.isEmpty() || listaEquiposCalentamiento.isEmpty()
						|| listaEquiposControlador.isEmpty() || listaEquiposDosificador.isEmpty()
						|| listaEquiposFiltrado.isEmpty()) {
					equipoCompleto = false;
					albercaSinEquipo.add(alberca.getNombrealberca());
				} else {
					List<Map<String, Object>> listaEquipos = new ArrayList<>();
					Map<String, Object> equipo = new HashMap<String, Object>();
					equipo.put("Calentamiento", listaEquiposCalentamiento);
					equipo.put("Controlador", listaEquiposControlador);
					equipo.put("Dosificador", listaEquiposDosificador);
					equipo.put("Filtrado", listaEquiposFiltrado);

					listaEquipos.add(equipo);
					albercaEquipos.put(alberca.getNombrealberca(), listaEquipos);
				}
				listaProyectosAGenerar.add(new Proyecto(0,new Date(),"BITACORA DIARIA "+ alberca.getNombrealberca(),"0",new Tipoproyecto(8, ""),alberca, "TRUE"));
			}

			if (equipoCompleto) {

				// Crear formularios para cada una de las albercas
				
				for (Proyecto proyectoNuevo : listaProyectosAGenerar) {
					List<Camposproyecto> camposBitacoraDiaria = new ArrayList<Camposproyecto>();
					Proyecto proyecto = this.proyecto.save(proyectoNuevo);
					camposBitacoraDiaria = bitacoraDiaria(proyecto, listaEquiposCalentamiento, listaEquiposControlador, listaEquiposDosificador, listaEquiposBomba, listaEquiposFiltrado);
					System.out.println(camposBitacoraDiaria.size());
					this.camposproyecto.saveAll(camposBitacoraDiaria);
				}
				

				// Elimina las sedes relacionadas al proyecto y vuelve a guardar para actualizar
				// el proyecto
				this.proyectoalberca.eliminarSedePorProyecto(proyectoAlberca.getIdproyectoalberca());
				this.proyectoalberca.save(proyectoAlberca);
			} else {
				respuesta = "No hay algun equipo relacionado con la alberca:" + albercaSinEquipo.toString();
			}

		}
		return respuesta;
	}

	private List<Camposproyecto> bitacoraDiaria(Proyecto proyecto, List<Equipocalentamiento> listaEquiposCalentamiento,
			List<Equipocontrolador> listaEquiposControlador, List<Equipodosificador> listaEquiposDosificador,List<Equipobomba> listaEquiposBomba,
			List<Equipofiltrado> listaEquiposFiltrado) {

		List<Camposproyecto> camposBitacora = new ArrayList<>(this.camposproyecto.obtenerCatalogoCampoPorProyecto(231));
		List<Camposproyecto> respuesta = new ArrayList<Camposproyecto>();
		

		List<Camposproyecto> camposBitacoraGenerada = new ArrayList<Camposproyecto>();
		camposBitacoraGenerada.addAll(camposBitacora.subList(0, 15));
		
		camposBitacoraGenerada.addAll(agregarEquipos(1,152,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto));
		camposBitacoraGenerada.addAll(camposBitacora.subList(23, 38));
		camposBitacoraGenerada.addAll(agregarEquipos(2,135,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto));
		camposBitacoraGenerada.addAll(camposBitacora.subList(46, 61));
		camposBitacoraGenerada.addAll(agregarEquipos(3,139,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto));
		camposBitacoraGenerada.addAll(camposBitacora.subList(69, 84));
		camposBitacoraGenerada.addAll(agregarEquipos(4,143,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto));
		camposBitacoraGenerada.addAll(camposBitacora.subList(92, 107));
		camposBitacoraGenerada.addAll(agregarEquipos(5,147,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto));
		camposBitacoraGenerada.addAll(camposBitacora.subList(114, 122));
		
		for (Camposproyecto camposproyecto : camposBitacoraGenerada) {
			Camposproyecto campoAux = new Camposproyecto();
			campoAux.setIdcamposproyecto(0);
			campoAux.setAgrupacion(camposproyecto.getAgrupacion());
			campoAux.setAlerta(camposproyecto.getAlerta());
			campoAux.setCampo(camposproyecto.getCampo());
			campoAux.setEditable(camposproyecto.getEditable());
			campoAux.setLongitud(camposproyecto.getLongitud());
			campoAux.setPattern(camposproyecto.getPattern());
			campoAux.setPordefecto(camposproyecto.getPordefecto());
			campoAux.setProyecto(proyecto);
			campoAux.setTipocampo(camposproyecto.getTipocampo());
			campoAux.setValidarduplicidad(camposproyecto.getValidarduplicidad());
			respuesta.add(campoAux);
			System.out.println(campoAux.getIdcamposproyecto() + ": " + campoAux.getCampo());
		}

		return respuesta;
	}
	
	private List<Camposproyecto> agregarEquipos(int numMuestra, int idAgrupacion, List<Equipocalentamiento> listaEquiposCalentamiento,
			List<Equipocontrolador> listaEquiposControlador, List<Equipodosificador> listaEquiposDosificador,List<Equipobomba> listaEquiposBomba,
			List<Equipofiltrado> listaEquiposFiltrado, Proyecto proyecto) {
		List<Camposproyecto> campos = new ArrayList<Camposproyecto>();
		Agrupacion agrupacion = this.agrupacion.findById(idAgrupacion).get();
		int i =1;
		for (Equipofiltrado filtro : listaEquiposFiltrado) {
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra +" FILTRO "+ i++ +" (PSI)","FALSE","TRUE",10,"CHECKBOX","[N/A]", agrupacion,proyecto,""));
		}
		i=1;
		for (Equipocalentamiento caldera : listaEquiposCalentamiento) {
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA "+ numMuestra +" CALDERA "+ i++ +" (°C)","FALSE","TRUE",10,"CHECKBOX","[N/A]",agrupacion,proyecto,""));
		}
		i=1;
		for (Equipobomba bomba : listaEquiposBomba) {
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra + " MOTOBOMBA "+ i++,"FALSE","TRUE",10,"CHECKBOX","[N/A]", agrupacion,proyecto,""));
		}
		i=1;
		for (Equipocontrolador controlador : listaEquiposControlador) {
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA "+ numMuestra + " CONTROLADOR "+ i++,"FALSE","TRUE",10,"CHECKBOX","[N/A]", agrupacion,proyecto,""));
		}
		i=1;
		for (Equipodosificador dosificador : listaEquiposDosificador) {
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra + " DOSIFICADOR "+ i++,"FALSE","TRUE",10,"CHECKBOX","[N/A]", agrupacion,proyecto,""));
		}
		return campos;
	}
}
