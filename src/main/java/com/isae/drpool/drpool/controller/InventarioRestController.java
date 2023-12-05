package com.isae.drpool.drpool.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.reflect.TypeToken;
import com.google.firebase.cloud.StorageClient;
import com.google.gson.Gson;
import com.isae.drpool.drpool.dao.IAsignarRegistroDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.ICatalogoDAO;
import com.isae.drpool.drpool.dao.IDocumentoGenerado;
import com.isae.drpool.drpool.dao.IEnProcesoDAO;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IPendienteDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.IUsuarioDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.dao.IVistaDatosISSSTE;
import com.isae.drpool.drpool.dao.IVistaRegistroBusquedaDAO;
import com.isae.drpool.drpool.entity.Agrupacion;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Asignacionregistro;
import com.isae.drpool.drpool.entity.Campos;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Catalogo;
import com.isae.drpool.drpool.entity.CatalogoAux;
import com.isae.drpool.drpool.entity.Documentogenerado;
import com.isae.drpool.drpool.entity.Evidencia;
import com.isae.drpool.drpool.entity.Firma;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Tipoproyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.entity.VistaDatosISSSTE;
import com.isae.drpool.drpool.entity.VistaRegistroBusqueda;
import com.isae.drpool.drpool.entity.firmasdocumento;
import com.isae.drpool.drpool.utils.CombinarPDF;
import com.isae.drpool.drpool.utils.GenerarDocumentosUtils;
import com.isae.drpool.drpool.utils.LeerDocumentoExcel;
import com.isae.drpool.drpool.utils.PagingList;

import ij.ImagePlus;
import ij.process.ImageProcessor;

@RestController
//@RequestMapping("/inventario")
public class InventarioRestController {

	@Autowired
	private IInventarioDAO inventario;

	@Autowired
	private IVistaRegistroBusquedaDAO vistaRegistroBusqueda;

	@Autowired
	private ICamposProyectoDAO camposProyecto;

	@Autowired
	private IValoresDAO valores;

	@Autowired
	private IProyectoDAO proyecto;

	@Autowired
	private ICatalogoDAO catalogo;

	@Autowired
	private IFirmaDocumentosDAO firmaDocumento;

	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;

	@Autowired
	private IAsignarRegistroDAO asignacionRegistro;

	@Autowired
	private IDocumentoGenerado documentoGenerado;

	@Autowired
	private IVistaDatosISSSTE vistaDatosIssste;

	@Autowired
	private IEnProcesoDAO enProceso;

	@Autowired
	private IPendienteDAO pendiente;
	
	@Autowired
	private IUsuarioDAO usuario;
	
	@Autowired
	private IFirmaDocumentosDAO firma;

