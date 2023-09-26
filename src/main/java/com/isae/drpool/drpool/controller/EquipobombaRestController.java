package com.isae.drpool.drpool.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEquipobombaDAO;
import com.isae.drpool.drpool.entity.Equipobomba;


@RestController
public class EquipobombaRestController {
	
	@Autowired
	private IEquipobombaDAO equipobomba;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/equipobomba")
	public List<Equipobomba> obtenerEquipo(){
		return this.equipobomba.findAll();
	}
	
	@CrossOrigin(origins ="*")
	@PostMapping("/nuevo/equipobomba")
	public String nuevoEquipobomba(@RequestBody Equipobomba equipobomba) {
			this.equipobomba.save(equipobomba);
			return "Se guardo"; 
	}

}
