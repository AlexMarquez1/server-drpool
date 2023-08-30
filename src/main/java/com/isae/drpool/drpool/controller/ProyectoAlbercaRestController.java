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
	

}
