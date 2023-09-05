package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEquipocalentamientoDAO;
import com.isae.drpool.drpool.entity.Equipocalentamiento;

@RestController
public class EquipocalentamientoRestController {
	
	@Autowired
	private IEquipocalentamientoDAO equipocalentamiento; 
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/equipocalentamiento")
	public List<Equipocalentamiento> obtenerEquipo(){
		return this.equipocalentamiento.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/equipocalentamiento")
	public String nuevoEquipocalentamiento(@RequestBody Equipocalentamiento equipocalentamiento) {
		try {
			this.equipocalentamiento.save(equipocalentamiento);
			return "Se guardo";
		}catch(Exception e) {
			return "insertar filtro" + e.toString();
		}
	}

}
