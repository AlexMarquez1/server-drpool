package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IPerfilesDAO;
import com.isae.drpool.drpool.entity.Perfile;


@RestController
//@RequestMapping("/perfiles")
public class PerfilesRestController {

	@Autowired
	private IPerfilesDAO perfiles;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/perfiles")
	public List<Perfile> getPerfiles() {		
		return perfiles.findAll();
	}
}
