package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IAsignacionClienteDAO;
import com.isae.drpool.drpool.dao.IAsignacionProyectoDAO;
import com.isae.drpool.drpool.dao.IEstatusProyecto;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.EstatusProyecto;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;

@RestController
public class DashboardController {

	@Autowired
	private IInventarioDAO inventario;

	@Autowired
	private IAsignacionProyectoDAO asignacionRegistro;

	@Autowired
	private IEstatusProyecto estatusProyecto;
	
	@Autowired
	private IUsuarioDAO usuario;
	
	@Autowired
	private IAsignacionClienteDAO asignacionCliente;

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/total/registros/proyectos")
	public List<Map<String, Object>> obtenerTotalRegistrosPorProyecto() {
		List<Map<String, Object>> respuesta = new ArrayList<>();
		List<Object> consulta = new ArrayList<Object>();

		consulta = this.inventario.obtenerTotalRegistrosProyectos();

		for (Object item : consulta) {
			Object[] objeto = (Object[]) item;
			Map<String, Object> aux = new HashMap<>();
			aux.put("domain", objeto[0]);
			aux.put("measure", objeto[1]);
			aux.put("idproyecto", objeto[2]);
			respuesta.add(aux);
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/total/estatus/proyecto/{idproyecto}")
	public List<Map<String, Object>> obtenerTotalEstatusProyecto(
			@PathVariable(value = "idproyecto") String idProyecto) {
		List<Map<String, Object>> respuesta = new ArrayList<>();
		List<Object> consulta = new ArrayList<Object>();

		consulta = this.inventario.obtenerTotalEstatusProyecto(Integer.parseInt(idProyecto));

		for (Object item : consulta) {
			Object[] objeto = (Object[]) item;
			Map<String, Object> aux = new HashMap<>();
			aux.put("domain", objeto[0]);
			aux.put("measure", objeto[1]);
			respuesta.add(aux);
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/total/estatus/proyecto/usuario/{idusuario}")
	public Map<String, Object> obtenerTotalEstatusProyectoPorIdUsuario(
			@PathVariable(value = "idusuario") String idusuario) {
		Map<String, Object> proyectos = new HashMap<>();
		List<Object> consulta = new ArrayList<Object>();
		Usuario usuario = this.usuario.obtenerUsuarioPorId(Integer.parseInt(idusuario));
		List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
		
		if(usuario.getPerfile().getIdperfil() <= 2) {
			 listaProyectos = this.asignacionCliente.obtenerProyectosPorClienteAplicacion(usuario.getClienteAplicacion());
			 System.out.println("Total de proyectos: "+ listaProyectos.size());

				for (Proyecto proyecto : listaProyectos) {
					List<EstatusProyecto> estatusProyecto = this.estatusProyecto
							.obtenerEstatusPorProyecto(proyecto.getIdproyecto());
					Map<String, Object> aux = new HashMap<>();
					consulta = this.inventario.obtenerTotalEstatusProyecto(
							proyecto);
					if (estatusProyecto.isEmpty()) {
						aux.put("NUEVO", 0);
						aux.put("ASIGNADO", 0);
						aux.put("EN PROCESO", 0);
						aux.put("PENDIENTE", 0);
						aux.put("CERRADO", 0);
					} else {
						aux.put("CERRADO", 0);
						for (EstatusProyecto estatus : estatusProyecto) {
							aux.put(estatus.getEstatus(), 0);
						}
					}
					for (Object item : consulta) {
						Object[] objeto = (Object[]) item;
						aux.put(objeto[0].toString(), objeto[1]);
					}
					proyectos.put(proyecto.getProyecto(), aux);
				}
		}else {			
			listaProyectos = this.asignacionRegistro
					.obtenerProyectosAsignados(usuario);
			System.out.println("Total de proyectos asignados: "+ listaProyectos.size());

			for (Proyecto proyecto : listaProyectos) {
				List<EstatusProyecto> estatusProyecto = this.estatusProyecto
						.obtenerEstatusPorProyecto(proyecto.getIdproyecto());
				Map<String, Object> aux = new HashMap<>();
				consulta = this.inventario.obtenerTotalEstatusProyectoAsignados(
						proyecto, usuario);
				if (estatusProyecto.isEmpty()) {
					aux.put("NUEVO", 0);
					aux.put("ASIGNADO", 0);
					aux.put("EN PROCESO", 0);
					aux.put("PENDIENTE", 0);
					aux.put("CERRADO", 0);
				} else {
					aux.put("CERRADO", 0);
					for (EstatusProyecto estatus : estatusProyecto) {
						aux.put(estatus.getEstatus(), 0);
					}
				}
				for (Object item : consulta) {
					Object[] objeto = (Object[]) item;
					aux.put(objeto[0].toString(), objeto[1]);
				}
				proyectos.put(proyecto.getProyecto(), aux);
			}
		}

		return proyectos;
	}

}
