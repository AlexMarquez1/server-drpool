package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.utils.ObtenerDatos;


@RestController
//@RequestMapping("/datosduplicados")
public class DatosDuplicadosController {

	@Autowired
	private IValoresDAO valores;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/duplicados/proyecto/{idproyecto}")
	public Map<String, Object> obtenerDuplicadosPorProyecto(@PathVariable(value = "idproyecto") String idproyecto) {
		List<Object> respuesta = new ArrayList<Object>();
		
		respuesta = this.valores.obtenerDuplicadosPorProyecto(Integer.parseInt(idproyecto));
		Map<String, Object> datos = new HashMap<String, Object>();
		
		for(Object item : respuesta) {
			Object[] aux = (Object[]) item;
			datos.put((String) aux[2], aux);
		}
		
		
		return datos;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/registros/duplicados/proyecto/{idproyecto}")
	public List<List<Agrupaciones>> obtenerRegistrosDuplicadosPorProyecto(@PathVariable(value = "idproyecto") String idproyecto, @RequestBody String dato) {
		List<List<Agrupaciones>> respuesta = new ArrayList<List<Agrupaciones>>();
		System.out.println("Dato: " + dato);
		List<Valore> repetidos = this.valores.obtenerValoresDuplicadosPorProyecto(dato, Integer.parseInt(idproyecto));
		ObtenerDatos obtenerDatos = new ObtenerDatos();
		for(Valore item : repetidos) {
			respuesta.add(obtenerDatos.getDatosRegistro(String.valueOf(item.getInventario().getIdinventario()), idproyecto, this.valores));
		}
		return respuesta;
	}
	
	
}