	@CrossOrigin(origins = "*")
	@PostMapping("/registrar/registro/plantilla/{idProyecto}")
	public List<String> registrarRegistroPlantilla(@PathVariable(value = "idProyecto") String idProyecto,
			@RequestBody List<Inventario> listaRegistro) {
		List<String> respuesta = new ArrayList<>();
		List<String> folios = new ArrayList<>();
		List<Inventario> listaInventario = new ArrayList<Inventario>();
		int size = listaRegistro.size();
		for (int i = 0; i < size; i++) {
			folios.add(listaRegistro.get(i).getFolio());
			listaInventario.add(new Inventario(listaRegistro.get(i).getIdinventario(), new Date(),
					listaRegistro.get(i).getFolio().isEmpty() ? "NUEVO REGISTRO" : listaRegistro.get(i).getFolio(),
					"NUEVO", listaRegistro.get(i).getProyecto()));
		}
		List<Inventario> guardados = this.inventario.saveAll(listaInventario);

//		List<Inventario> lista = this.inventario.obtenerFoliosRegistrados(folios);

		for (Inventario item : guardados) {
			respuesta.add(String.valueOf(item.getIdinventario()));
		}
		System.out.println("Registros agregados");

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/registrar/inventario/{folio}/{idProyecto}")
	public List<String> registrarInventario(@PathVariable(value = "folio") String folio,
			@PathVariable(value = "idProyecto") String idProyecto) {
		List<String> respuesta = new ArrayList<>();

		List<String> comprobacion = new ArrayList<String>();
		Inventario inventario = new Inventario();

		comprobacion = this.inventario.comprobarFolio(folio, Integer.parseInt(idProyecto));

		if (!comprobacion.isEmpty()) {
			respuesta.add("existe");
		} else {
			inventario.setFolio(folio.toUpperCase());
			inventario.setProyecto(new Proyecto(Integer.parseInt(idProyecto)));
			inventario.setFechacreacion(new Date());
			inventario.setEstatus("NUEVO");

			this.inventario.save(inventario);
			int obtenerId = this.inventario.obtenerUltimoId();

			respuesta.add(String.valueOf(obtenerId));
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registros/{idProyecto}")
	public List<Inventario> getRegistros(@PathVariable(value = "idProyecto") String idProyecto) {
		List<Inventario> lista = this.inventario.obtenerPorIdProyecto(Integer.parseInt(idProyecto));
		return lista;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registrosalberca/{idalberca}")
	public List<Inventario> getRegistrosAlberca(@PathVariable(value = "idalberca") String idalberca) {
		List<Inventario> lista = this.inventario.obtenerIdInventario(Integer.parseInt(idalberca));
		return lista;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registros/busqueda/{idProyecto}/{busqueda}")
	public List<Inventario> getRegistrosBusqueda(@PathVariable(value = "idProyecto") String idProyecto,
			@PathVariable(value = "busqueda") String busqueda) {
		List<VistaRegistroBusqueda> lista = this.vistaRegistroBusqueda.datosBusqueda(busqueda,
				Integer.parseInt(idProyecto));
		List<Inventario> listaRegistro = new ArrayList<Inventario>();

		for (VistaRegistroBusqueda item : lista) {
			listaRegistro.add(new Inventario(item.getIdinventario(),item.getFechacreacionregistro(),item.getFolio(),"",item.getProyecto()));
		}

		return listaRegistro;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/folios/registrados/{idProyecto}")
	public List<String> getFoliosRegistrados(@PathVariable(value = "idProyecto") String idProyecto,
			@RequestBody List<String> folios) {
		List<String> respuesta = new ArrayList<>();
		List<Inventario> respuestaFolios = this.inventario.obtenerFoliosRegistrados(folios);

		if (!respuestaFolios.isEmpty()) {
			for (int i = 0; i < respuestaFolios.size(); i++) {
				for (int j = 0; j < folios.size(); j++) {
					if (respuestaFolios.get(i).getFolio().equalsIgnoreCase(folios.get(j))) {
						respuesta.add(folios.get(j));
					}
				}
			}
		} else {
			respuesta.add("vacio");
			System.out.println("Arignando Vacio");
		}

		System.out.println("Respuesta: " + respuesta);

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/registros/dashboard")
	public List<Inventario> obtenerInventarioDashboard(@RequestBody List<String> proyectos) {
		List<Inventario> lista = new ArrayList<Inventario>();
		if (!proyectos.isEmpty()) {
			for (String proyecto : proyectos) {
				List<Inventario> respuesta = this.inventario
						.obtenerPorIdProyecto(this.proyecto.obtenerProyectoPorNombre(proyecto).getIdproyecto());
				if (!respuesta.isEmpty()) {
					for (Inventario inventario : respuesta) {
						lista.add(inventario);
					}
				}
			}
		}

		return lista;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/inventarios/idcampo/valor/{idcampo}/{dato}/{idusuario}")
	public List<Inventario> obtenerInventarioPorValorCampo(@PathVariable(value= "idcampo") String idcampo, @PathVariable(value="dato") String dato, @PathVariable(value="idusuario") String idUsuario) {
//		List<Inventario> lista = new ArrayList<Inventario>();
//		List<Valore> listaValores = this.valores.obtenerInventarioPorValorCampo(dato, Integer.parseInt(idcampo));
//		List<Integer> listaIds = new ArrayList<Integer>();
//		listaValores.forEach((item) -> listaIds.add(item.getInventario().getIdinventario()));
//		lista = this.inventario.findAllById(listaIds);
		
		List<Inventario> lista = new ArrayList<Inventario>();
		List<Object> listaValores = this.valores.obtenerInventarioPorValorCampo(dato, Integer.parseInt(idcampo), Integer.parseInt(idUsuario));
		for(Object item : listaValores) {
			Object [] inventario  = (Object[]) item;
			lista.add(new Inventario((int) inventario[0], null, (String) inventario[1], (String) inventario[4],new Proyecto((int) inventario[2])));
		}

		return lista;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/generar/documento/registros")
	public List<Integer> descargarRegistros(@RequestBody List<Inventario> listaRegistros) {
		List<Integer> lista = new ArrayList<Integer>();
		List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
		List<String> auxProyecto = new ArrayList<String>();
		Map<String, List<Camposproyecto>> camposProyectos = new HashMap<>();
		Map<String, List<Inventario>> registros = new HashMap<>();
		List<List<Valore>> valores = new ArrayList<List<Valore>>();

		System.out.println("Creando documento");
		for (Inventario registro : listaRegistros) {
			if (!auxProyecto.contains(registro.getProyecto().getProyecto())) {
				listaProyectos.add(registro.getProyecto());
				auxProyecto.add(registro.getProyecto().getProyecto());
			}
		}
		int aux = 0;

		for (Proyecto proyecto : listaProyectos) {
			camposProyectos.put(proyecto.getProyecto(),
					this.camposProyecto.obtenerCatalogoCampoPorProyecto(proyecto.getIdproyecto()));
			List<Inventario> auxRegistro = new ArrayList<Inventario>();
			for (Inventario registro : listaRegistros) {
				if (registro.getProyecto().getProyecto().equalsIgnoreCase(proyecto.getProyecto())) {
					auxRegistro.add(registro);
				}
			}
			registros.put(proyecto.getProyecto(), auxRegistro);
			List<Valore> valor = this.valores.obtenerDatosCampoPorProyecto(proyecto.getIdproyecto());
			System.out.println("Consultando valores: " + aux++);
			valores.add(valor);
		}
		try {
			System.out.println("Lista de proyecto: " + auxProyecto);
			GenerarDocumentosUtils generarDocumento = new GenerarDocumentosUtils();

			ByteArrayInputStream in = generarDocumento.generarExcelRegistros(listaProyectos, camposProyectos, registros,
					this.valores);
			System.out.println("Documento generado");
			byte[] bytes = IOUtils.toByteArray(in);
			for (byte b : bytes) {
				lista.add((int) b);
			}
			System.out.println("Documento creado y listo para mandar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/generar/documento/registros/{idproyecto}")
	public List<Integer> descargarRegistros(@RequestBody List<Inventario> listaRegistros,
			@PathVariable(value = "idproyecto") String idProyecto) {
		System.out.println("Creando documento");
		List<VistaDatosISSSTE> respuesta = new ArrayList<VistaDatosISSSTE>();
		List<Integer> lista = new ArrayList<Integer>();

//		List<Inventario> listaInventarios = new ArrayList<Inventario>();
		List<Integer> listaIds = new ArrayList<Integer>();

		
		for (Inventario item : listaRegistros) {
//			listaInventarios.add(new Inventario(item.getIdRegistro()));
			listaIds.add(item.getIdinventario());
		}
		
		System.out.println("Inicia la carga de datos");
		long  startTime = System.currentTimeMillis();
		respuesta = this.vistaDatosIssste.obtenerDatosPorIdInventario(listaIds);
		long finishedTime = System.currentTimeMillis();
        System.out.println("Tiempo transcurrido: " + (finishedTime - startTime)/1000 + " Segundos");
        System.out.println("Termina la carga de datos");
        
		try {
			GenerarDocumentosUtils generarDocumento = new GenerarDocumentosUtils();

			ByteArrayInputStream in = generarDocumento.generarExcelRegistrosISSSTE(respuesta);
			byte[] bytes = IOUtils.toByteArray(in);
			for (byte b : bytes) {
				lista.add((int) b);
			}
			
			System.out.println("Documento creado y listo para mandar");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/datos/nuevo/registro")
	public Map<String, Object> obtenerDatosNuevoRegistro(@RequestBody Proyecto proyecto) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		respuesta.put("listaAgrupaciones", getCamposProyecto(proyecto));

		respuesta.put("catalogos", getCatalogosProyecto(proyecto));

		respuesta.put("respuestaFirmas", obtenerCantidadFirmas(proyecto));

		respuesta.put("respuestaFotos", obtenerCantidadFotos(proyecto));

		respuesta.put("respuestaCheckBox", obtenerCantidadCheckBox(proyecto));

		respuesta.put("respuestaCheckBoxEvidencia", obtenerCantidadCheckBoxEvidencia(proyecto));

		System.out.println(respuesta.get("respuestaCheckBoxEvidencia"));

		return respuesta;
	}

	public List<Agrupaciones> getCamposProyecto(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampoPorProyecto(proyecto.getIdproyecto());
		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
		List<String> agrupaciones = new ArrayList<String>();
		List<Campos> campos = new ArrayList<Campos>();
		Campos campo = new Campos();
		for (Camposproyecto item : lista) {
			campo = new Campos();
			campo.setIdCampo(item.getIdcamposproyecto());
			campo.setNombreCampo(item.getCampo());
			campo.setTipoCampo(item.getTipocampo());
			campo.setRestriccion(item.getPattern());
			campo.setEditable(item.getEditable());
			campo.setAlerta(item.getAlerta());
			campo.setAgrupacion(item.getAgrupacion().getAgrupacion());
			campo.setLongitud(item.getLongitud());
			campo.setPordefecto(item.getPordefecto());
			campo.setValidarduplicidad(item.getValidarduplicidad());

			if (agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
				agrupaciones.add(item.getAgrupacion().getAgrupacion());
				campos.add(campo);
				listaAgrupaciones.add(new Agrupaciones(item.getAgrupacion().getIdagrupacion(),
						item.getAgrupacion().getAgrupacion(), campos));
			} else {
				if (agrupaciones.contains(item.getAgrupacion().getAgrupacion())) {
					List<Campos> aux = new ArrayList<Campos>();
					aux = listaAgrupaciones.get(agrupaciones.indexOf(item.getAgrupacion().getAgrupacion())).getCampos();
					aux.add(campo);
					listaAgrupaciones.get(agrupaciones.indexOf(item.getAgrupacion().getAgrupacion())).setCampos(aux);
				} else {
					agrupaciones.add(item.getAgrupacion().getAgrupacion());
					campos = new ArrayList<Campos>();
					campos.add(campo);
					listaAgrupaciones.add(new Agrupaciones(item.getAgrupacion().getIdagrupacion(),
							item.getAgrupacion().getAgrupacion(), campos));
				}
			}
		}
		return listaAgrupaciones;
	}

	private Map<String, CatalogoAux> getCatalogosProyecto(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCatalogoCampo(proyecto.getIdproyecto());
		Map<String, CatalogoAux> respuesta = new HashMap<String, CatalogoAux>();
		if (!lista.isEmpty()) {
			for (Camposproyecto item : lista) {
				respuesta.put(item.getCampo(), getDatosCatalogoProyecto(proyecto, item.getCampo()));
			}
		}
		return respuesta;
	}

	private CatalogoAux getDatosCatalogoProyecto(Proyecto proyecto, String tipoCatalogo) {
		Proyecto pro = this.proyecto.findById(proyecto.getIdproyecto()).get();
		List<Catalogo> listaCatalogos = new ArrayList<Catalogo>();
		if(pro.getProyecto().contains("BITACORA DIARIA")) {
			listaCatalogos = this.catalogo.obtenerDatosCatalogoProyecto(tipoCatalogo,
					231);
		}else if(pro.getProyecto().contains("REPORTE SEMANAL")) {
			listaCatalogos = this.catalogo.obtenerDatosCatalogoProyecto(tipoCatalogo,
					232);
		}else {
			listaCatalogos = this.catalogo.obtenerDatosCatalogoProyecto(tipoCatalogo,
					proyecto.getIdproyecto());
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

	private List<firmasdocumento> obtenerCantidadFirmas(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFirmasPorProyecto(proyecto.getIdproyecto());

		List<firmasdocumento> firmas = new ArrayList<firmasdocumento>();
		for (Camposproyecto campo : lista) {
			firmas.add(new firmasdocumento(0, campo.getCampo(), "", new Camposproyecto(campo.getIdcamposproyecto()),
					new Inventario(0)));
		}
		return firmas;
	}

	public List<Fotoevidencia> obtenerCantidadFotos(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerFotoPorProyecto(proyecto.getIdproyecto());

		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for (Camposproyecto item : lista) {
			evidencias.add(new Fotoevidencia(0, item.getCampo(), "", "",
					new Usuario(),
					new Inventario(0, new Date(), "", "", new Proyecto(0, new Date(), "", new Tipoproyecto())),
					new Camposproyecto(item.getIdcamposproyecto())));
		}
		return evidencias;
	}

	private List<String> obtenerCantidadCheckBox(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxPorProyecto(proyecto.getIdproyecto());
		List<String> campos = new ArrayList<String>();
		for (Camposproyecto item : lista) {
			campos.add(item.getCampo());
		}
		return campos;
	}

	public List<Fotoevidencia> obtenerCantidadCheckBoxEvidencia(Proyecto proyecto) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxEvidenciaPorProyecto(proyecto.getIdproyecto());

		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for (Camposproyecto item : lista) {
			evidencias
					.add(new Fotoevidencia(0, "", "", "", new Usuario(),
							new Inventario(0, new Date(), "", "",
									new Proyecto(0, new Date(), "", new Tipoproyecto(0, ""))),
							new Camposproyecto(item.getIdcamposproyecto(), "", item.getCampo(),"", "", 0, "", "",
									new Agrupacion(), new Proyecto(), "")));
		}
		System.out.println(evidencias.size());
		return evidencias;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/excel/datos/proyecto/{idusuario}/{idproyecto}")
	public String obtenerExcelDatosProyecto(@RequestBody List<Integer> documento,
			@PathVariable(value = "idusuario") String idUsuario,
			@PathVariable(value = "idproyecto") String idProyecto) {
		String respuesta = "correcto";

		byte[] byteDocumento = new byte[documento.size()];
		for (int i = 0; i < documento.size(); i++) {
			byteDocumento[i] = (byte) documento.get(i).intValue();
		}

		File tempFile;
		try {
			tempFile = File.createTempFile("documento", ".xlsx", null);
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(byteDocumento);
			System.out.println("Direccion temporal del archivo: " + tempFile.getPath());

			LeerDocumentoExcel leerDocumentoExcel = new LeerDocumentoExcel();

			List<Camposproyecto> listaCampos = this.camposProyecto
					.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));
			Proyecto proyecto = this.proyecto.obtenerProyectoPorId(Integer.parseInt(idProyecto));

			// Valores obtenidos en la respuesta:
			// listaFolios
			// listaValores
			Map<String, Object> respuestaDocumento = leerDocumentoExcel.leerDocumento(tempFile, listaCampos, proyecto);
			List<Inventario> listaInventarios = (List<Inventario>) respuestaDocumento.get("listaInventarios");
			List<Valore> listaValores = (List<Valore>) respuestaDocumento.get("listaValores");
			List<String> listaFolios = (List<String>) respuestaDocumento.get("listaFolios");
			String estatus = (String) respuestaDocumento.get("Estatus");

			System.out.println("Folios a ingresar: " + listaFolios);
			System.out.println("Total Inventarios: " + listaInventarios.size());
			System.out.println("Total Valores: " + listaValores.size());
			System.out.println(estatus);

			if (estatus.equalsIgnoreCase("Correcto")) {
				System.out.println("Se leyo el documento Correctamente, listo para cargar los datos");
				List<Inventario> respuestaInventarios = this.inventario.obtenerFoliosRegistrados(listaFolios);

				if (respuestaInventarios.isEmpty()) {
					System.out.println("Sin folios Repetidos");

					// Guardando Inventario
					List<Inventario> inventariosGuardados = this.inventario.saveAll(listaInventarios);

					List<Valore> listaValoresRegistrar = new ArrayList<Valore>();

					int totalCampos = listaCampos.size();
					int indInventario = 0;
					int contadorCampos = 1;
					for (int i = 0; i < listaValores.size(); i++) {
						Valore valor = listaValores.get(i);
						if (contadorCampos == totalCampos) {

							contadorCampos = 1;

							listaValoresRegistrar.add(new Valore(0, valor.getValor(), valor.getCamposproyecto(),
									inventariosGuardados.get(indInventario)));
							indInventario++;
						} else {
							listaValoresRegistrar.add(new Valore(0, valor.getValor(), valor.getCamposproyecto(),
									inventariosGuardados.get(indInventario)));
							contadorCampos++;
						}
					}

					PagingList<Valore> paging = new PagingList<>(listaValoresRegistrar, 1000);

					while (paging.hasNext()) {
						this.valores.saveAll(paging.next());
					}

//					int insercionMaxima = 1000;
//					int contador =1;
//					List<Valore> listaAux = new ArrayList<Valore>();
////					long inicio = System.currentTimeMillis();
//					long fin;
//					double tiempo;
//					for(int i = 0; i < listaValoresRegistrar.size(); i++) {
//						
//						if(contador == insercionMaxima) {
//							// Guardando Valores 
//							listaAux.add(listaValoresRegistrar.get(i));
//							this.valores.saveAll(listaAux);
////							fin = System.currentTimeMillis();
////							tiempo = (double) ((fin - inicio)/1000);
////							System.out.println("Tiempo: "+tiempo+ "Segundos");
//							System.out.println("Se insertaron: "+contador);
//							contador = 1;
//							listaAux = new ArrayList<Valore>();
////							inicio = System.currentTimeMillis();
//						}else {
//							contador++;
//							listaAux.add(listaValoresRegistrar.get(i));
//						}
//					}
//					
//					if(contador <= 1000) {
//						this.valores.saveAll(listaAux);
////						fin = System.currentTimeMillis();
////						tiempo = (double) ((fin - inicio)/1000);
////						System.out.println("Tiempo: "+tiempo+ "Segundos");
//						System.out.println("Se insertaron: "+contador);
//					}

					List<Asignacionregistro> asignacionRegistro = new ArrayList<Asignacionregistro>();

					for (Inventario inventario : inventariosGuardados) {
						asignacionRegistro
								.add(new Asignacionregistro(0, new Usuario(Integer.parseInt(idUsuario)), inventario));
					}

					// Guardando Asignacion de Registros
					this.asignacionRegistro.saveAll(asignacionRegistro);

				} else {
					System.out.println("Folios Repetidos");
					respuesta = "Folios Repetidos>";
					for (Inventario inventario : respuestaInventarios) {
						respuesta += inventario.getFolio() + ">";
					}
				}
			} else if (estatus.equalsIgnoreCase("El documento no tiene el mismo formato")) {
				respuesta = estatus;
			} else if (estatus.contains("Error")) {
				System.out.println("Dentro del if error");
				respuesta = estatus;
			} else {
				respuesta = "Error al leer el documento";
			}

			System.out.println("Respuesta: " + estatus);

		} catch (IOException e) {
			e.printStackTrace();
			respuesta = "Error al leer el Excel";
			System.out.println("Error al leer el Excel");

		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/pdf/seleccionado/{proyecto}/{paginas}")
	public Map<String, Object> obtenerPDFCombinado(@PathVariable(value = "proyecto") String proyecto,
			@PathVariable(value = "paginas") String paginas, @RequestBody List<Integer> idRegistros) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		CombinarPDF combinarPdf = new CombinarPDF();
		List<String> documentos = new ArrayList<String>();
		List<Documentogenerado> listaDocumentos = this.documentoGenerado.obtenerDocumentosPorIds(idRegistros);

		for (Documentogenerado documento : listaDocumentos) {
			documentos.add(documento.getUrl());
		}

//		documentos.add("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Proyectos%2F66-FIRMAS%2F70-PRUEBA%20FIRMA%2FEvidencias%2F70-PRUEBA%20FIRMA?alt=media&token=a97dfdb5-016e-41e2-87bb-7cba2a83d61e");
//		documentos.add("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Proyectos%2F116-ISSSTE%2F18522-3031-39%2FEvidencias%2F18522-3031-39?alt=media&token=18522-3031-39.pdf");
//// Documento con error		documentos.add("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Proyectos%2F116-ISSSTE%2F15993-3239-100%2FEvidencias%2F15993-3239-100?alt=media&token=15993-3239-100.pdf");
//		documentos.add("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Proyectos%2F116-ISSSTE%2F18522-3031-39%2FEvidencias%2F18522-3031-39?alt=media&token=18522-3031-39.pdf");

		respuesta = combinarPdf.generarPdf(documentos, proyecto, paginas);
		if (((String) respuesta.get("respuesta")).contains("Error")) {
			String res = (String) respuesta.get("respuesta");

			res += " El documento del folio: "
					+ listaDocumentos.get(Integer.parseInt(res.split(":")[1])).getInventario().getFolio()
					+ " contiene errores, comprueba que el documento se pueda abrir antes de realizar esta accion";
			respuesta.put("respuesta", res);
		}

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/registro/eliminar/id/{idinventario}/{password}")
	public String eliminarRegistro(@PathVariable(value = "idinventario") String idinventario,@PathVariable(value = "password") String password) {
		String respuesta = "Error al eliminar el registro";
		if(password.equals("eliminarregistro170313")) {
			Inventario inventario = this.inventario.getById(Integer.parseInt(idinventario));
			System.out
					.println("Inventario a eliminar: " + inventario.getFolio() + " \n Id: " + inventario.getIdinventario());
			// Eliminando asignacion
			this.asignacionRegistro.eliminarPoridInventario(inventario.getIdinventario());
			//Eliminando firmas del documento
			this.firmaDocumento.eliminarPoridInventario(inventario.getIdinventario());
			//Eliminando fotos evidencia
			this.fotoEvidencia.eliminarPoridInventario(inventario.getIdinventario());
			//Eliminando estatus en proceso
			this.enProceso.eliminarInventario(inventario.getIdinventario());
			//Eliminando estatus pendiente
			this.pendiente.eliminarInventario(inventario.getIdinventario());
			//Eliminando valores
			this.valores.eliminarPoridInventario(inventario.getIdinventario());
			//Eliminando documento
			this.documentoGenerado.eliminarPoridInventario(inventario.getIdinventario());
			//Eliminando inventario
			this.inventario.deleteById(inventario.getIdinventario());

			// Eliminando archivos de Firebase
			try {
				//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
				URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
				FileInputStream serviceAccount = new FileInputStream(
						descargarConector(url, "google-service-descarga.json"));
				String direccionCarpeta = "Proyectos/" + inventario.getProyecto().getIdproyecto() + "-" +inventario.getProyecto().getProyecto() + "/"+ inventario.getIdinventario() + "-" + inventario.getFolio()+"/";
				System.out.println("Direccion a eliminar: " + direccionCarpeta);
				respuesta = eliminarObjetoFirebase(serviceAccount, "isae-de6da.appspot.com", direccionCarpeta);
			} catch (IOException e) {
				System.out.println("Error al eliminar los archivos");
			}
		}else {
			respuesta = "Acceso denegado, Contraseña no valida";
		}

		return respuesta;
	}

	private String eliminarObjetoFirebase(FileInputStream serviceAccount, String bucketName, String objectName) {
		String respuesta = "";
		try {

			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			Page<Blob> blobPage = storage.list(bucketName, Storage.BlobListOption.prefix(objectName));

			List<BlobId> blobIdList = new LinkedList<>();
			for (Blob blob : blobPage.iterateAll()) {
				blobIdList.add(blob.getBlobId());
			}

			
			if (!blobIdList.isEmpty()) {
				List<Boolean> deleted = storage.delete(blobIdList);

				if (deleted.contains(false)) {
					System.out.println("No se pudo eliminar la carpeta o algun archivo");
					respuesta = "No se pudo eliminar la carpeta o algun archivo";
				} else {
					System.out.println("Se elimino la carpeta correctamente");
					respuesta = "Se elimino la carpeta correctamente";
				}
			} else {
				System.out.println("No existen archivos");
				respuesta = "Se elimino la carpeta correctamente";
			}

			// Con esta linea solo se elimina un solo archivo
//			boolean deleted = storage.delete(bucketName, objectName);
//			
//			if(deleted) {
//				System.out.println("Se elimino la carpeta correctamente");
//			}else {
//				System.out.println("No se pudo eliminar la carpeta");
//			}

			System.out.println("Object " + objectName + " was deleted from " + bucketName);
			return respuesta;
		} catch (IOException e) {
			System.out.println("Error al acceder a Firebase");
			respuesta = "Error al eliminar el registro";
			return respuesta;
		}
	}

	private String descargarConector(URL url, String localFilename) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;

		String tempDir = System.getProperty("java.io.tmpdir");
		String outputPath = tempDir + "/" + localFilename;

		try {
			// connect
			URLConnection urlConn = url.openConnection();

			// get inputstream from connection
			is = urlConn.getInputStream();
			fos = new FileOutputStream(outputPath);

			// 4KB buffer
			byte[] buffer = new byte[4096];
			int length;

			// read from source and write into local file
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			return outputPath;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
	}

	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("isae-de6da")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/evidencias/inventarios")
	public List<Fotoevidencia> obtenerEvidencias(@RequestBody List<Integer> listaInventarios){
		List<Fotoevidencia> listaEvidencias = new ArrayList<Fotoevidencia>();
		List<Documentogenerado> listaDocumentosGenerados = new ArrayList<Documentogenerado>();
		//Cargar los documentos para poder descargar
		listaDocumentosGenerados = this.documentoGenerado.obtenerDocumentosPorIds(listaInventarios);
		listaEvidencias = this.fotoEvidencia.obtenerfotoPoridInventario(listaInventarios);
		for(Documentogenerado item : listaDocumentosGenerados) {
			listaEvidencias.add(new Fotoevidencia(0,item.getNombreDocumento(),item.getUrl(),"",new Usuario(),item.getInventario(),new Camposproyecto()));
		}
		
		System.out.println("Total de datos: "+ listaEvidencias.size());
		return listaEvidencias;
	}

	// ------------------------------Aplicacion
	// movil--------------------------------

	@CrossOrigin(origins = "*")
	@GetMapping("/registrar/asignar/nuevo/registro/{idproyecto}/{idusuario}")
	public List<Inventario> registrarNuevo(@PathVariable(value = "idproyecto") String idProyecto,
			@PathVariable(value = "idusuario") String idUsuario) {
		System.out.println("IDProyecto: " + idProyecto);
		System.out.println("IDUsuario: " + idUsuario);
		List<Inventario> inventarioIngresado = this.inventario.registrarAsignarRegistro(Integer.parseInt(idUsuario),
				Integer.parseInt(idProyecto));

		List<Camposproyecto> listaCamposProyecto = this.camposProyecto
				.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));

		List<Valore> listaValores = new ArrayList<Valore>();

		for (Camposproyecto item : listaCamposProyecto) {
			listaValores.add(new Valore(0, "", item, inventarioIngresado.get(0)));
		}

		this.valores.saveAll(listaValores);

		return inventarioIngresado;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/registrar/nuevo/registro/{idproyecto}/{idusuario}")
	public Map<String, Object> registrarNuevoInventario(@PathVariable(value = "idproyecto") String idProyecto,
			@PathVariable(value = "idusuario") String idUsuario) {

		Proyecto proyecto = this.proyecto.obtenerProyectoPorId(Integer.parseInt(idProyecto));
		Usuario usuario = this.usuario.getById(Integer.parseInt(idUsuario));

		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		List<Inventario> inventarioIngresado = this.inventario.registrarAsignarRegistro(Integer.parseInt(idUsuario),
				Integer.parseInt(idProyecto));
		int ultimoRegistroIngresado = this.asignacionRegistro.obtenerUltimoIngresadoPorUsuario(usuario.getIdusuario(), proyecto.getIdproyecto());
		ultimoRegistroIngresado++;
		String folioGenerado = usuario.getUsuario() + "-" + proyecto.getProyecto() + "-" + ultimoRegistroIngresado;
		this.inventario.cambiarFolio(folioGenerado, inventarioIngresado.get(0).getIdinventario());
		
		
		List<Agrupaciones> listaAgrupaciones = getCamposProyecto(proyecto);
		listaAgrupaciones.get(0).getCampos().get(0).setValor(folioGenerado);

		respuesta.put("listaAgrupaciones", listaAgrupaciones);

		respuesta.put("catalogos", getCatalogosProyecto(proyecto));

		respuesta.put("respuestaFirmas", obtenerCantidadFirmas(proyecto));

		respuesta.put("respuestaFotos", obtenerCantidadFotos(proyecto));

		respuesta.put("respuestaCheckBox", obtenerCantidadCheckBox(proyecto));

		respuesta.put("respuestaCheckBoxEvidencia", obtenerCantidadCheckBoxEvidencia(proyecto));

		respuesta.put("inventario", inventarioIngresado.get(0));

		List<Camposproyecto> listaCamposProyecto = this.camposProyecto
				.obtenerCatalogoCampoPorProyecto(Integer.parseInt(idProyecto));

		List<Valore> listaValores = new ArrayList<Valore>();

		for (Camposproyecto item : listaCamposProyecto) {
			if(item.getCampo().equalsIgnoreCase("FOLIO")) {
				listaValores.add(new Valore(0, folioGenerado, item, inventarioIngresado.get(0)));
			}else {
				listaValores.add(new Valore(0, "", item, inventarioIngresado.get(0)));
			}
		}

		this.valores.saveAll(listaValores);

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/folio/registro")
	public List<String> actualizarFolioInventario(@RequestBody Inventario registro) {
		List<String> respuesta = new ArrayList<>();

		this.inventario.cambiarFolio(registro.getFolio(), registro.getIdinventario());

		respuesta.add("Correcto");

		return respuesta;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/registro/id/{idRegistro}")
	public Inventario getRegistro(@PathVariable(value = "idRegistro") String idRegistro) {
		Inventario inventario = this.inventario.findById(Integer.parseInt(idRegistro)).get();
		System.out.println(inventario);
		return inventario;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/inventario/actualizar/valores" )
	public List<String> registrarCambiosMovil(@RequestBody Object body) {
		List<String> respuesta = new ArrayList<String>();
		respuesta.add("listo");
		ObjectMapper objectMapper = new ObjectMapper();
		int indAgrupacion = 0;
		Usuario usuario;
		Inventario inventario;
		List<Agrupaciones> listaAgrupaciones;
		List<Firma> listaFirmas;
		List<Evidencia> listaFotos;
		String estatus;
		List<Evidencia> listaEvidencias;
		
        try {
        	
        	Map<String, Object> request = objectMapper.convertValue(body, Map.class);
        	
        	
        	Gson gson = new Gson();
            String json = gson.toJson(request.get("usuario"));
        	
            usuario = gson.fromJson(json, new TypeToken<Usuario>(){}.getType());
            
            json = gson.toJson(request.get("inventario"));
            
            inventario = gson.fromJson(json, new TypeToken<Inventario>(){}.getType());
            
            indAgrupacion = (int) request.get("ind");
            estatus = (String) request.get("estatus");
            json = gson.toJson(request.get("listaAgrupaciones"));
            listaAgrupaciones = gson.fromJson(json, new TypeToken<List<Agrupaciones>>(){}.getType());
            
            //TODO: Actualizando valores
            if(indAgrupacion != 0) {
            	 for(Campos campo : listaAgrupaciones.get(indAgrupacion).getCampos() ) {
                 	if(campo.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
                 		if(campo.getNombreCampo().equalsIgnoreCase("FOLIO")) {
                 			inventario.setFolio(campo.getValor());
                     		this.inventario.cambiarFolio(campo.getValor(), inventario.getIdinventario());
                 		}
                 	}
                 	
                 	if(!campo.getTipoCampo().equals("FOTO") || !campo.getTipoCampo().equals("CHECKBOX-EVIDENCIA") || !campo.getTipoCampo().equals("FIRMA")) {            		
                 		this.valores.actualizarValorEHistorial(campo.getIdCampo(), usuario.getIdusuario(), inventario.getIdinventario(), campo.getValor());
                 	}
                 	
                 }
            }else {
            	for(int i =0; i < listaAgrupaciones.size(); i++) {
            		 for(Campos campo : listaAgrupaciones.get(i).getCampos() ) {
                      	if(campo.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
                     		if(campo.getNombreCampo().equalsIgnoreCase("FOLIO")) {
                      		inventario.setFolio(campo.getValor());
                      		this.inventario.cambiarFolio(campo.getValor(), inventario.getIdinventario());
                     		}
                      	}
                      	
                      	if(!campo.getTipoCampo().equals("FOTO") || !campo.getTipoCampo().equals("CHECKBOX-EVIDENCIA") || !campo.getTipoCampo().equals("FIRMA")) {            		
                      		this.valores.actualizarValorEHistorial(campo.getIdCampo(), usuario.getIdusuario(), inventario.getIdinventario(), campo.getValor());
                      	}
                      	
                      }
            	}
            }
            
            if(request.get("firmas") != null) {
             	System.out.println("Datos con Firmas");
             	json= gson.toJson(request.get("firmas"));
             	listaFirmas = gson.fromJson(json, new TypeToken<List<Firma>>(){}.getType());
             	System.out.println("Cantidad de Firmas: " +listaFirmas.size());
             	for(Firma firma : listaFirmas) {
             		actualizarFirma(firma,usuario.getIdusuario());
             	}
             }
             if(request.get("fotos") != null) {
             	System.out.println("Datos con Fotos");
             	json = gson.toJson(request.get("fotos"));
             	listaFotos = gson.fromJson(json, new TypeToken<List<Evidencia>>(){}.getType());
             	actualizarEvidencia(usuario.getIdusuario(),listaFotos);
             }
             if(request.get("evidencias") != null) {
             	System.out.println("Datos con Evidencias");
             	json = gson.toJson(request.get("evidencias"));
             	listaEvidencias = gson.fromJson(json, new TypeToken<List<Evidencia>>(){}.getType());
             	actualizarEvidencia(usuario.getIdusuario(),listaEvidencias);
             }
        
        
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return respuesta;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/inventario/actualizar/valores/nuevo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public List<String> registrarCambiosMovil(
			@RequestPart("data") Object body,
            @RequestPart("firmas") MultipartFile[] firmas
            //@RequestPart("fotos") MultipartFile[] fotos,
            //@RequestPart("evidencia") MultipartFile[] evidencia
            ) {
		List<String> respuesta = new ArrayList<String>();
		respuesta.add("listo");
		ObjectMapper objectMapper = new ObjectMapper();
		int indAgrupacion = 0;
		Usuario usuario;
		Inventario inventario;
		List<Agrupaciones> listaAgrupaciones;
		List<Firma> listaFirmas;
		List<Evidencia> listaFotos;
		String estatus;
		List<Evidencia> listaEvidencias;
		
        try {
        	
        	Map<String, Object> request = objectMapper.convertValue(body, Map.class);
        	System.out.println(request);
        	System.out.println(firmas);
        	//System.out.println(fotos.length);
        	//System.out.println(evidencia.length);
        	
//        	Gson gson = new Gson();
//            String json = gson.toJson(request.get("usuario"));
//        	
//            usuario = gson.fromJson(json, new TypeToken<Usuario>(){}.getType());
//            
//            json = gson.toJson(request.get("inventario"));
//            
//            inventario = gson.fromJson(json, new TypeToken<Inventario>(){}.getType());
//            
//            indAgrupacion = (int) request.get("ind");
//            estatus = (String) request.get("estatus");
//            json = gson.toJson(request.get("listaAgrupaciones"));
//            listaAgrupaciones = gson.fromJson(json, new TypeToken<List<Agrupaciones>>(){}.getType());
//            
//            //TODO: Actualizando valores
//            if(indAgrupacion != 0) {
//            	 for(Campos campo : listaAgrupaciones.get(indAgrupacion).getCampos() ) {
//                 	if(campo.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
//                 		inventario.setFolio(campo.getValor());
//                 		this.inventario.cambiarFolio(campo.getValor(), inventario.getIdinventario());
//                 	}
//                 	
//                 	if(!campo.getTipoCampo().equals("FOTO") || !campo.getTipoCampo().equals("CHECKBOX-EVIDENCIA") || !campo.getTipoCampo().equals("FIRMA")) {            		
//                 		this.valores.actualizarValorEHistorial(campo.getIdCampo(), usuario.getIdusuario(), inventario.getIdinventario(), campo.getValor());
//                 	}
//                 	
//                 }
//            }else {
//            	for(int i =0; i < listaAgrupaciones.size(); i++) {
//            		 for(Campos campo : listaAgrupaciones.get(i).getCampos() ) {
//                      	if(campo.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
//                      		inventario.setFolio(campo.getValor());
//                      		this.inventario.cambiarFolio(campo.getValor(), inventario.getIdinventario());
//                      	}
//                      	
//                      	if(!campo.getTipoCampo().equals("FOTO") || !campo.getTipoCampo().equals("CHECKBOX-EVIDENCIA") || !campo.getTipoCampo().equals("FIRMA")) {            		
//                      		this.valores.actualizarValorEHistorial(campo.getIdCampo(), usuario.getIdusuario(), inventario.getIdinventario(), campo.getValor());
//                      	}
//                      	
//                      }
//            	}
//            }
//            
//            if(request.get("firmas") != null) {
//             	System.out.println("Datos con Firmas");
//             	json= gson.toJson(request.get("firmas"));
//             	listaFirmas = gson.fromJson(json, new TypeToken<List<Firma>>(){}.getType());
//             	System.out.println("Cantidad de Firmas: " +listaFirmas.size());
//             	for(Firma firma : listaFirmas) {
//             		actualizarFirma(firma,usuario.getIdusuario());
//             	}
//             }
//             if(request.get("fotos") != null) {
//             	System.out.println("Datos con Fotos");
//             	json = gson.toJson(request.get("fotos"));
//             	listaFotos = gson.fromJson(json, new TypeToken<List<Evidencia>>(){}.getType());
//             	actualizarEvidencia(usuario.getIdusuario(),listaFotos);
//             }
//             if(request.get("evidencias") != null) {
//             	System.out.println("Datos con Evidencias");
//             	json = gson.toJson(request.get("evidencias"));
//             	listaEvidencias = gson.fromJson(json, new TypeToken<List<Evidencia>>(){}.getType());
//             	actualizarEvidencia(usuario.getIdusuario(),listaEvidencias);
//             }
        
        
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return respuesta;
	}
	
	
	public String actualizarFirma(Firma firma, int idUsuario) {
		String respuesta = "";
		
		List<firmasdocumento> lista = this.firma.obtenerConcidencia(firma.getNombreFirma(), firma.getCamposProyecto().getIdcamposproyecto(), firma.getInventario().getIdinventario());
		
		firmasdocumento nuevaFirma = new firmasdocumento(0,firma.getNombreFirma(),"",firma.getCamposProyecto(),firma.getInventario());
	
		nuevaFirma.setInventario(this.inventario.findById(nuevaFirma.getInventario().getIdinventario()).get());
		
		File archivoAnterior = null;
		
		byte[] firmaByte = new byte[firma.getFirma().size()];

		int ind =0;
		for(int item : firma.getFirma()) {
			firmaByte [ind] = (byte) item;
			ind++;
		}
		
		try {
			File firmaFile = File.createTempFile(nuevaFirma.getNombrefirma(), ".png");
			boolean mismaFirma = false;
			String firmaAnterior = "";
			
			FileUtils.writeByteArrayToFile(firmaFile, firmaByte);
					
			if(!lista.isEmpty()) {
				firmaAnterior = lista.get(0).getUrl();
				archivoAnterior = new File(descargarArchivo(new URL(firmaAnterior), "firmaAnterior.png"));
				mismaFirma = compararImagenes(firmaFile.getPath(),archivoAnterior.getPath());
			}
			
			
			String urlFirma = guardarEvidencia(nuevaFirma.getInventario(), firmaFile, nuevaFirma.getNombrefirma(), "Firmas");

			System.out.println("Tamaño de la firma: " + firma.getFirma().size());
			System.out.println("Lista de firmas: "+ lista);
			if(!lista.isEmpty()) {
				if(firma.getFirma().size() == 0) {
					this.firma.deleteById(lista.get(0).getIdfirma());
					this.valores.actualizaridValorValores("FALSE", lista.get(0).getCamposProyecto().getIdcamposproyecto(), lista.get(0).getInventario().getIdinventario());
					this.valores.actualizarValorEHistorial(firma.getCamposProyecto().getIdcamposproyecto(), idUsuario, firma.getInventario().getIdinventario(), "FIRMA ELIMINADA");
				}else {
					nuevaFirma.setIdfirma(lista.get(0).getIdfirma());
					nuevaFirma.setUrl(urlFirma);
					this.firma.save(nuevaFirma);
					this.valores.actualizaridValorValores("TRUE", lista.get(0).getCamposProyecto().getIdcamposproyecto(), lista.get(0).getInventario().getIdinventario());
					if(mismaFirma) {
						System.out.println("La firma es la misma");
					}else {
						System.out.println("La firma es diferente");
						String urlHistorico = guardarEvidencia(nuevaFirma.getInventario(), archivoAnterior,
								firma.getNombreFirma()+"-"+ new Date(), "Historico");
						this.valores.actualizarHistorial(firma.getCamposProyecto().getIdcamposproyecto(), idUsuario, firma.getInventario().getIdinventario(), urlFirma,urlHistorico);
					}
				}
			}else {
				if(firma.getFirma().size() != 0) {
					nuevaFirma.setUrl(urlFirma);
					this.firma.save(nuevaFirma);
					this.valores.actualizaridValorValores("TRUE", firma.getCamposProyecto().getIdcamposproyecto(), firma.getInventario().getIdinventario());
					
					String urlHistorico = guardarEvidencia(nuevaFirma.getInventario(), firmaFile,
							firma.getNombreFirma()+"-"+ new Date(), "Historico");
					this.valores.actualizarValorEHistorial(firma.getCamposProyecto().getIdcamposproyecto(), idUsuario, firma.getInventario().getIdinventario(), urlHistorico);
				}
			}
			
		} catch (IOException e) {
			System.out.println("Error: "+ e);
		}
		
		respuesta= "Correcto";
		
		return respuesta;
		
	}
	
	public static boolean compararImagenes(String rutaImagen1, String rutaImagen2) {
       boolean iguales = true;
       System.out.println("Ruta 1: " + rutaImagen1);
       System.out.println("Ruta 2: " + rutaImagen2);
       try {
		BufferedImage imagen1 = ImageIO.read(new File(rutaImagen1));
		BufferedImage imagen2 = ImageIO.read(new File(rutaImagen2));
		if(imagen1 != null && imagen2 != null) {
			int anchoImagen1 = imagen1.getWidth();
			int altoImagen1 = imagen1.getHeight();
			int anchoImagen2 = imagen2.getWidth();
			int altoImagen2 = imagen2.getHeight();
			System.out.println("Ancho 1: " + anchoImagen1);
			System.out.println("Alto 1: " + altoImagen1);
			System.out.println("Ancho 2: " + anchoImagen2);
			System.out.println("Alto 2: " + altoImagen2);

			if (anchoImagen1 != anchoImagen2 || altoImagen1 != altoImagen2) {
			    System.out.println("Las imágenes tienen dimensiones diferentes.");
			    return false;
			}
			
			for (int y = 0; y < altoImagen1; y++) {
			    for (int x = 0; x < anchoImagen1; x++) {
			        int rgbImagen1 = imagen1.getRGB(x, y);
			        int rgbImagen2 = imagen2.getRGB(x, y);		        
			        if (rgbImagen1 != rgbImagen2) {
			            System.out.println("Las imágenes son diferentes.");
			            return false;
			        }
			    }
			}
		}else {
			return false;
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       return iguales;
    }
	
	public String actualizarEvidencia(int idUsuario, List<Evidencia> evidencias) {
		String respuesta = "Error";
		
		for(Evidencia evidencia : evidencias) {
			List<Fotoevidencia> lista = this.fotoEvidencia.obtenerConcidencia(evidencia.getNombreEvidencia(),
					evidencia.getCamposProyecto().getIdcamposproyecto(), evidencia.getInventario().getIdinventario());

			Fotoevidencia nuevaEvidencia = new Fotoevidencia(0, evidencia.getNombreEvidencia(), "", "",
					new Usuario(idUsuario), evidencia.getInventario(), evidencia.getCamposProyecto());

			nuevaEvidencia.setInventario(this.inventario.findById(nuevaEvidencia.getInventario().getIdinventario()).get());
			File archivoAnterior = null;

			byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

			int ind = 0;
			for (int item : evidencia.getEvidencia()) {
				evidenciaByte[ind] = (byte) item;
				ind++;
			}

			try {
				File evidenciaFile = File.createTempFile(nuevaEvidencia.getNombrefoto().length() <= 3 ? "000"+nuevaEvidencia.getNombrefoto()  :nuevaEvidencia.getNombrefoto(), ".png");
				boolean mismaEvidencia = false;
				String evidenciaAnterior= "";

				FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);
				
				if(!lista.isEmpty()) {
					evidenciaAnterior = lista.get(0).getUrl();
					archivoAnterior = new File(descargarArchivo(new URL(evidenciaAnterior), "EvidenciaAnterior.png"));
					mismaEvidencia = compararImagenes(evidenciaFile.getPath(),archivoAnterior.getPath());
				}

				String urlEvidencia = guardarEvidencia(nuevaEvidencia.getInventario(), evidenciaFile,
						nuevaEvidencia.getNombrefoto(), "Evidencias");

				if (!lista.isEmpty()) {
					nuevaEvidencia.setIdfoto(lista.get(0).getIdfoto());
					nuevaEvidencia.setUrl(urlEvidencia);
					if(idUsuario == 0) {
						nuevaEvidencia.setUsuario(lista.get(0).getUsuario());
					}
					this.fotoEvidencia.save(nuevaEvidencia);
					//TODO: comprobar si es la misma evidencia
					if(mismaEvidencia) {
						System.out.println("La evidencia es la misma");
					}else {
						System.out.println("La evidencia es diferente");
						String urlHistorico = guardarEvidencia(nuevaEvidencia.getInventario(), archivoAnterior,
								nuevaEvidencia.getNombrefoto()+"-"+ new Date(), "Historico");
						this.valores.actualizarHistorial(nuevaEvidencia.getCampoProyecto().getIdcamposproyecto(), idUsuario, nuevaEvidencia.getInventario().getIdinventario(), urlEvidencia,urlHistorico);
					}
				} else {
					
					nuevaEvidencia.setUrl(urlEvidencia);
					this.fotoEvidencia.save(nuevaEvidencia);
					
					String urlHistorico = guardarEvidencia(nuevaEvidencia.getInventario(), evidenciaFile,
							nuevaEvidencia.getNombrefoto()+"-"+ new Date(), "Historico");
					this.valores.actualizarHistorial(evidencia.getCamposProyecto().getIdcamposproyecto(), idUsuario, evidencia.getInventario().getIdinventario(), urlHistorico,"");
					System.out.println("Se actualizo el historico de la imagen");
				}

			} catch (IOException e) {
				e.printStackTrace();
				respuesta = "Error";
			}
		}
		respuesta= "Correcto";

		return respuesta;

	}
	
	public String guardarEvidencia(Inventario inventario, File file, String nombreFirma, String tipo) {
		try {
			
	
			//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
			FileInputStream serviceAccount = new FileInputStream(descargarArchivo(url, "google-service-descarga.json"));
			//String bucketName = "isae-de6da.appspot.com";
			String bucketName = "dr-pool-eca1c.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String objectName = "Proyectos/" + inventario.getProyecto().getIdproyecto() + "-"
						+ inventario.getProyecto().getProyecto().toUpperCase() + "/" + inventario.getIdinventario()
						+ "-"+inventario.getFolio()+"/"+tipo+"/"+nombreFirma;

				Map<String, String> map = new HashMap<>();
		        map.put("firebaseStorageDownloadTokens", nombreFirma.replace(" ", "")+".png");
		        
				BlobId blobId = BlobId.of(bucketName, objectName);
				BlobInfo blobInfo = BlobInfo
						.newBuilder(blobId)
						.setContentType("image/png")
						.setMetadata(map)
						.build();
				

				Blob b = storage.create(blobInfo, new FileInputStream(file.getAbsolutePath()), new Storage. BlobWriteOption[0]);
				
				
				System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);
				
// Sirve para crear un link temporal para la descarga del archivo almacenado en Firebase
//				URL urlFirma = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
				
				String [] direccionTemporal = b.getSelfLink().split("/");
				
				String urlFirma = generarUrl(direccionTemporal,b.getMetadata().get("firebaseStorageDownloadTokens"));
				
				return urlFirma;
			}

		} catch (IOException e) {

			System.out.println("Exception " + e.getMessage());
			return "Error al guardar la firma";
		}
		return "";
	}
	
	private String generarUrl(String [] direccionTemporal, String token) {
		//String url= "https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/";
		String url = "https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/";
		
		return url+direccionTemporal[8]+"?alt=media&token="+token;
	}
	
	private String descargarArchivo(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        String tempDir = System.getProperty("java.io.tmpdir");
        String outputPath = tempDir + "/" + localFilename;

        try {
        	//connect
            URLConnection urlConn = url.openConnection();

            //get inputstream from connection
            is = urlConn.getInputStream();               
            fos = new FileOutputStream(outputPath);   

            // 4KB buffer
            byte[] buffer = new byte[4096];
            int length;

            // read from source and write into local file
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return outputPath;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

}
