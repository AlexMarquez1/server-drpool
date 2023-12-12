package com.isae.drpool.drpool.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAlbercaDAO;
import com.isae.drpool.drpool.dao.IDireccionSedeDAO;
import com.isae.drpool.drpool.dao.ISedeDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Sede;
import com.isae.drpool.drpool.entity.Usuario;

@RestController
public class SedeRestController {
	
	@Autowired
	private ISedeDAO sede;
	@Autowired
	private IDireccionSedeDAO direccion; 
	@Autowired
	private IUsuarioDAO usuario;
	@Autowired
	private IAlbercaDAO alberca;
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/sedes")
	public List<Sede> obtenerSede(){
		return this.sede.findAll();
	} 
	
	@CrossOrigin(origins="*")
	@PostMapping("/nueva/sede")
	public String nuevaSede(@RequestBody Sede sede) {
		String respuesta;
		if(!this.sede.existsByNombre(sede.getNombre()) || this.sede.existsById(sede.getIdsede())) {
			respuesta = "se guardo correctamente";
			System.out.println(sede);
			
			Usuario coordinador = sede.getCoordinador();
			Usuario operador = sede.getOperador();
			
			coordinador.setAsignacion("ASIGNADO");
			operador.setAsignacion("ASIGNADO");
			
			this.usuario.save(coordinador);
			this.usuario.save(operador);
			
			
			if(sede.getDireccion().getIddireccion() != 0) {
				this.direccion.save(sede.getDireccion());
			}
			
			this.sede.save(sede);
			
			if(sede.getEstatus().equals("INACTIVO")) {
				List<Alberca> albercas = this.alberca.findBySede_Idsede(sede.getIdsede());
				for(Alberca alb : albercas) {
					alb.setEstatus("INACTIVO");
					this.alberca.save(alb);
				}
					
			}
		}else {
			respuesta = "El nombre de la Sede ya se encuentra registrado";	
		}
		
		return respuesta;
	}
}


