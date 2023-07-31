package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.dao.IAsignarRegistroDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.entity.Asignacionproyecto;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;


@RestController
//@RequestMapping("/asignacion/proyecto")
public class AsignacionProyectoRestController {

	@Autowired
	private IAsignacionProyectoDAO asignacionProyecto;
	
	@Autowired
	private IAsignarRegistroDAO asignarRegistro;
	
	@Autowired
	private IInventarioDAO inventario;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/eliminar/asignacion/proyecto/{idUsuario}/{idProyecto}")
	public List<String> eliminarAsignacionProyecto(
			@PathVariable(value = "idUsuario") String idUsuario,
			@PathVariable(value = "idProyecto") String idProyecto) {
		List<String> respuesta = new ArrayList<>();
		

		this.asignacionProyecto.eliminarAsignacion(Integer.parseInt(idUsuario), Integer.parseInt(idProyecto));
		respuesta.add("correcto");
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/asignar/proyecto/{idUsuario}/{idProyecto}")
	public List<String> asignarProyecto(@PathVariable(value = "idUsuario") String idUsuario,
			@PathVariable(value = "idProyecto") String idProyecto) {
		List<String> respuesta = new ArrayList<>();
		Asignacionproyecto asignacionProyecto = 
				new Asignacionproyecto(0, new Usuario(Integer.parseInt(idUsuario)), new Proyecto(Integer.parseInt(idProyecto)));
		
		this.asignacionProyecto.save(asignacionProyecto);
		
		respuesta.add("correcto");
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/proyectos/asignados/{idusuario}")
	public List<Proyecto> getProyectosAsignados(@PathVariable(value = "idusuario") String idusuario) {
		List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
		List<Asignacionproyecto> lista = this.asignacionProyecto.obtenerProyectosAsignados(Integer.parseInt(idusuario));
		for(Asignacionproyecto item : lista) {
			listaProyectos.add(item.getProyecto());
		}
		return listaProyectos;
	}
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/proyectos/asignados/usuarios")
	public List<Proyecto> getProyectosAsignadosPorUsuarios(@RequestBody List<Usuario> listaUsuarios) {
		List<Proyecto> lista = this.asignacionProyecto.obtenerProyectosAsignados(listaUsuarios);
		
		return lista;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registros/asignados/{idusuario}")
	public List<String> getRegistrosAsignados(@PathVariable(value = "idusuario") String idusuario) {
		return this.asignacionProyecto.obtenerRegistroAsignado(Integer.parseInt(idusuario));
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registros/asignados/usuario/proyecto/{idusuario}/{idproyecto}")
	public List<Inventario> getRegistrosAsignadosUsuarioProyecto(
			@PathVariable(value = "idusuario") String idUsuario,
			@PathVariable(value = "idproyecto") String idProyecto
			) {
		List<Inventario> listaAsignacionRegistros;
		List<Inventario> listaRegistro = new ArrayList<Inventario>();
		if(idProyecto.equalsIgnoreCase("0")) {
			listaAsignacionRegistros = this.asignarRegistro.obtenerRegistrosAsignadosUsuario(new Usuario(Integer.parseInt(idUsuario)));
			
		}else {
			listaAsignacionRegistros = this.asignarRegistro.obtenerRegistrosAsignadosUsuarioProyecto(new Usuario(Integer.parseInt(idUsuario)), new Proyecto(Integer.parseInt(idProyecto)));
		}
		
		if(listaAsignacionRegistros.isEmpty() && idUsuario.equalsIgnoreCase("0")) {
			listaRegistro = this.inventario.findAll();
			
		}else {
			listaRegistro = listaAsignacionRegistros;

		}
		
		
		
		return listaRegistro;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/registros/proyecto")
	public List<Inventario> getRegistrosPorProyecto(
			@RequestBody Proyecto proyecto
			) {
		List<Inventario> listaRegistro = new ArrayList<Inventario>();
		
		listaRegistro = this.asignarRegistro.obtenerRegistrosPorProyectos(proyecto);
		System.out.println("Lista de registros: "+ listaRegistro.size());
		
		
		return listaRegistro;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/registros/asignados/usuario/proyecto")
	public List<Inventario> getRegistrosAsignadosUsuariosProyectos(@RequestBody Map<String,Object> contenido) {
		List<Inventario> listaAsignacionRegistros = new ArrayList<Inventario>();
		Gson gson = new Gson();
		String json = gson.toJson(contenido.get("usuarios"));
		
		List<Usuario> listaUsuarios = gson.fromJson(json, new TypeToken<List<Usuario>>(){}.getType());
		json = gson.toJson(contenido.get("proyectos"));
		List<Proyecto> listaProyectos = gson.fromJson(json, new TypeToken<List<Proyecto>>(){}.getType());
		
			listaAsignacionRegistros = this.asignarRegistro.obtenerRegistrosAsignadosUsuarioProyecto(listaUsuarios, listaProyectos);
		return listaAsignacionRegistros;
	}
}
