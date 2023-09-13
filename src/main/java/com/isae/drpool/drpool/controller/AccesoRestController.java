package com.isae.drpool.drpool.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAccesoDAO;
import com.isae.drpool.drpool.entity.Acceso;

@RestController
public class AccesoRestController {
	
	@Autowired
	private IAccesoDAO acceso; 
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/pass")
	public String obtenerPass() {
		return this.acceso.findById(1).get().getPass();
	}
}
