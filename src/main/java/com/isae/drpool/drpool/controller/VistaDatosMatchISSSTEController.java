package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IVistaDatosMatchISSSTE;
import com.isae.drpool.drpool.entity.VistaDatosMatchValores;

@RestController
public class VistaDatosMatchISSSTEController {
	
	@Autowired
	private IVistaDatosMatchISSSTE vistaDatosMatch;
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/match/datos/issste")
	public List<VistaDatosMatchValores> getDatosProyectoBusqueda() {
		List<VistaDatosMatchValores> listaDatos = new ArrayList<VistaDatosMatchValores>();
		listaDatos = this.vistaDatosMatch.findAll();
		
		return listaDatos;
	}

}
