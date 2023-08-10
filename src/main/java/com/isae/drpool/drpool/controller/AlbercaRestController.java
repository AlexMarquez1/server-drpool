package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAlbercaDAO;
import com.isae.drpool.drpool.entity.Alberca;



@RestController
public class AlbercaRestController {

	@Autowired
	private IAlbercaDAO alberca;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/albercas")
	public List<Alberca> obtenerAlberca(){
		return this.alberca.findAll();
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/nueva/alberca")
	public String nuevaAlberca(@RequestBody Alberca alberca) {
		String respuesta = "se guardo correctamente la alberca";
		this.alberca.save(alberca);
		return respuesta;
	}
}
