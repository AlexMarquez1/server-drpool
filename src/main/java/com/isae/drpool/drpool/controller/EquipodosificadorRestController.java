package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEquipodosificadorDAO;
import com.isae.drpool.drpool.entity.Equipodosificador;

@RestController
public class EquipodosificadorRestController {
	
	@Autowired
	private IEquipodosificadorDAO equipodosificador; 
	
	@CrossOrigin(origins ="*")
	@GetMapping("/obtener/equipodosificador")
	public List<Equipodosificador> obtenerEquipo(){
		return this.equipodosificador.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/equipodosificador")
	public String nuevoEquipodosificador(@RequestBody Equipodosificador equipodosificador) {
		try {
			this.equipodosificador.save(equipodosificador);
			return "Se guardo";
		}catch(Exception e) {
			return "insertar dosificador" + e.toString();
		}
	}

}
