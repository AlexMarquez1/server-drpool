package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.isae.drpool.drpool.dao.IAsignacionClienteDAO;
import com.isae.drpool.drpool.dao.IClienteDAO;
import com.isae.drpool.drpool.entity.Cliente;
import com.isae.drpool.drpool.entity.Proyecto;

@RestController
public class ClienteRestController {

	@Autowired
	private IClienteDAO cliente;
	
	@Autowired
	private IAsignacionClienteDAO asignacionCliente;
	
	@CrossOrigin(origins="*")
	@GetMapping("/obtener/clientes")
	public List<Cliente> obtenerClientes() {
		return this.cliente.findAll();
	}
	
	@CrossOrigin(origins="*")
	@GetMapping("/obtener/clientes/usuario/{idclienteaplicacion}")
	public List<Cliente> obtenerClientesPoUsuario(@PathVariable("idclienteaplicacion") String idClienteAplicacion) {
		
		return this.cliente.obtenerClientesPorUsuario(Integer.parseInt(idClienteAplicacion));
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/obtener/proyectos/cliente")
	public List<Proyecto> obtenerProyectosPorCliente(@RequestBody Cliente cliente) {
		
		return this.asignacionCliente.obtenerProyectosPorCliente(cliente);
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/nuevo/cliente")
	public String nuevoCliente(@RequestBody Map<String, Object> cliente) {
		String respuesta = "errorGuardar";
		Gson gson = new Gson();
		String json = gson.toJson(cliente.get("cliente"));
		
		Cliente nuevoCliente = gson.fromJson(json, new TypeToken<Cliente>() {}.getType());
		json = gson.toJson(cliente.get("imagen"));
		
		List<Integer> listaBites = gson.fromJson(json, new TypeToken<List<Integer>>() {}.getType());
		
		System.out.println(listaBites);
		return respuesta;
	}
}
