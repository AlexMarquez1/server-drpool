package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ICamposBusquedaDAO;
import com.isae.drpool.drpool.entity.CamposBusqueda;

@RestController
public class CamposBusquedaController {

	
	@Autowired
	private ICamposBusquedaDAO camposBusqueda;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/camposbusqueda/proyecto/{idproyecto}/{idcampoproyecto}")
	public List<CamposBusqueda> obtenerCamposBusqueda(@PathVariable(value = "idproyecto") String idproyecto, @PathVariable(value = "idcampoproyecto") String idCampoProyecto) {
		
		List<CamposBusqueda> listaRespuesta = this.camposBusqueda.obtenerCamposBusquedaPorCampoProyecto(Integer.parseInt(idproyecto), Integer.parseInt(idCampoProyecto), idCampoProyecto);
		
		
		return listaRespuesta;
	}
	
}
