package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Campos;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Tipoproyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.entity.firmasdocumento;


@RestController
//@RequestMapping("/campos/proyecto")
public class CamposProyectoRestController {
	
	@Autowired
	private ICamposProyectoDAO camposProyecto;
	
	@Autowired
	private IValoresDAO valores;
	
	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;
	
	@Autowired
	private IFirmaDocumentosDAO firmaDocumento;
	
	@Autowired
	private IInventarioDAO inventario;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/catalogo/campos/proyecto")
	public List<String> getCatalogoCamposProyecto(@RequestBody Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampo(proyecto.getIdproyecto());
		List<String> listaCatalogo = new ArrayList<String>();
		
		for(Camposproyecto item : lista) {
			listaCatalogo.add(item.getCampo());
		}
		return listaCatalogo;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/proyecto/{idProyecto}")
	public List<Agrupaciones> getCamposProyecto(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));
		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
		List<String> agrupaciones = new ArrayList<String>();
		List<Campos> campos = new ArrayList<Campos>(); 
		Campos campo = new Campos();
		for(Camposproyecto item : lista) {
			campo = new Campos();
			campo.setIdCampo(item.getIdcamposproyecto());
			campo.setNombreCampo(item.getCampo());
			campo.setTipoCampo(item.getTipocampo());
			campo.setRestriccion(item.getPattern());
			campo.setAlerta(item.getAlerta());
			campo.setAgrupacion(item.getAgrupacion().getAgrupacion());
			campo.setLongitud(item.getLongitud());
			
			if(agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
				agrupaciones.add(item.getAgrupacion().getAgrupacion());
				campos.add(campo);
				listaAgrupaciones.add(new Agrupaciones(item.getAgrupacion().getIdagrupacion(), item.getAgrupacion().getAgrupacion(),campos));
			}else {
				if(agrupaciones.contains(item.getAgrupacion().getAgrupacion())) {
					List<Campos> aux = new ArrayList<Campos>();
					aux = listaAgrupaciones.get(agrupaciones.indexOf(item.getAgrupacion().getAgrupacion())).getCampos();
					aux.add(campo);
					listaAgrupaciones.get(agrupaciones.indexOf(item.getAgrupacion().getAgrupacion())).setCampos(aux);
				}else {
					agrupaciones.add(item.getAgrupacion().getAgrupacion());
					campos = new ArrayList<Campos>();
					campos.add(campo);
					listaAgrupaciones.add(new Agrupaciones(item.getAgrupacion().getIdagrupacion(), item.getAgrupacion().getAgrupacion(),campos));
				}
			}
		}
		return listaAgrupaciones;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datos/registro/{idRegistro}/{idProyecto}")
	public List<Agrupaciones> getDatosRegistro(
			@PathVariable(value = "idRegistro") String idRegistro,
			@PathVariable(value = "idProyecto") String idProyecto
			) {
		System.out.println("IdInventario: " + idRegistro);
		int idInventario = Integer.parseInt(idRegistro), idProyect = Integer.parseInt(idProyecto);
		Inventario inventario;
		if(idProyecto.equals("0")) {
			inventario = this.inventario.findById(Integer.parseInt(idRegistro)).get();
			idInventario = inventario.getIdinventario();
			idProyect = inventario.getProyecto().getIdproyecto();
			
		}
		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
		List<String> agrupaciones = new ArrayList<String>();
		List<Campos> campos = new ArrayList<Campos>(); 
		Campos campo = new Campos();
		List<Valore> lista = this.valores.obtenerDatosCampoPorProyecto(idInventario, idProyect);
		for(Valore item : lista) {
			campo = new Campos();
			campo.setIdCampo(item.getCamposproyecto().getIdcamposproyecto());
			campo.setNombreCampo(item.getCamposproyecto().getCampo());
			campo.setTipoCampo(item.getCamposproyecto().getTipocampo());
			campo.setRestriccion(item.getCamposproyecto().getPattern());
			campo.setAlerta(item.getCamposproyecto().getAlerta());
			campo.setAgrupacion(item.getCamposproyecto().getAgrupacion().getAgrupacion());
			campo.setLongitud(item.getCamposproyecto().getLongitud());
			campo.setValor(item.getValor());
			
			if(agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
				agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
				campos.add(campo);
				listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(), item.getCamposproyecto().getAgrupacion().getAgrupacion(),campos));
			}else {
				if(agrupaciones.contains(item.getCamposproyecto().getAgrupacion().getAgrupacion())) {
					List<Campos> aux = new ArrayList<Campos>();
					aux = listaAgrupaciones.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion())).getCampos();
					aux.add(campo);
					listaAgrupaciones.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion())).setCampos(aux);
				}else {
					agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
					campos = new ArrayList<Campos>();
					campos.add(campo);
					listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(), item.getCamposproyecto().getAgrupacion().getAgrupacion(),campos));
				}
			}
		}
		return listaAgrupaciones;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/busqueda/proyecto/{idProyecto}")
	public List<String> getCamposProyectoBusqueda(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));
		List<String> campos = new ArrayList<String>();
		for(Camposproyecto item : lista) {
			campos.add(item.getCampo());
		}
		return campos;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/campos/proyectos")
	public List<Camposproyecto> obtenerCamposBusquedaProyectos(@RequestBody List<Proyecto> proyectos) {
		
		List<Camposproyecto> listaRespuesta = this.camposProyecto.obtenerCatalogoCampoPorProyecto(proyectos);
		
		
		return listaRespuesta;
	}
	
	
	
	
