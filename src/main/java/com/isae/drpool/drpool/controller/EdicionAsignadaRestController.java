package com.isae.drpool.drpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IEdicionAsignadaDAO;
import com.isae.drpool.drpool.entity.EdicionAsignada;


@RestController
//@RequestMapping("/EdicionAsignada")
public class EdicionAsignadaRestController {
	
	@Autowired
	private IEdicionAsignadaDAO edicionAsignada;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/ediciones/usuario/{idusuario}/{idinventario}")
	public List<EdicionAsignada> getEdicionesAsignadas(
			@PathVariable(value = "idusuario") String idusuario,
			@PathVariable(value = "idinventario") String idinventario
			) {
		return this.edicionAsignada.obtenerCamposAsignados(Integer.parseInt(idusuario), Integer.parseInt(idinventario));
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/asignar/ediciones")
	public String asignarEdicion(
			@RequestBody List<EdicionAsignada> listaEdicionesAAsignar
			) {
		if(!listaEdicionesAAsignar.isEmpty()) {
			this.edicionAsignada.eliminarEdicionPorUsuario(listaEdicionesAAsignar.get(0).getUsuario().getIdusuario(), listaEdicionesAAsignar.get(0).getInventario().getIdinventario());
			this.edicionAsignada.saveAll(listaEdicionesAAsignar);
		}
		return "ok";
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/eliminar/ediciones/usuario/campo/{idusuario}/{idinventario}/{idcampo}")
	public String eliminarAsignacion(
			@PathVariable(value = "idusuario") String idusuario,
			@PathVariable(value = "idinventario") String idinventario,
			@PathVariable(value = "idcampo") String idcampo
			) {
		this.edicionAsignada.eliminarPorInventarioIdCampo(Integer.parseInt(idinventario), Integer.parseInt(idcampo), Integer.parseInt(idusuario));
		
		return "Borrados";
	}

}
