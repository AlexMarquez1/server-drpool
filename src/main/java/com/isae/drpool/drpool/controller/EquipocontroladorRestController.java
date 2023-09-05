package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEquipocontroladorDAO;
import com.isae.drpool.drpool.entity.Equipocontrolador;


@RestController
public class EquipocontroladorRestController {

	@Autowired
	private IEquipocontroladorDAO equipocontrolador; 
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/equipocontrolador")
	public List<Equipocontrolador> obtenerEquipo(){
		return this.equipocontrolador.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/equipocontrolador")
	public String nuevoEquipocontrolador(@RequestBody Equipocontrolador equipocontrolador) {
		try {
			this.equipocontrolador.save(equipocontrolador);
			return "se guardo";
		}catch(Exception e) {
			return "insertar controlador" + e.toString();
		}
		
	}
}
