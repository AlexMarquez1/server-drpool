package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.ICamposBusquedaDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.ICatalogoDAO;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.CamposBusqueda;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Catalogo;
import com.isae.drpool.drpool.entity.CatalogoAux;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Tipoproyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.firmasdocumento;
import com.isae.drpool.drpool.utils.ObtenerDatos;

@RestController
public class ObtenerDatosController {

	@Autowired
	private IValoresDAO valores;

	@Autowired
	private ICamposProyectoDAO camposProyecto;

	@Autowired
	private IFirmaDocumentosDAO firmaDocumento;

	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;

	@Autowired
	private ICatalogoDAO catalogo;
	
	@Autowired
	private ICamposBusquedaDAO camposBusqueda;
	
	@Autowired
	private IProyectoDAO proyecto;

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datoscompletos/registro/{idRegistro}/{idProyecto}/{idUsuario}")
	public Map<String, Object> obtenerDatos(@PathVariable(value = "idRegistro") String idRegistro,
			@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "idUsuario") String idUsuario) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, CatalogoAux> catalogos = new HashMap<String, CatalogoAux>();
		
		Proyecto pro = this.proyecto.findById(Integer.parseInt(idProyecto)).get();
		
		String idPro = idProyecto;

		if(pro.getProyecto().contains("BITACORA DIARIA")) {
			idPro = "231";

		}else if(pro.getProyecto().contains("REPORTE SEMANAL")) {
			idPro = "232";
		}
		
		List<String> listaCatalogos = getCatalogosProyecto(idPro);
		
		List<CamposBusqueda> listaCamposBusqueda = new ArrayList<CamposBusqueda>();
		listaCamposBusqueda = this.camposBusqueda.obtenerCamposBusquedaPorProyecto(Integer.parseInt(idProyecto));
		for (String item : listaCatalogos) {
			catalogos.put(item, getDatosCatalogoProyecto(idPro, item, idUsuario));
		}
		ObtenerDatos obtenerDatos = new ObtenerDatos();
		
		respuesta.put("listaAgrupaciones", obtenerDatos.getDatosRegistro(idRegistro, idProyecto,this.valores));
		respuesta.put("listaCamposBusqueda", listaCamposBusqueda);
		respuesta.put("catalogos", catalogos);
//		System.out.println("Catalogos"+catalogos);
		respuesta.put("respuestaFirmas", obtenerCantidadFirmas(idProyecto, idRegistro));
		respuesta.put("respuestaFotos", obtenerCantidadFotos(idProyecto, idRegistro));
		respuesta.put("respuestaCheckbox", obtenerCantidadCheckBox(idProyecto));
		respuesta.put("respuestaCheckboxEvidencia", obtenerCantidadCheckBoxEvidencia(idProyecto, idRegistro));

		return respuesta;

	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/datoscompletos/registro/{idRegistro}/{idProyecto}")
	public Map<String, Object> obtenerDatosPorUsuarios(@PathVariable(value = "idRegistro") String idRegistro,
			@PathVariable(value = "idProyecto") String idProyecto, @RequestBody List<Usuario> usuarios) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, CatalogoAux> catalogos = new HashMap<String, CatalogoAux>();
		List<String> listaCatalogos = getCatalogosProyecto(idProyecto);
		List<CamposBusqueda> listaCamposBusqueda = new ArrayList<CamposBusqueda>();
		listaCamposBusqueda = this.camposBusqueda.obtenerCamposBusquedaPorProyecto(Integer.parseInt(idProyecto));
		for(Usuario usuario : usuarios) {
			for (String item : listaCatalogos) {
				catalogos.put(item, getDatosCatalogoProyecto(idProyecto, item, String.valueOf(usuario.getIdusuario())));
			}
		}
		ObtenerDatos obtenerDatos = new ObtenerDatos();
		
		respuesta.put("listaAgrupaciones", obtenerDatos.getDatosRegistro(idRegistro, idProyecto,this.valores));
		respuesta.put("listaCamposBusqueda", listaCamposBusqueda);
		respuesta.put("catalogos", catalogos);
