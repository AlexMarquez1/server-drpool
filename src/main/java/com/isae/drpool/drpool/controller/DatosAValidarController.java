package com.isae.drpool.drpool.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IDatosAValidarDAO;
import com.isae.drpool.drpool.entity.DatosAValidar;


@RestController
//@RequestMapping("/datosavalidar")
public class DatosAValidarController {
	
	@Autowired
	private IDatosAValidarDAO datosAValidar;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datosvalidar/pendiente")
	public List<DatosAValidar> obtenerDatosAValidarPendiente(){
		List<DatosAValidar> respuesta = this.datosAValidar.obtenerDatosAValidarPendientes();
		return respuesta;
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datosvalidar/asignados")
	public List<DatosAValidar> obtenerDatosAValidarAsignado(){
		List<DatosAValidar> respuesta = this.datosAValidar.obtenerDatosAValidarAsignados();
		
		return respuesta;
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/total/datosavalidar/{tipodedato}")
	public Integer obtenerTotalDatosAValidar(@PathVariable(value = "tipodedato")  String tipoDeDato){
		Integer respuesta = this.datosAValidar.obtenerTotalDatosAvalidar(tipoDeDato);
		
		System.out.println("Respuesta: " + respuesta);
		
		return respuesta;
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/tipo/datosavalidar")
	public List<String> obtenerTiposDeDatos(){
		List<String> respuesta = this.datosAValidar.obtenerTiposDeDatos();
		
		return respuesta;
		
	}

}
