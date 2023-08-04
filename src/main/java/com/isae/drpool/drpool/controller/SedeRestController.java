package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ISedeDAO;
import com.isae.drpool.drpool.entity.Sede;

@RestController
public class SedeRestController {
	
	@Autowired
	private ISedeDAO sede;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/sedes")
	public List<Sede> obtenerSede(){
		return this.sede.findAll();
	}
}
