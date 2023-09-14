package com.isae.drpool.drpool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IDireccionSedeDAO;
import com.isae.drpool.drpool.dao.ISedeDAO;
import com.isae.drpool.drpool.entity.Sede;

@RestController
public class SedeRestController {
	
	@Autowired
	private ISedeDAO sede;
	@Autowired
	private IDireccionSedeDAO direccion; 
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/sedes")
	public List<Sede> obtenerSede(){
		return this.sede.findAll();
	} 
	
	@CrossOrigin(origins="*")
	@PostMapping("/nueva/sede")
	public String nuevaSede(@RequestBody Sede sede) {
		String respuesta = "se guardo correctamente";
		System.out.println(sede);
		
		if(sede.getDireccion().getIddireccion() != 0) {
			this.direccion.save(sede.getDireccion());
		}
		
		this.sede.save(sede);
		return respuesta;
	}
}


