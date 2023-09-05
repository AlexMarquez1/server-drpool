package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEquipofiltradoDAO;
import com.isae.drpool.drpool.entity.Equipofiltrado;

@RestController
public class EquipofiltradoRestController {
	
	@Autowired
	private IEquipofiltradoDAO equipofiltrado; 
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/equipofiltrado")
	public List<Equipofiltrado> obtenerEquipo(){
		return this.equipofiltrado.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/equipofiltrado")
	public String nuevoEquipofiltrado(@RequestBody Equipofiltrado equipofiltrado) {
		try {
			this.equipofiltrado.save(equipofiltrado);
			return "se guardo";
		}catch(Exception e) {
			return "insertar filtro" + e.toString();
		}
	}
	

}
