package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ILocalidadesDAO;
import com.isae.drpool.drpool.entity.Localidades;


@RestController
public class LocalidadesRestController {
	
	@Autowired
	private ILocalidadesDAO localidades;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/localidades")
	public List<Localidades> getDatosProyectoBusqueda() {	
		return localidades.findAll();
	}
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/localidades/cp/{cp}")
	public List<Localidades> getDatosProyectoBusqueda(@PathVariable String cp) {
		System.out.println(cp);
		return localidades.obtenerCP(cp);
	}
	
	
	

}
