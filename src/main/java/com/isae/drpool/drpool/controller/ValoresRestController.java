package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Campos;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.entity.ValoresCampo;
import com.isae.drpool.drpool.entity.ValoresCampos;


@RestController
//@RequestMapping("/valores")
public class ValoresRestController {

	@Autowired
	private IValoresDAO valores;
	
	@Autowired
	private IProyectoDAO proyecto;
	
	@Autowired
	private ICamposProyectoDAO camposProyecto;
	
	@Autowired
	private IInventarioDAO inventario;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/registrar/campo/valores")
	public List<String> registrarCampoValores(@RequestBody List<ValoresCampo> valores) {
		List<String> respuesta = new ArrayList<>();
		List<Valore> listaValores = new ArrayList<Valore>();

		int id = 0;

		for (int i = 0; i < valores.size(); i++) {
			id++;
			listaValores.add(new Valore(0, valores.get(i).getValor().toUpperCase(),
					new Camposproyecto(valores.get(i).getIdCampo()), new Inventario(valores.get(i).getIdInventario())));
		}

		System.out.println(listaValores);
		this.valores.saveAll(listaValores);

		respuesta.add("correcto");

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/registrar/campo/valores/plantilla")
	public List<String> registrarCampoValoresPlantilla(@RequestBody List<List<ValoresCampo>> listaValores) {
		List<String> respuesta = new ArrayList<>();
		List<Valore> listaValor = new ArrayList<Valore>();

		//int id = this.valores.obtenerUltimoId();
		System.out.println("Dentro del metodo");

		for (int i = 0; i < listaValores.size(); i++) {
			System.out.println("Dentro del primer for: "+i);
			for (int j =  0; j < listaValores.get(i).size(); j++) {
				System.out.println("Dentro del segundo for: "+j);
				//id++;
				listaValor.add(new Valore(0, listaValores.get(i).get(j).getValor().toUpperCase(),
						new Camposproyecto(listaValores.get(i).get(j).getIdCampo()),
						new Inventario(listaValores.get(i).get(j).getIdInventario())));
			}
		}
//		for(Valore valor : listaValor) {
//			System.out.println("Guardando: "+ valor.getValor());
//			this.valores.save(valor);
//
//		}
		
		this.valores.saveAll(listaValor);
		

		respuesta.add("correcto");

		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datos/busqueda/proyecto/{idProyecto}/{tipoBusqueda}")
	public List<String> getDatosProyectoBusqueda(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "tipoBusqueda") String tipoBusqueda
			) {
		List<String> datos = new ArrayList<String>();
		if(tipoBusqueda.equalsIgnoreCase("TODO")) {
			datos = this.valores.obtenerValorPorProyecto(Integer.parseInt(idProyecto));
		}else {
			datos = this.valores.obtenerValorPorProyectoCampo(Integer.parseInt(idProyecto), tipoBusqueda);
		}
		
		return datos;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/valor/campo/inventario/{idcampo}/{idinventario}")
	public String obtenerValorCampo(
			@PathVariable(value = "idcampo") String idCampo,
			@PathVariable(value = "idinventario") String idInventario
			) {
		return this.valores.obtenerValorCampoPorIdCampo(Integer.parseInt(idCampo), Integer.parseInt(idInventario));
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/valores/campos")
	public List<String> actualizarValores(@RequestBody List<ValoresCampos> valoresCampos){
		List<String> respuesta = new ArrayList<>();
		
		System.out.println(valoresCampos);
		
		for(ValoresCampos item : valoresCampos) {
			if(!item.getValor().isEmpty()) {
				this.valores.actualizarValores(item.getValor(),item.getIdcampoproyecto(), item.getIdinventario());
			}else {
				this.valores.actualizarValores("",item.getIdcampoproyecto(), item.getIdinventario());
			}
		}
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/valores/campos/historial/{idusuario}")
	public List<String> actualizarValoresHistorial(@RequestBody List<ValoresCampos> valoresCampos, @PathVariable(value = "idusuario") String idusuario){
		List<String> respuesta = new ArrayList<>();
		
		System.out.println(valoresCampos);
		
		for(ValoresCampos item : valoresCampos) {
			if(!item.getValor().isEmpty()) {
//				this.valores.actualizarValores(item.getValor(),item.getIdcampoproyecto(), item.getIdinventario());
				this.valores.actualizarValorEHistorial(item.getIdcampoproyecto(), Integer.parseInt(idusuario), item.getIdinventario(), item.getValor());
			}else {
//				this.valores.actualizarValores("",item.getIdcampoproyecto(), item.getIdinventario());
				this.valores.actualizarValorEHistorial(item.getIdcampoproyecto(), Integer.parseInt(idusuario), item.getIdinventario(), "");
			}
		}
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/valores/campos/busqueda")
	public List<Inventario> obtenerValoresCampos(@RequestBody Map<String,Object> busqueda){
		List<Inventario> respuesta = new ArrayList<>();
		
		int idProyecto =Integer.parseInt(busqueda.get("idproyecto").toString());
		String campo = busqueda.get("campo").toString();
		String dato = busqueda.get("busqueda").toString();
		int idUsuario = Integer.parseInt(busqueda.get("idusuario").toString());
		
		Camposproyecto campoProyecto = this.camposProyecto.obtenerCampoProyectoPorProyecto(idProyecto, campo);
		List<Object> respuestaBusqueda = new ArrayList<Object>();
		if(idUsuario == 0) {
			respuestaBusqueda = this.valores.obtenerRespuestaBusquedaProyecto(idProyecto, campoProyecto.getIdcamposproyecto(), dato);
		}else {
			respuestaBusqueda = this.valores.obtenerRespuestaBusquedaProyecto(idProyecto, campoProyecto.getIdcamposproyecto(), dato, idUsuario);
		}
		
		
		for(Object item : respuestaBusqueda) {
			Object [] inventario  = (Object[]) item;
			Proyecto  proyectoAux = this.proyecto.findById((int) inventario[2]).get();
			respuesta.add(new Inventario((int) inventario[0], (Date) inventario[3], (String) inventario[1], (String) inventario[4], proyectoAux));
		}
		
		
		System.out.println("Respuesta: " + respuesta);
		
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/valores/campos/busqueda/usuarios")
	public List<Inventario> obtenerValoresCamposPorUsuarios(@RequestBody Map<String,Object> busqueda){
		List<Inventario> respuesta = new ArrayList<>();
		
		int idProyecto =Integer.parseInt(busqueda.get("idproyecto").toString());
		String campo = busqueda.get("campo").toString();
		String dato = busqueda.get("busqueda").toString();
		List<Usuario> usuarios =  (List<Usuario>) busqueda.get("usuarios");
		
		Camposproyecto campoProyecto = this.camposProyecto.obtenerCampoProyectoPorProyecto(idProyecto, campo);
		
		List<Object> respuestaBusqueda = new ArrayList<Object>();
		
		for(Usuario usuario : usuarios) {
			respuestaBusqueda.add(this.valores.obtenerRespuestaBusquedaProyecto(idProyecto, campoProyecto.getIdcamposproyecto(), dato, usuario.getIdusuario()));
		}
		
		//List<Object> respuestaBusqueda = this.valores.obtenerRespuestaBusquedaProyecto(idProyecto, campoProyecto.getIdcamposproyecto(), dato, idUsuario);
		
		for(Object item : respuestaBusqueda) {
			Object [] inventario  = (Object[]) item;
			Proyecto  proyectoAux = this.proyecto.findById((int) inventario[2]).get();
			respuesta.add(new Inventario((int) inventario[0], (Date) inventario[3], (String) inventario[1], (String) inventario[4], proyectoAux));
		}
		
		
		System.out.println("Respuesta: " + respuesta);
		
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/valores/busqueda/{idproyecto}/{idusuario}")
	public List<String> obtenerValoresBusqueda(@RequestBody String tipoBusqueda, @PathVariable(value = "idproyecto") String idproyecto, @PathVariable(value = "idusuario") String idusuario){
		List<String> respuesta = new ArrayList<>();
		
		Camposproyecto campoProyecto = this.camposProyecto.obtenerCampoProyectoPorProyecto(Integer.parseInt(idproyecto), tipoBusqueda);
		
		if(idusuario.equals("0")) {
			respuesta = this.valores.obtenerValoresPorCampoProyecto(Integer.parseInt(idproyecto),campoProyecto.getIdcamposproyecto());
		}else {
			respuesta = this.valores.obtenerValoresPorCampoProyecto(Integer.parseInt(idproyecto),campoProyecto.getIdcamposproyecto(), Integer.parseInt(idusuario));
		}
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/valores/busqueda")
	public List<Valore> obtenerValoresBusqueda(@RequestBody Map<String,Object> contenido){
		List<Valore> respuesta = new ArrayList<Valore>();
		
		Gson gson = new Gson();
		String json = gson.toJson(contenido.get("campo"));
		
		Camposproyecto campoProyecto = gson.fromJson(json, new TypeToken<Camposproyecto>(){}.getType());
		
		json = gson.toJson(contenido.get("usuarios"));
		List<Usuario> listaUsuarios = gson.fromJson(json, new TypeToken<List<Usuario>>(){}.getType());
		
		json = gson.toJson(contenido.get("proyecto"));
		
		List<Proyecto> proyecto = gson.fromJson(json, new TypeToken<List<Proyecto>>(){}.getType());
		
		respuesta = this.valores.obtenerValoresPorCampoProyecto(proyecto.get(0), campoProyecto, listaUsuarios);
		
		return respuesta;
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping("/validar/valores/duplicados/{idproyecto}/{idinventario}")
	public String validarValoresDuplicados(@RequestBody List<Campos> campos, @PathVariable(value = "idproyecto") String idproyecto,@PathVariable(value = "idinventario") String idinventario){
		String respuesta = "DUPLICADOS: ";
		List<String> valores = new ArrayList<String>();
		List<Integer> ids = new ArrayList<Integer>();
		List<Camposproyecto> camposProyecto = new ArrayList<Camposproyecto>();
		List<Valore> respuestaDuplicados;
		for(Campos campo : campos) {
			valores.add(campo.getValor());
			ids.add(campo.getIdCampo());
		}
		
		camposProyecto = this.camposProyecto.findAllById(ids);
		
		System.out.println(valores);
		System.out.println(camposProyecto);
		System.out.println(idproyecto);
		System.out.println(idinventario);
		
		if(idinventario.equals("0")) {
			respuestaDuplicados = this.valores.obtenerDuplicidad(valores, camposProyecto, this.proyecto.obtenerProyectoPorId(Integer.parseInt(idproyecto)),new Inventario(1, new Date(), "", "", new Proyecto(Integer.parseInt(idproyecto))));
		}else {
			respuestaDuplicados = this.valores.obtenerDuplicidad(valores, camposProyecto, this.proyecto.obtenerProyectoPorId(Integer.parseInt(idproyecto)),this.inventario.obtenerPorIdInventario(Integer.parseInt(idinventario)));
		}
		
		if(respuestaDuplicados.isEmpty()) {
			respuesta = "SIN DUPLICADOS";
			System.out.println("Sin duplicados");
		}else {
			for(Valore valor : respuestaDuplicados) {
				respuesta += "["+valor.getValor()+" Con el folio: "+valor.getInventario().getFolio()+" Codigo: "+valor.getInventario().getIdinventario()+"]";
			}
		}
		
		return respuesta;
	}
	
	
	
}
