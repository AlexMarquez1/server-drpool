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

import com.isae.drpool.drpool.dao.IHistorialCambiosDAO;
import com.isae.drpool.drpool.entity.HistorialCambios;
import com.isae.drpool.drpool.entity.Inventario;

@RestController
public class HistorialCambiosController {
	
	@Autowired
	private IHistorialCambiosDAO historialCambios;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/todos/cambios")
	public List<HistorialCambios> obtenerTodoHistorialCambios() {
		
		return this.historialCambios.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/historial/registro")
	public List<HistorialCambios> ObtenerHistorialPorInventario(@RequestBody Inventario inventario) {
		List<HistorialCambios> lista = this.historialCambios.obtenerHistorialPorInventario(inventario);
		return lista;
	}

}
