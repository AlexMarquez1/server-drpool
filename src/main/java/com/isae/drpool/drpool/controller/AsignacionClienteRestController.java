package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAsignacionClienteDAO;
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.entity.AsignacionCliente;


@RestController
public class AsignacionClienteRestController {
	
	@Autowired
	private IAsignacionClienteDAO asignacionCliente;
	
	@Autowired
	private IAsignacionProyectoDAO asignacionProyecto;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/asignaciones/cliente")
	public List<AsignacionCliente> obteneAsignacionCliente(){
		return this.asignacionCliente.findAll();
	}
	

}
