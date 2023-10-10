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
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.IEquipobombaDAO;
import com.isae.drpool.drpool.dao.IEquipocalentamientoDAO;
import com.isae.drpool.drpool.dao.IEquipocontroladorDAO;
import com.isae.drpool.drpool.dao.IEquipodosificadorDAO;
import com.isae.drpool.drpool.dao.IEquipofiltradoDAO;
import com.isae.drpool.drpool.dao.IProyectoAlbercaDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.IProyectoSedeDAO;
import com.isae.drpool.drpool.dao.ISedeDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Asignacionproyecto;
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
import com.isae.drpool.drpool.entity.Usuario;

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
	
	@Autowired
	private IAsignacionProyectoDAO asignacionProyecto;
	
	@Autowired
	private IProyectoSedeDAO proyectoSede;
	
	@Autowired
	private ISedeDAO sede; 
	
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
		Map<String,Usuario> proyectoCoordinador = new HashMap<String,Usuario>();
		Map<String,Usuario> proyectoOperador = new HashMap<String,Usuario>();
		List<Proyecto> listaProyectosAGenerarBitacora = new ArrayList<Proyecto>();
		List<Proyecto> listaProyectosAGenerarSemanal = new ArrayList<Proyecto>();

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
				
				//Se cambia el nombre del proyecto de la bitacora diaria y reporte semanal a sus respectivos nombres con el nombre del proyecto alberca generado
				
				//Sede se = this.sede.getById(alberca.getSede().getIdsede());
				
				//ProyectoSede prosede = prosede.
						
				//System.out.println("Proyecto Sede: " + prosede);
				
				//royectoAlberca proyectoalberca; //= this.proyectoalberca.getById();
				
				//proyectoalberca.getProyectoSedes();
				
				
				//System.out.println("Nombre del proyecto alberca: " + proyectoalberca.getNombreproyectoalberca());
				String nombreProyectoBitacora = "BITACORA DIARIA,"+ proyectoAlberca.getNombreproyectoalberca() + "," + alberca.getNombrealberca(); //+ "-" + alberca.getNombrealberca()
				System.out.println("Nombre del proyecto alberca: " + proyectoAlberca.getNombreproyectoalberca());
				String nombreProyectoSemanal = "REPORTE SEMANAL,"+ proyectoAlberca.getNombreproyectoalberca() + "," + alberca.getNombrealberca();
				listaProyectosAGenerarBitacora.add(new Proyecto(0,new Date(),nombreProyectoBitacora,"0",new Tipoproyecto(8, ""),alberca, "TRUE"));
				listaProyectosAGenerarSemanal.add(new Proyecto(0,new Date(),nombreProyectoSemanal,"0",new Tipoproyecto(8, ""),alberca, "TRUE"));
				proyectoCoordinador.put(nombreProyectoBitacora, alberca.getSede().getCoordinador());
				proyectoCoordinador.put(nombreProyectoSemanal, alberca.getSede().getCoordinador());
				proyectoOperador.put(nombreProyectoBitacora, alberca.getSede().getOperador());
			}
			
			System.out.println("Alberca a crear: " + alberca);
			
			System.out.println("Equipo filtrado: " + listaEquiposFiltrado);

			if (equipoCompleto) {

				// Crear formularios para cada una de las albercas
				
				for (Proyecto proyectoNuevo : listaProyectosAGenerarBitacora) {
					List<Camposproyecto> camposBitacoraDiaria = new ArrayList<Camposproyecto>();
					Proyecto proyecto = this.proyecto.save(proyectoNuevo);
					this.asignacionProyecto.save(new Asignacionproyecto(0, proyectoOperador.get(proyecto.getProyecto()), proyecto));
					this.asignacionProyecto.save(new Asignacionproyecto(0, proyectoCoordinador.get(proyecto.getProyecto()), proyecto));
					camposBitacoraDiaria = bitacoraDiaria(proyecto, listaEquiposCalentamiento, listaEquiposControlador, listaEquiposDosificador, listaEquiposBomba, listaEquiposFiltrado, proyectoNuevo.getAlberca().getIdalberca());
					//System.out.println("Campos de bitacora diaria: " + camposBitacoraDiaria);
					this.camposproyecto.saveAll(camposBitacoraDiaria);
				}
				
				for (Proyecto proyectoNuevo : listaProyectosAGenerarSemanal) {
					List<Camposproyecto> camposBitacoraDiaria = new ArrayList<Camposproyecto>();
					Proyecto proyecto = this.proyecto.save(proyectoNuevo);
					this.asignacionProyecto.save(new Asignacionproyecto(0, proyectoCoordinador.get(proyecto.getProyecto()), proyecto));
					camposBitacoraDiaria = reporteSemanal(proyecto);
					//System.out.println("Campos de bitacora diaria: " + camposBitacoraDiaria);
					this.camposproyecto.saveAll(camposBitacoraDiaria);
				}
				

				// Elimina las sedes relacionadas al proyecto y vuelve a guardar para actualizar
				// el proyecto
				
				this.proyectoalberca.eliminarSedePorProyecto(proyectoAlberca.getIdproyectoalberca());
				this.proyectoalberca.save(proyectoAlberca);
				//System.out.println("proyecto alberca: " + proyectoAlberca);
			} else {
				respuesta = "No hay algun equipo relacionado con la alberca:" + albercaSinEquipo.toString();
			}

		}
		return respuesta;
	}
	
	private List<Camposproyecto> reporteSemanal(Proyecto proyecto){
		
		List<Camposproyecto> camposBitacora = new ArrayList<>(this.camposproyecto.obtenerCatalogoCampoPorProyecto(232));
		List<Camposproyecto> respuesta = new ArrayList<Camposproyecto>();
		
		for (Camposproyecto camposproyecto : camposBitacora) {
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
		}
		
		return respuesta;
	}

	private List<Camposproyecto> bitacoraDiaria(Proyecto proyecto, List<Equipocalentamiento> listaEquiposCalentamiento,
			List<Equipocontrolador> listaEquiposControlador, List<Equipodosificador> listaEquiposDosificador,List<Equipobomba> listaEquiposBomba,
			List<Equipofiltrado> listaEquiposFiltrado, int idAlberca) {

		List<Camposproyecto> camposBitacora = new ArrayList<>(this.camposproyecto.obtenerCatalogoCampoPorProyecto(231));
		List<Camposproyecto> respuesta = new ArrayList<Camposproyecto>();
		

		List<Camposproyecto> camposBitacoraGenerada = new ArrayList<Camposproyecto>();
		camposBitacoraGenerada.addAll(camposBitacora.subList(0, 16));
		
		camposBitacoraGenerada.addAll(agregarEquipos(1,152,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto, idAlberca));
		camposBitacoraGenerada.addAll(camposBitacora.subList(23, 39));
		camposBitacoraGenerada.addAll(agregarEquipos(2,135,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto, idAlberca));
		camposBitacoraGenerada.addAll(camposBitacora.subList(46, 62));
		camposBitacoraGenerada.addAll(agregarEquipos(3,139,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto, idAlberca));
		camposBitacoraGenerada.addAll(camposBitacora.subList(69, 85));
		camposBitacoraGenerada.addAll(agregarEquipos(4,143,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto, idAlberca));
		camposBitacoraGenerada.addAll(camposBitacora.subList(92, 108));
		camposBitacoraGenerada.addAll(agregarEquipos(5,147,listaEquiposCalentamiento,
				listaEquiposControlador, listaEquiposDosificador,listaEquiposBomba,
				 listaEquiposFiltrado, proyecto, idAlberca));
		camposBitacoraGenerada.addAll(camposBitacora.subList(114, 123));
		
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
		}

		return respuesta;
	}
	
	private List<Camposproyecto> agregarEquipos(int numMuestra, int idAgrupacion, List<Equipocalentamiento> listaEquiposCalentamiento,
			List<Equipocontrolador> listaEquiposControlador, List<Equipodosificador> listaEquiposDosificador,List<Equipobomba> listaEquiposBomba,
			List<Equipofiltrado> listaEquiposFiltrado, Proyecto proyecto, int idAlberca) {
		List<Camposproyecto> campos = new ArrayList<Camposproyecto>();
		Agrupacion agrupacion = this.agrupacion.findById(idAgrupacion).get();
		int i =1;
		for (Equipofiltrado filtro : listaEquiposFiltrado) {
			if(filtro.getAlberca().getIdalberca() == idAlberca) { 
			campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra +" FILTRO "+ i++ +" (PSI)","FALSE","TRUE",10,"[N/A]","CHECKBOX", agrupacion,proyecto,""));
			System.out.println("Campo de filtro: " + idAlberca + "filtros" + filtro);
			}
		}
		i=1;
		for (Equipocalentamiento caldera : listaEquiposCalentamiento) {
			if(caldera.getAlberca().getIdalberca() == idAlberca) campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA "+ numMuestra +" CALDERA "+ i++ +" (°C)","FALSE","TRUE",10,"[N/A]","CHECKBOX",agrupacion,proyecto,""));
		}
		i=1;
		for (Equipobomba bomba : listaEquiposBomba) {
			if(bomba.getAlberca().getIdalberca() == idAlberca) campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra + " MOTOBOMBA "+ i++,"FALSE","TRUE",10,"[N/A]","CHECKBOX", agrupacion,proyecto,""));
		}
		i=1;
		for (Equipocontrolador controlador : listaEquiposControlador) {
			if(controlador.getAlberca().getIdalberca() == idAlberca) campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA "+ numMuestra + " CONTROLADOR "+ i++,"FALSE","TRUE",10,"[N/A]","CHECKBOX", agrupacion,proyecto,""));
		}
		i=1;
		for (Equipodosificador dosificador : listaEquiposDosificador) {
			if(dosificador.getAlberca().getIdalberca() == idAlberca) campos.add(new Camposproyecto(0,"INSERTA LOS DATOS SOLICITADOS EN EL CAMPO","TOMA DE MUESTRA " + numMuestra + " DOSIFICADOR "+ i++,"FALSE","TRUE",10,"[N/A]","CHECKBOX", agrupacion,proyecto,""));
		}
		return campos;
		
	}
}