//------------------------------Aplicacion Movil------------------------------------
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/firma/proyecto/{idProyecto}/{idInventario}")
	public List<firmasdocumento> obtenerCantidadFirmas(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idInventario") String idInventario
			) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFirmasPorProyecto(Integer.parseInt(idProyecto));
		
		List<firmasdocumento> firmas = new ArrayList<firmasdocumento>();
		for(Camposproyecto campo : lista) {
			List<firmasdocumento> firma = this.firmaDocumento.obtenerConcidencia(campo.getCampo(), campo.getIdcamposproyecto(), Integer.parseInt(idInventario));
			if(firma.isEmpty()) {
				firmas.add(new firmasdocumento(0,campo.getCampo(),"",new Camposproyecto(campo.getIdcamposproyecto()), new Inventario(Integer.parseInt(idInventario))));
			}else {
				firmas.add(firma.get(0));
			}
		}
		return firmas;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/checkbox/proyecto/{idProyecto}")
	public List<String> obtenerCantidadCheckBox(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxPorProyecto(Integer.parseInt(idProyecto));
		List<String> campos = new ArrayList<String>();
		for(Camposproyecto item : lista) {
			campos.add(item.getCampo());
		}
		return campos;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/checkboxevidencia/proyecto/{idProyecto}/{idInventario}")
	public List<Fotoevidencia> obtenerCantidadCheckBoxEvidencia(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idInventario") String idInventario
			) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxEvidenciaPorProyecto(Integer.parseInt(idProyecto));
		
		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for(Camposproyecto item : lista) {
			List<Fotoevidencia> evidencia = this.fotoEvidencia.obtenerFotoPorInventarioCampo(Integer.parseInt(idInventario),item.getIdcamposproyecto());
			
			if(evidencia.isEmpty()) {
				evidencias.add(new Fotoevidencia(0,"","","",new Usuario(),new Inventario(Integer.parseInt(idInventario),new Date(),"","",new Proyecto(0, new Date(),"",new Tipoproyecto(0, ""))),new Camposproyecto(item.getIdcamposproyecto(),"",item.getCampo(),"","",0,"","", new Agrupacion(), new Proyecto(),"")));
			}else {
				for(Fotoevidencia foto : evidencia) {
					evidencias.add(foto);
				}
			}
		}
		System.out.println(evidencias.size());
		return evidencias;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/foto/proyecto/{idProyecto}/{idInventario}")
	public List<Fotoevidencia> obtenerCantidadFotos(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idInventario") String idInventario
			) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFotoPorProyecto(Integer.parseInt(idProyecto));
		
		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for(Camposproyecto item : lista) {
			List<Fotoevidencia> evidencia = this.fotoEvidencia.obtenerConcidencia(item.getCampo(), item.getIdcamposproyecto(), Integer.parseInt(idInventario));
			
			if(evidencia.isEmpty()) {
				evidencias.add(new Fotoevidencia(0,item.getCampo(),"","",new Usuario(),new Inventario(0,new Date(),"","", new Proyecto(0,new Date(),"", new Tipoproyecto())),new Camposproyecto(item.getIdcamposproyecto())));
			}else {
				evidencias.add(evidencia.get(0));
			}
		}
		return evidencias;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/fotos/proyecto/{idProyecto}/{idinventario}")
	public List<Fotoevidencia> obtenerFotosPorProyecto(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idinventario") String idinventario
			) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFotoPorProyecto(Integer.parseInt(idProyecto));
		List<Fotoevidencia> listaFotos = new ArrayList<Fotoevidencia>();
		for(Camposproyecto campo : lista) {
			List<Fotoevidencia> aux = this.fotoEvidencia.obtenerFotoPorInventarioCampo(Integer.parseInt(idinventario), campo.getIdcamposproyecto());
			if(aux.isEmpty()) {
				listaFotos.add(new Fotoevidencia(0,campo.getCampo(),"","", new Usuario(),
						new Inventario(0, new Date(), "", "", new Proyecto(0, new Date(), "", new Tipoproyecto(0,""))),
						new Camposproyecto(0, "", "","", "",0,"","", new Agrupacion(0,""), new Proyecto(0, new Date(), "", new Tipoproyecto(0,"")),"")
						));
			}else {
				listaFotos.add(aux.get(0));
			}

		}
		
		return listaFotos;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/firmas/proyecto/{idProyecto}/{idinventario}")
	public List<firmasdocumento> obtenerFirmasPorProyecto(
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idinventario") String idinventario
			) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFirmasPorProyecto(Integer.parseInt(idProyecto));
		List<firmasdocumento> listaFirmas = new ArrayList<firmasdocumento>();
		for(Camposproyecto campo : lista) {
			List<firmasdocumento> aux = this.firmaDocumento.obtenerFirmaPorInventarioCampo(Integer.parseInt(idinventario), campo.getIdcamposproyecto());
			if(aux.isEmpty()) {
				listaFirmas.add(new firmasdocumento(0, campo.getCampo(), "", new Camposproyecto(campo.getIdcamposproyecto(), "", "","","", 0,"","", new Agrupacion(0,""), new Proyecto(0, new Date(), "", new Tipoproyecto(0,"")),""), new Inventario(0, new Date(), "", "", new Proyecto(0, new Date(), "", new Tipoproyecto(0,"")))));
			}else {
				listaFirmas.add(aux.get(0));
			}
		}
		
		return listaFirmas;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/campos/por/proyecto/{idProyecto}")
	public List<Camposproyecto> getCamposProyectoBusquedaMovil(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));
		return lista;
	}

}
