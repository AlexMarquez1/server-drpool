package com.isae.drpool.drpool.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAlbercaDAO;
import com.isae.drpool.drpool.dao.IEquipobombaDAO;
import com.isae.drpool.drpool.dao.IEquipocalentamientoDAO;
import com.isae.drpool.drpool.dao.IEquipocontroladorDAO;
import com.isae.drpool.drpool.dao.IEquipodosificadorDAO;
import com.isae.drpool.drpool.dao.IEquipofiltradoDAO;
import com.isae.drpool.drpool.dao.IProyectoAlbercaDAO;
import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Equipobomba;
import com.isae.drpool.drpool.entity.Equipocalentamiento;
import com.isae.drpool.drpool.entity.Equipocontrolador;
import com.isae.drpool.drpool.entity.Equipodosificador;
import com.isae.drpool.drpool.entity.Equipofiltrado;
import com.isae.drpool.drpool.entity.ProyectoAlberca;
import com.isae.drpool.drpool.entity.ProyectoSede;
import com.isae.drpool.drpool.entity.Sede;

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
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/proyectosalberca")
	public List<ProyectoAlberca> obtenerAlberca(){
		return this.proyectoalberca.findAll();
	}
	
	@CrossOrigin(origins="*")
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
 		
		
		List<ProyectoSede> lista = new ArrayList<>();
		for(int i =0; i< proyectoAlberca.getProyectoSedes().size(); i++ ) {
			lista.add(new ProyectoSede(0,proyectoAlberca.getProyectoSedes().get(i).getSede(), proyectoAlberca));
			listaAlbercas.addAll(this.alberca.obtenerAlbercaPorSede(proyectoAlberca.getProyectoSedes().get(i).getSede().getIdsede()));
			if (listaAlbercas.isEmpty() ){
				sedeSinAlberca.add(proyectoAlberca.getProyectoSedes().get(i).getSede().getNombre());
			}
		}
		proyectoAlberca.setProyectoSedes(lista);
		System.out.println(proyectoAlberca);
		System.out.println(listaAlbercas);
		if (listaAlbercas.isEmpty() ) {
			System.out.print("No hay albercas relacionadas");
			respuesta = "No hay albercas relacionadas," +sedeSinAlberca.toString();
		}else {
		
			for (Alberca alberca : listaAlbercas) {
				listaEquiposBomba.addAll(this.equipobomba.obtenerEquipoBombaPorAlberca(alberca.getIdalberca()));
				listaEquiposCalentamiento.addAll(this.equipocalentamiento.obtenerEquipoCalentamientoPorAlberca(alberca.getIdalberca()));
				listaEquiposControlador.addAll(this.equipocontrolador.obtenerEquipoControladorPorAlberca(alberca.getIdalberca()));
				listaEquiposDosificador.addAll(this.equipodosificador.obtenerEquipoDosificadorPorAlberca(alberca.getIdalberca()));
				listaEquiposFiltrado.addAll(this.equipofiltrado.obtenerEquipoFiltradoPorAlberca(alberca.getIdalberca()));
				if (listaEquiposBomba.isEmpty() || 
					listaEquiposCalentamiento.isEmpty() || 
					listaEquiposControlador.isEmpty() || 
					listaEquiposDosificador.isEmpty() || 
					listaEquiposFiltrado.isEmpty()){
					
					
					
					equipoCompleto = false;
					albercaSinEquipo.add(alberca.getNombrealberca());
					
				}
			}
			
			if (equipoCompleto) {
				this.proyectoalberca.eliminarSedePorProyecto(proyectoAlberca.getIdproyectoalberca());
				
				this.proyectoalberca.save(proyectoAlberca);
			}else {
				respuesta = "No hay algun equipo relacionado con la alberca:"+albercaSinEquipo.toString();
			}
				
		}	
		return respuesta;
	}
}
