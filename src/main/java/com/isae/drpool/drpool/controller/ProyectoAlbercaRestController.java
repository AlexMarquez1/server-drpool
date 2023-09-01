package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IProyectoAlbercaDAO;
import com.isae.drpool.drpool.entity.ProyectoAlberca;
import com.isae.drpool.drpool.entity.ProyectoSede;
import com.isae.drpool.drpool.entity.Sede;

@RestController
public class ProyectoAlbercaRestController {
	
	@Autowired
	private IProyectoAlbercaDAO proyectoalberca;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/proyectosalberca")
	public List<ProyectoAlberca> obtenerAlberca(){
		return this.proyectoalberca.findAll();
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/nuevo/proyectoalberca")
	public String nuevoProyectoAlberca(@RequestBody ProyectoAlberca proyectoAlberca) {
		String respuesta = "se guardo correctamente el proyecto";
		this.proyectoalberca.save(proyectoAlberca);
		return respuesta;
	}
	
	@CrossOrigin(origins="*")
	@GetMapping("/nuevo/prueba")
	public String nuevoProyectoAlberca() {
		String respuesta = "se guardo correctamente el proyecto";
		
		List<ProyectoSede> lista = new ArrayList();
		
		ProyectoAlberca proyectoAlberca = new ProyectoAlberca();
		proyectoAlberca.setIdproyectoalberca(0);
		proyectoAlberca.setNombreproyectoalberca("Nombre del proyecto");
		proyectoAlberca.setNumeroproyecto("10");
		proyectoAlberca.setTiposervicio("Tipo de servicio");
		proyectoAlberca.setFechafincontrato("12/12/12");
		proyectoAlberca.setFechainiciocontrato("11/11/11");
		proyectoAlberca.setEstatus("Estatus");
		
		lista.add(new ProyectoSede(0,new Sede(5),proyectoAlberca));
		
		proyectoAlberca.setProyectoSedes(lista);
		
		System.out.println(proyectoAlberca);
		this.proyectoalberca.save(proyectoAlberca);
		
		
		return respuesta;
	}
	

}
