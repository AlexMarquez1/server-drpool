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

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.ICatalogoDAO;
import com.isae.drpool.drpool.dao.ICatalogoRelacionadoDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.ITipoCatalogoDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Catalogo;
import com.isae.drpool.drpool.entity.CatalogoAux;
import com.isae.drpool.drpool.entity.CatalogoRelacionadoAux;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Tipocatalogo;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.VistaCatalogoRelacionado;



@RestController
//@RequestMapping("/catalogo")
public class CatalogoRestController {
	
	@Autowired
	private ICatalogoDAO catalogo;
	
	@Autowired
	private ITipoCatalogoDAO tipoCatalogo;
	
	@Autowired
	private IProyectoDAO proyecto;
	
	@Autowired
	private ICamposProyectoDAO camposProyecto;
	@Autowired
	private ICatalogoRelacionadoDAO catalogoRelacionado;
	
	@Autowired
	private IUsuarioDAO usuario;

	@CrossOrigin(origins = "*")
	@PostMapping("/crear/catalogo/{idProyecto}/{tipoCatalogo}")
	public List<String> ingresarCatalogo(@RequestBody List<String> contenidoCatalogo,
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "tipoCatalogo") String tipoCatalogo) {
		List<String> respuesta = new ArrayList<>();
		Catalogo catalogo = new Catalogo();
		List<Catalogo> listaCatalogo = new ArrayList<Catalogo>();

		Tipocatalogo tipoCatalogoOb = this.tipoCatalogo.obtenerCatalogoPorDescripcion(tipoCatalogo);
		Proyecto proyecto = this.proyecto.findById(Integer.parseInt(idProyecto)).get();

		for (int i = 0; i< contenidoCatalogo.size(); i++) {
			listaCatalogo.add(new Catalogo(0,contenidoCatalogo.get(i),tipoCatalogoOb,proyecto, null));
		}
		this.catalogo.saveAll(listaCatalogo);

		respuesta.add("correcto");
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/crear/catalogo/relacionado/{idproyecto}")
	public List<String> ingresarCatalogoRelacionado(@PathVariable(value = "idproyecto") String idProyecto,
			@RequestBody CatalogoRelacionadoAux catalogo) {
		List<String> respuesta = new ArrayList<>();
		respuesta.add("correcto");

		this.catalogo.eliminarCatalogoRelacionado(catalogo.getCatalogoPadre(), Integer.parseInt(idProyecto), catalogo.getTipoCatalogoPadre());
		
		
		for (String item : catalogo.getCatalogoHijo()) {
			this.catalogo.registrarCatalogoRelacionado(catalogo.getTipoCatalogoPadre(), catalogo.getCatalogoPadre(), catalogo.getTipoCatalogoHijo(), item, Integer.parseInt(idProyecto));
			
		}

		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/eliminar/catalogos/{tipoCatalogo}/{idProyecto}")
	public List<String> eliminarCatalogos(@PathVariable(value = "tipoCatalogo") String tipoCatalogo,
			@PathVariable(value = "idProyecto") String idProyecto) {
		List<String> respuesta = new ArrayList<>();

		this.catalogo.eliminarCatalogos(tipoCatalogo, Integer.parseInt(idProyecto));
		respuesta.add("correcto");
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/id/catalogo/{catalogo}/{idtipo}/{idproyecto}")
	public String obtenerIdCatalogo(
			@PathVariable(value = "catalogo") String catalogo,
			@PathVariable(value = "idtipo") String tipo,
			@PathVariable(value = "idproyecto") String idproyecto
			) {
		String respuesta ="0";
		
		Catalogo respuestaCatalogo = this.catalogo.obtenerCatalogoPorTipoProyecto(catalogo,tipo, Integer.parseInt(idproyecto));

		if(respuestaCatalogo != null) {
			respuesta = String.valueOf(respuestaCatalogo.getIdcatalogo());
		}
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/catalogo/datos/proyecto/{tipoCatalogo}")
	public CatalogoAux getDatosCatalogoProyecto(@RequestBody Proyecto proyecto,
			@PathVariable(value = "tipoCatalogo") String tipoCatalogo) {
		List<Catalogo> listaCatalogos = this.catalogo.obtenerDatosCatalogoProyecto(tipoCatalogo, proyecto.getIdproyecto());
		CatalogoAux catalogo = new CatalogoAux();
		List<String> lista = new ArrayList<String>();
		if(!listaCatalogos.isEmpty()) {
			catalogo.setProyecto(listaCatalogos.get(0).getProyecto());
			catalogo.setTipoCatalogo(listaCatalogos.get(0).getTipocatalogoBean().getTipo());
			for(Catalogo item : listaCatalogos) {
				lista.add(item.getCatalogo());
			}
			catalogo.setCatalogo(lista);
		}		
		
		return catalogo;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/catalogo/datos/proyecto/{tipoCatalogo}/{idUsuario}")
	public CatalogoAux getDatosCatalogoProyecto(@RequestBody Proyecto proyecto,
			@PathVariable(value = "tipoCatalogo") String tipoCatalogo,
			@PathVariable(value = "idUsuario") String idUsuario
			) {
		List<Catalogo> listaCatalogos = this.catalogo.obtenerDatosCatalogoProyectoSinUsuario(tipoCatalogo, proyecto.getIdproyecto());
		List<Catalogo> listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipoCatalogo, proyecto.getIdproyecto(), Integer.parseInt(idUsuario));
		List<Catalogo> ListaCatalogoSinUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuarioNulo(tipoCatalogo, proyecto.getIdproyecto());
		if(!listaCatalogosUsuario.isEmpty()) {
			listaCatalogos.addAll(listaCatalogosUsuario);
			if(ListaCatalogoSinUsuario.isEmpty()){
				listaCatalogos.addAll(ListaCatalogoSinUsuario);
			}
		}
		CatalogoAux catalogo = new CatalogoAux();
		List<String> lista = new ArrayList<String>();
		if(!listaCatalogos.isEmpty()) {
			catalogo.setProyecto(listaCatalogos.get(0).getProyecto());
			catalogo.setTipoCatalogo(listaCatalogos.get(0).getTipocatalogoBean().getTipo());
			for(Catalogo item : listaCatalogos) {
				lista.add(item.getCatalogo());
			}
			catalogo.setCatalogo(lista);
		}		
		
		return catalogo;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/nuevo/catalogos/usuario/{tipocatalogo}/{idproyecto}/{idusuario}/{catalogo}")
	public String nuevoCatalogoUsuario(
			@PathVariable(value = "tipocatalogo") String tipocatalogo,
			@PathVariable(value = "idproyecto") String idproyecto,
			@PathVariable(value = "idusuario") String idusuario,
			@PathVariable(value = "catalogo") String catalogo
			) {
		String respuesta = "Error";
		try {
			Tipocatalogo tipoCatalogo = this.tipoCatalogo.obtenerCatalogoPorDescripcion(tipocatalogo);
			
			
			List<Catalogo> listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipocatalogo, Integer.parseInt(idproyecto), Integer.parseInt(idusuario), catalogo);
			Proyecto proyecto = this.proyecto.obtenerProyectoPorId(Integer.parseInt(idproyecto));
			Usuario usuario = this.usuario.obtenerUsuarioPorId(Integer.parseInt(idusuario));
			if(listaCatalogosUsuario.isEmpty()) {
				this.catalogo.save(new Catalogo(0,catalogo,tipoCatalogo,proyecto,usuario));
			}
			
			respuesta = "Correcto";
			
		}catch(Exception e) {
			System.out.println("Error encontrado: "+e);
		}
		
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nuevo/catalogos/usuario/{tipocatalogo}/{idproyecto}/{idusuario}")
	public String nuevoCatalogoAutoCompleteUsuario(
			@PathVariable(value = "tipocatalogo") String tipocatalogo,
			@PathVariable(value = "idproyecto") String idproyecto,
			@PathVariable(value = "idusuario") String idusuario,
			@RequestBody Map<String,String> catalogo
			) {
		String respuesta = "Error";
		try {
			Tipocatalogo tipoCatalogo = this.tipoCatalogo.obtenerCatalogoPorDescripcion(tipocatalogo);
			
			
			List<Catalogo> listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipocatalogo, Integer.parseInt(idproyecto), Integer.parseInt(idusuario), catalogo.get("catalogo"));
			Proyecto proyecto = this.proyecto.obtenerProyectoPorId(Integer.parseInt(idproyecto));
			Usuario usuario = this.usuario.obtenerUsuarioPorId(Integer.parseInt(idusuario));
			if(listaCatalogosUsuario.isEmpty()) {
				this.catalogo.save(new Catalogo(0,catalogo.get("catalogo"),tipoCatalogo,proyecto,usuario));
			}
			
			respuesta = "Correcto";
			
		}catch(Exception e) {
			System.out.println("Error encontrado: "+e);
		}
		
		
		return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/catalogo/datos/proyecto/relacionado/{tipoCatalogoPadre}/{catalogoPadre}")
	public CatalogoAux getDatosCatalogoProyectoRelacionado(@RequestBody Proyecto proyecto,
			@PathVariable(value = "tipoCatalogoPadre") String tipoCatalogoPadre,
			@PathVariable(value = "catalogoPadre") String catalogoPadre
			) {
		List<VistaCatalogoRelacionado> lista = this.catalogoRelacionado.obtenerCatalogosRelacionados(tipoCatalogoPadre, catalogoPadre, proyecto.getIdproyecto());
		List<String> listaCatalogo = new ArrayList<String>();
		CatalogoAux catalogo = new CatalogoAux();
		for(VistaCatalogoRelacionado item : lista) {
			listaCatalogo.add(item.getCatalogoHijo());
		}
		
		catalogo.setTipoCatalogo(lista.get(0).getTipoHijo());
		catalogo.setProyecto(this.proyecto.findById(lista.get(0).getIdproyecto()).get());
		catalogo.setCatalogo(listaCatalogo);
		
		return catalogo;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/catalogorelacionado/{idProyecto}")
	public CatalogoRelacionadoAux getCatalogoRelacionado(@RequestBody CatalogoRelacionadoAux catalogo,
			@PathVariable(value = "idProyecto") String idProyecto) {
		List<VistaCatalogoRelacionado> lista = this.catalogoRelacionado.obtenerCatalogoRelacionado(catalogo.getTipoCatalogoPadre(), catalogo.getCatalogoPadre(),Integer.parseInt(idProyecto));
		CatalogoRelacionadoAux catalogoRelacionado = new CatalogoRelacionadoAux();
		List<String> listaCatalogo = new ArrayList<String>();
		for(VistaCatalogoRelacionado item : lista) {
			listaCatalogo.add(item.getCatalogoHijo());
		}
		
		if(!lista.isEmpty()) {
			catalogoRelacionado.setTipoCatalogoPadre(lista.get(0).getTipoPadre());
			catalogoRelacionado.setCatalogoPadre(lista.get(0).getCatalogoPadre());
			catalogoRelacionado.setTipoCatalogoHijo(lista.get(0).getTipoHijo());
			catalogoRelacionado.setCatalogoHijo(listaCatalogo);
		}
		
		return catalogoRelacionado;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/catalogos/proyecto/{idProyecto}")
	public List<String> getCatalogosProyecto(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampo(Integer.parseInt(idProyecto));
		List<String> catalogos = new ArrayList<String>();
		if(!lista.isEmpty()) {
			for(Camposproyecto item : lista) {
				catalogos.add(item.getCampo());
			}
		}
		return catalogos;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/catalogo/datos/proyecto/relacionado/{idProyecto}")
	public List<CatalogoRelacionadoAux> getCatalogoRelacionado(
			@PathVariable(value = "idProyecto") String idProyecto
			) {
		List<CatalogoRelacionadoAux> listaCatalogoRelacionado = new ArrayList<CatalogoRelacionadoAux>();
		String tipoCatalogoPadre = "NUEVO";
		String catalogoPadre = "";
		String tipoCatalogoHijo = "";
		List<String> catalogoHijo = new ArrayList<String>();
		
		List<VistaCatalogoRelacionado> lista = this.catalogoRelacionado.obtenerCatalogosRelacionadosPorProyecto(Integer.parseInt(idProyecto));
		
		for(VistaCatalogoRelacionado item : lista) {
			if(tipoCatalogoPadre.equalsIgnoreCase("NUEVO")) {
				tipoCatalogoPadre = item.getTipoPadre();
				catalogoPadre = item.getCatalogoPadre();
				tipoCatalogoHijo = item.getTipoHijo();
				catalogoHijo.add( item.getCatalogoHijo());
			}else {
				if(tipoCatalogoPadre.equalsIgnoreCase(item.getTipoPadre()) && catalogoPadre.equalsIgnoreCase(item.getCatalogoPadre())) {
					catalogoHijo.add(item.getCatalogoHijo());
				}else {
					listaCatalogoRelacionado.add(new CatalogoRelacionadoAux(tipoCatalogoPadre,catalogoPadre,tipoCatalogoHijo,catalogoHijo));
					catalogoHijo = new ArrayList<>();
					tipoCatalogoPadre = item.getTipoPadre();
					catalogoPadre = item.getCatalogoPadre();
					tipoCatalogoHijo = item.getTipoHijo();
					catalogoHijo.add( item.getCatalogoHijo());
				}
			}
		}
		listaCatalogoRelacionado.add(new CatalogoRelacionadoAux(tipoCatalogoPadre,catalogoPadre,tipoCatalogoHijo,catalogoHijo));
		
		return listaCatalogoRelacionado;
	}
}
