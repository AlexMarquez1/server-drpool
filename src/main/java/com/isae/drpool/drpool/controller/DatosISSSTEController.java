package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IDatosISSSTEDAO;
import com.isae.drpool.drpool.entity.Datosissste;


@RestController
public class DatosISSSTEController {
	
	@Autowired
	private IDatosISSSTEDAO datosIssste;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datosissste/nombreusuario/{nombre}")
	public List<Datosissste> obtenerPorNombreDeUsuario(
			@PathVariable(value = "nombre") String nombre) {
		List<Datosissste> respuesta = new ArrayList<Datosissste>();
		

		respuesta = this.datosIssste.obtenerDatosPorNombreUsuario(nombre);
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/nombres/issste/{nombre}")
	public List<String> obtenerNombresIssste(
			@PathVariable(value = "nombre") String nombre) {
		List<String> respuesta = new ArrayList<String>();
		
		String nombreLowerCase = nombre.toLowerCase();
		String nombreCapitalice = capitalizar(nombre);
		String nombreUpperCase = nombre.toUpperCase();
		
		respuesta = this.datosIssste.obtenerNombresUsuario(nombreLowerCase,nombreCapitalice, nombreUpperCase);
		
		return respuesta;
	}
	
	private String capitalizar(String nombre) {
		String respuesta = "";
		String palabras[] = nombre.split(" ");
		
		if(palabras.length == 1) {
			respuesta = upperCaseFirst(palabras[0]);
		}else {
			for(String palabra : palabras) {
				respuesta += upperCaseFirst(palabra);
				respuesta += " ";
			}
		}
		
		return respuesta;
	}
	
	private String upperCaseFirst(String val) {
		String respuesta = "";
		String primeraLetra = String.valueOf( val.toCharArray()[0]);
	    respuesta = primeraLetra.toUpperCase()+val.substring(1).toLowerCase();
	    return respuesta;
	   }
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datosissste/numerousuario/{numero}")
	public List<Datosissste> obtenerPorNumeroDeUsuario(
			@PathVariable(value = "numero") String numero) {
		List<Datosissste> respuesta = new ArrayList<Datosissste>();
		
		System.out.println("Numero a buscar: " + numero);
		respuesta = this.datosIssste.obtenerDatosPorNumeroUsuario(Integer.parseInt(numero));
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datosissste")
	public List<Datosissste> obtenerDatosIssste() {
		List<Datosissste> respuesta = new ArrayList<Datosissste>();
		
		respuesta = this.datosIssste.findAll();
		
		return respuesta;
	}
}
