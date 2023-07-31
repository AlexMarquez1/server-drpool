package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ILocalidadDAO;
import com.isae.drpool.drpool.entity.ClienteAplicacion;
import com.isae.drpool.drpool.entity.Localidad;

@RestController
public class LocalidadRestController {
	
	@Autowired
	private ILocalidadDAO localidad;
	
	@CrossOrigin("*")
	@PostMapping("/obtener/localidades/cliente/aplicacion")
	public List<Localidad> obtenerLocalidades(@RequestBody ClienteAplicacion cliente){
		
		List<Localidad> respuesta = new ArrayList<Localidad>();
		
		respuesta = this.localidad.obtenerLocalidadPorCliente(cliente);
		
		return respuesta;
	}

}