//		System.out.println("Catalogos"+catalogos);
		respuesta.put("respuestaFirmas", obtenerCantidadFirmas(idProyecto, idRegistro));
		respuesta.put("respuestaFotos", obtenerCantidadFotos(idProyecto, idRegistro));
		respuesta.put("respuestaCheckbox", obtenerCantidadCheckBox(idProyecto));
		respuesta.put("respuestaCheckboxEvidencia", obtenerCantidadCheckBoxEvidencia(idProyecto, idRegistro));

		return respuesta;

	}

//	private List<Agrupaciones> getDatosRegistro(String idRegistro, String idProyecto) {
//		System.out.println(idRegistro);
//		System.out.println(idProyecto);
//		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
//		List<String> agrupaciones = new ArrayList<String>();
//		List<Campos> campos = new ArrayList<Campos>();
//		Campos campo = new Campos();
//		List<Valore> lista = this.valores.obtenerDatosCampoPorProyecto(Integer.parseInt(idRegistro),
//				Integer.parseInt(idProyecto));
//		for (Valore item : lista) {
//			campo = new Campos();
//			campo.setIdCampo(item.getCamposproyecto().getIdcamposproyecto());
//			campo.setNombreCampo(item.getCamposproyecto().getCampo());
//			campo.setValidarduplicidad(item.getCamposproyecto().getValidarduplicidad());
//			campo.setTipoCampo(item.getCamposproyecto().getTipocampo());
//			campo.setRestriccion(item.getCamposproyecto().getPattern());
//			campo.setAlerta(item.getCamposproyecto().getAlerta());
//			campo.setAgrupacion(item.getCamposproyecto().getAgrupacion().getAgrupacion());
//			campo.setLongitud(item.getCamposproyecto().getLongitud());
//			campo.setValor(item.getValor());
//			campo.setPordefecto(item.getCamposproyecto().getPordefecto());
//
//			if (agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
//				agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
//				campos.add(campo);
//				listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(),
//						item.getCamposproyecto().getAgrupacion().getAgrupacion(), campos));
//			} else {
//				if (agrupaciones.contains(item.getCamposproyecto().getAgrupacion().getAgrupacion())) {
//					List<Campos> aux = new ArrayList<Campos>();
//					aux = listaAgrupaciones
//							.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion()))
//							.getCampos();
//					aux.add(campo);
//					listaAgrupaciones
//							.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion()))
//							.setCampos(aux);
//				} else {
//					agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
//					campos = new ArrayList<Campos>();
//					campos.add(campo);
//					listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(),
//							item.getCamposproyecto().getAgrupacion().getAgrupacion(), campos));
//				}
//			}
//		}
//		return listaAgrupaciones;
//	}

	private List<String> getCatalogosProyecto(String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampo(Integer.parseInt(idProyecto));
		List<String> catalogos = new ArrayList<String>();
		if (!lista.isEmpty()) {
			for (Camposproyecto item : lista) {
				catalogos.add(item.getCampo());
			}
		}
		return catalogos;
	}

	private List<firmasdocumento> obtenerCantidadFirmas(String idProyecto, String idInventario) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFirmasPorProyecto(Integer.parseInt(idProyecto));

		List<firmasdocumento> firmas = new ArrayList<firmasdocumento>();
		for (Camposproyecto campo : lista) {
			List<firmasdocumento> firma = this.firmaDocumento.obtenerConcidencia(campo.getCampo(),
					campo.getIdcamposproyecto(), Integer.parseInt(idInventario));
			if (firma.isEmpty()) {
				firmas.add(new firmasdocumento(0, campo.getCampo(), "", new Camposproyecto(campo.getIdcamposproyecto()),
						new Inventario(Integer.parseInt(idInventario))));
			} else {
				firmas.add(firma.get(0));
			}
		}
		return firmas;
	}

	private List<Fotoevidencia> obtenerCantidadFotos(String idProyecto, String idInventario) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFotoPorProyecto(Integer.parseInt(idProyecto));

		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for (Camposproyecto item : lista) {
			List<Fotoevidencia> evidencia = this.fotoEvidencia.obtenerConcidencia(item.getCampo(),
					item.getIdcamposproyecto(), Integer.parseInt(idInventario));

			if (evidencia.isEmpty()) {
				evidencias.add(new Fotoevidencia(0, item.getCampo(), "", "",
						new Usuario(),
						new Inventario(0, new Date(), "", "", new Proyecto(0, new Date(), "", new Tipoproyecto())),
						new Camposproyecto(item.getIdcamposproyecto())));
			} else {
				evidencias.add(evidencia.get(0));
			}
		}
		return evidencias;
	}

	private List<String> obtenerCantidadCheckBox(String idProyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxPorProyecto(Integer.parseInt(idProyecto));
		List<String> campos = new ArrayList<String>();
		for (Camposproyecto item : lista) {
			campos.add(item.getCampo());
		}
		return campos;
	}

	private List<Fotoevidencia> obtenerCantidadCheckBoxEvidencia(String idProyecto, String idInventario) {
		List<Camposproyecto> lista = this.camposProyecto
				.obtenerSoloCheckBoxEvidenciaPorProyecto(Integer.parseInt(idProyecto));

		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for (Camposproyecto item : lista) {
			List<Fotoevidencia> evidencia = this.fotoEvidencia
					.obtenerFotoPorInventarioCampo(Integer.parseInt(idInventario), item.getIdcamposproyecto());

			if (evidencia.isEmpty()) {
				evidencias.add(
						new Fotoevidencia(0, "", "", "", new Usuario(),
								new Inventario(Integer.parseInt(idInventario), new Date(), "", "",
										new Proyecto(0, new Date(), "", new Tipoproyecto(0, ""))),
								new Camposproyecto(item.getIdcamposproyecto(), "",item.getCampo(),"", "",0, "", "",
										new Agrupacion(), new Proyecto(),"")));
			} else {
				for (Fotoevidencia foto : evidencia) {
					evidencias.add(foto);
				}
			}
		}
		System.out.println("Evidencias Checkbox: " + evidencias.size());
		return evidencias;
	}

	public CatalogoAux getDatosCatalogoProyecto(String idProyecto, String tipoCatalogo, String idUsuario) {
		List<Catalogo> listaCatalogos = this.catalogo.obtenerDatosCatalogoProyectoSinUsuario(tipoCatalogo,
				Integer.parseInt(idProyecto));
		List<Catalogo> ListaCatalogo = new ArrayList<Catalogo>();
		
		List<Catalogo> listaCatalogosUsuario = new ArrayList<Catalogo>();
		
		System.out.println("IDP: " + idProyecto);
		if(idProyecto.equals("231")) {
			System.out.println("TIPO DE CATALOGO: " + tipoCatalogo);
			listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipoCatalogo,
					Integer.parseInt(idProyecto));
		}else if(idProyecto.equals("232")) {
			listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipoCatalogo,
					Integer.parseInt(idProyecto));
		}else {
			listaCatalogosUsuario = this.catalogo.obtenerDatosCatalogoProyectoUsuario(tipoCatalogo,
					Integer.parseInt(idProyecto), Integer.parseInt(idUsuario));
			ListaCatalogo = this.catalogo.obtenerDatosCatalogoProyectoUsuarioNulo(tipoCatalogo, Integer.parseInt(idProyecto));
		}
		
		listaCatalogosUsuario.addAll(ListaCatalogo);
		if (!listaCatalogosUsuario.isEmpty()) {
			listaCatalogos.addAll(listaCatalogosUsuario);
		}
		CatalogoAux catalogo = new CatalogoAux();
		List<String> lista = new ArrayList<String>();
		if (!listaCatalogos.isEmpty()) {
			catalogo.setProyecto(listaCatalogos.get(0).getProyecto());
			catalogo.setTipoCatalogo(listaCatalogos.get(0).getTipocatalogoBean().getTipo());
			for (Catalogo item : listaCatalogos) {
				lista.add(item.getCatalogo());
			}
			catalogo.setCatalogo(lista);
		}

		return catalogo;
	}

}
