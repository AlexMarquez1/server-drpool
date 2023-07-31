package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ITipoProyectoDAO;
import com.isae.drpool.drpool.entity.Tipoproyecto;



@RestController
//@RequestMapping("/tipoproyecto")
public class TipoProyectoRestController {
	

	@Autowired
	private ITipoProyectoDAO tipoProyecto;

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/tipoproyecto")
	public List<String> getTipoProyectos() {
		List<String> resultado = new ArrayList<String>();
		
		List<Tipoproyecto> listaTipoProyecto = tipoProyecto.findAll();
		
		for(Tipoproyecto item : listaTipoProyecto) {
			resultado.add(item.getDescripcion());
		}
		
		return resultado;
	}

}
