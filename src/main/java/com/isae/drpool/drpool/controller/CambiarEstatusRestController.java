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

import com.isae.drpool.drpool.dao.IEnProcesoDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IPendienteDAO;
import com.isae.drpool.drpool.entity.EnProceso;
import com.isae.drpool.drpool.entity.Estatus;
import com.isae.drpool.drpool.entity.Pendiente;

@RestController
//@RequestMapping("/estatus")
public class CambiarEstatusRestController {
	
	@Autowired
	private IEnProcesoDAO enProceso;
	
	@Autowired
	private IPendienteDAO pendiente;
	
	@Autowired
	private IInventarioDAO inventario;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/enProceso/{idinventario}")
	public String getEnProceso(@PathVariable(value = "idinventario") String idinventario) {
		List<EnProceso> actual = this.enProceso.obtenerActual(Integer.parseInt(idinventario));
		String respuesta = "Error";
		if(!actual.isEmpty()) {
			respuesta = actual.get(0).getAgrupacion();
		}
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/pendiente/{idinventario}")
	public Pendiente getPendiente(@PathVariable(value = "idinventario") String idinventario) {
		List<Pendiente> actual = this.pendiente.obtenerActual(Integer.parseInt(idinventario));
		Pendiente respuesta = new Pendiente();
		if(!actual.isEmpty()) {
			respuesta = actual.get(0);
		}
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/estatus/cambiarestatus")
	public List<String> cambiarEstatus(@RequestBody Estatus estatus) {
		List<String> respuesta = new ArrayList<String>();
		System.out.println(estatus);
		this.enProceso.eliminarInventario(estatus.getInventario().getIdinventario());
		this.pendiente.eliminarInventario(estatus.getInventario().getIdinventario());
		
		switch(estatus.getEstatus()) {
		case "EN PROCESO":
			List<EnProceso> actual= this.enProceso.obtenerActual(estatus.getInventario().getIdinventario());
			EnProceso nuevo = new EnProceso(0,estatus.getInventario(),estatus.getAgrupacion());
			if(!actual.isEmpty()) {
				nuevo.setIdenproceso(actual.get(0).getIdenproceso());
			}
			this.enProceso.save(nuevo);
			
			break;
		case "PENDIENTE":
			List<Pendiente> actualPendiente = this.pendiente.obtenerActual(estatus.getInventario().getIdinventario());
			Pendiente nuevoPendiente = new Pendiente(0,estatus.getInventario(),estatus.getMotivo(),estatus.getDescripcion(),estatus.getAgrupacion());
			if(!actualPendiente.isEmpty()) {
				nuevoPendiente.setIdpendiente(actualPendiente.get(0).getIdpendiente());
			}
			this.pendiente.save(nuevoPendiente);
			break;
		}
		
		this.inventario.cambiarEstatus(estatus.getEstatus(), estatus.getInventario().getIdinventario());
		
		respuesta.add("Correcto");
		
		return respuesta;
	}
	
}
