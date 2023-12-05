package com.isae.drpool.drpool.controller;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.jasperreports.JasperReportsUtils;

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
import com.google.gson.Gson;
import com.google.protobuf.Struct;
import com.isae.drpool.drpool.dao.IActividadesDAO;
import com.isae.drpool.drpool.dao.IActividadimagenesDAO;
import com.isae.drpool.drpool.dao.IAlbercaDAO;
import com.isae.drpool.drpool.dao.ICamposProyectoDAO;
import com.isae.drpool.drpool.dao.IDocumentoGenerado;
import com.isae.drpool.drpool.dao.IFirmaDocumentosDAO;
import com.isae.drpool.drpool.dao.IFotoEvidenciaDAO;
import com.isae.drpool.drpool.dao.IInventarioDAO;
import com.isae.drpool.drpool.dao.IProyectoDAO;
import com.isae.drpool.drpool.dao.IReportemensualDAO;
import com.isae.drpool.drpool.dao.ISedeDAO;
import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Actividades;
import com.isae.drpool.drpool.entity.Actividadimagenes;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Alberca;
import com.isae.drpool.drpool.entity.Campos;
import com.isae.drpool.drpool.entity.Camposproyecto;
import com.isae.drpool.drpool.entity.Documentogenerado;
import com.isae.drpool.drpool.entity.Evidencia;
import com.isae.drpool.drpool.entity.EvidenciaCheckList;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Inventario;
import com.isae.drpool.drpool.entity.Proyecto;
import com.isae.drpool.drpool.entity.Reportemensual;
import com.isae.drpool.drpool.entity.Sede;
import com.isae.drpool.drpool.entity.Tipoproyecto;
import com.isae.drpool.drpool.entity.Usuario;
import com.isae.drpool.drpool.entity.Valore;
import com.isae.drpool.drpool.entity.firmasdocumento;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.SimpleFontFace;
import net.sf.jasperreports.engine.fonts.SimpleFontFamily;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import org.apache.commons.io.FileUtils;

@RestController
//@RequestMapping("/generarPDF")
public class GenerarPDFRestController {

	@Autowired
	private IInventarioDAO inventario;

	@Autowired
	private IFirmaDocumentosDAO firmas;

	@Autowired
	private IFotoEvidenciaDAO fotoEvidencia;

	@Autowired
	private ICamposProyectoDAO camposProyecto;

	@Autowired
	private IProyectoDAO proyecto;

	@Autowired
	private IValoresDAO valores;

	@Autowired
	private IDocumentoGenerado documentoGenerado;
	
	@Autowired
	private IAlbercaDAO alberca;
	
	@Autowired
	private ISedeDAO sede; 

	@Autowired
	private IReportemensualDAO reportemensual;
	
	@Autowired
	private IActividadesDAO actividades; 
	
	@Autowired
	private IActividadimagenesDAO actividadimagenes;
	

	private String firestorage_auth = "google-service-descarga.json";
	
	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/documento/pdf/{idInventario}")
	public List<Integer> generarDocumento(@PathVariable(value = "idInventario") String idInventario,
			@RequestBody List<Agrupaciones> listaAgrupaciones) {

		List<firmasdocumento> firmas = obtenerFirmasP(listaAgrupaciones, Integer.parseInt(idInventario));
		Inventario inventario = this.inventario.findById(Integer.parseInt(idInventario)).get();

		try {

			Map<String, Object> documento = generateInvoiceFor(listaAgrupaciones, firmas,
					inventario.getProyecto().getProyecto(), inventario);
			System.out.println("Despues de obtener el documento, ObtenerDocumento PDF");
			File invoicePdf = (File) documento.get("archivo");

			System.out.println("Generated successfully...");
			byte[] bytes = FileUtils.readFileToByteArray(invoicePdf);
			List<Integer> respuesta = new ArrayList<Integer>();

			for (int i = 0; i < bytes.length; i++) {
				respuesta.add((int) bytes[i]);
			}
			return respuesta;

//			HttpHeaders httpHeaders = getHttpHeaders(12, "formato", invoicePdf);
//			return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("Exception in Controller :: " + e.getMessage());
//			return (ResponseEntity<InputStreamResource>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
			// return new ArrayList<Integer>();
			return new ArrayList<Integer>();
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/obtener/documento/pdf/firmado/{idInventario}")
	public List<Integer> generarDocumentoConFirma(@PathVariable(value = "idInventario") String idInventario,
			@RequestBody List<Agrupaciones> listaAgrupaciones) {

		try {

			List<firmasdocumento> firmas = obtenerFirmasP(listaAgrupaciones, Integer.parseInt(idInventario));
			Inventario inventario = this.inventario.obtenerPorIdInventario(Integer.parseInt(idInventario));

			Map<String, Object> documento = generateInvoiceFor(listaAgrupaciones, firmas,
					inventario.getProyecto().getProyecto(), inventario);
			System.out.println("Despues de obtener el documento, ObtenerDocumento PDF Firmado");

			File invoicePdf = (File) documento.get("archivo");

			System.out.println("Generated successfully...");
			byte[] bytes = FileUtils.readFileToByteArray(invoicePdf);
			List<Integer> respuesta = new ArrayList<Integer>();

			for (int i = 0; i < bytes.length; i++) {
				respuesta.add((int) bytes[i]);
			}

			return respuesta;

//			HttpHeaders httpHeaders = getHttpHeaders(12, "formato", invoicePdf);
//			return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("Exception in Controller :: " + e.getMessage());
//			return (ResponseEntity<InputStreamResource>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
			return new ArrayList<Integer>();
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/generar/url/documento/{idinventario}")
	public String actualizarEvidencia(@PathVariable(value = "idinventario") String idinventario,
			@RequestBody Evidencia evidencia) {
		String respuesta = "";

		byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

		int ind = 0;
		for (int item : evidencia.getEvidencia()) {
			evidenciaByte[ind] = (byte) item;
			ind++;
		}

		try {
			File evidenciaFile = File.createTempFile(evidencia.getNombreEvidencia(), ".pdf");

			FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);

			Inventario inventario = this.inventario.obtenerPorIdInventario(Integer.parseInt(idinventario));

			String urlEvidencia = guardarEvidencia(inventario, evidenciaFile, evidencia.getNombreEvidencia());

			respuesta = urlEvidencia;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return respuesta;

	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generar/nuevo/documento/{idInventario}")
	public String volverAGenerarDocumento(@PathVariable(value = "idInventario") String idInventario) {

		try {
			Inventario inventario = this.inventario.obtenerPorIdInventario(Integer.parseInt(idInventario));
			List<Agrupaciones> listaAgrupaciones = getDatosRegistro(inventario.getIdinventario(),
					inventario.getProyecto().getIdproyecto());

			List<firmasdocumento> firmas = obtenerFirmasP(listaAgrupaciones, Integer.parseInt(idInventario));
			
			Map<String, Object> pdf = new HashMap<String,Object>();
			
			if(inventario.getProyecto().getProyecto().startsWith("BITACORA")) {
				pdf = parametrosBitacoraDrPool(listaAgrupaciones, firmas,
						inventario.getProyecto().getProyecto(), inventario);
			}else {
				pdf = generateInvoiceFor(listaAgrupaciones, firmas,
						inventario.getProyecto().getProyecto(), inventario);
			}
			
			 System.out.println("Despues de obtener el documento, GenerarNuevo Documento ");
			 
			 System.out.println("Object pdf: " + pdf);

			File invoicePdf = (File) pdf.get("archivo");
			
			System.out.println("Se obtuvo el archivo");
			String nombreEvidencia = pdf.get("nombre").toString();

			System.out.println("Nombre del documento: " + nombreEvidencia);
			System.out.println("Generated successfully...");
			String respuesta;

			respuesta = guardarEvidencia(inventario, invoicePdf, nombreEvidencia);
			List<Documentogenerado> lista = this.documentoGenerado.obtenerDocumento(Integer.parseInt(idInventario));
			Documentogenerado documento = new Documentogenerado(0, nombreEvidencia, respuesta, inventario);
			if (lista.isEmpty()) {
				this.documentoGenerado.save(documento);
			} else {
				documento.setIddocumento(lista.get(0).getIddocumento());
				this.documentoGenerado.save(documento);
			}

			return respuesta;

//			HttpHeaders httpHeaders = getHttpHeaders(12, "formato", invoicePdf);
//			return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("Exception in Controller :: " + e.getMessage());
//			return (ResponseEntity<InputStreamResource>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
			return "";
		}
	}
	
	private Map<String,Object> parametrosBitacoraDrPool(List<Agrupaciones> listaAgrupaciones, List<firmasdocumento> firmas,
			String nombreProyecto, Inventario inventario) throws IOException{
		Map<String, Object> parametros = new HashMap<String,Object>();
		Map<String, Object> tablaBitacora = new HashMap<String,Object>();
		Map<String, Object> respuesta = new HashMap<String,Object>();
		//Creamos el mapa de fecha para el check list 
		
		List<Map<String, Object>> listEquipos = new ArrayList<Map<String, Object>>();
		
		List<ConcurrentHashMap<String, Object>> nameEquipos = new ArrayList<ConcurrentHashMap<String,Object>>();
		ConcurrentHashMap<String, Object> list_contenedora_equipos = new ConcurrentHashMap<String, Object>();
		List<ConcurrentHashMap<String, Object>> listCheckbox1 = new ArrayList<ConcurrentHashMap<String,Object>>();
		List<ConcurrentHashMap<String, Object>> listCheckbox2= new ArrayList<ConcurrentHashMap<String,Object>>();
		List<ConcurrentHashMap<String, Object>> listCheckbox3 = new ArrayList<ConcurrentHashMap<String,Object>>();
		List<ConcurrentHashMap<String, Object>> listCheckbox4 = new ArrayList<ConcurrentHashMap<String,Object>>();
		List<ConcurrentHashMap<String, Object>> listCheckbox5 = new ArrayList<ConcurrentHashMap<String,Object>>();
		
		
		File pdfFile;
		String nombrePdf = inventario.getFolio();
		Proyecto proyecto = this.proyecto.obtenerProyectoPorId(inventario.getProyecto().getIdproyecto());
		Alberca alberca = this.alberca.getById(proyecto.getAlberca().getIdalberca());
		Sede sede = this.sede.getById(alberca.getSede().getIdsede());
		
		List<Agrupaciones> listaAgrupacionesC = new ArrayList<>(listaAgrupaciones);
		System.out.println("--------------------------");
		System.out.println(listaAgrupaciones);
		System.out.println("--------------------------");
		Boolean iterar = false; 
		 
		for (Agrupaciones agrupacion : listaAgrupacionesC) {
			
			
			for (Campos campo : agrupacion.getCampos()) {

				if(campo.getNombreCampo().contains("OBSERVACIONES")) parametros.put(campo.getNombreCampo(), campo.getValor());
				
				
				if(agrupacion.getAgrupacion().contains("PARAMETROS MUESTRA")) {
					if(campo.getNombreCampo().contains("HORA")) {
						System.out.println("Se mando la hora a checklist");
						list_contenedora_equipos.put(campo.getNombreCampo(), campo.getValor());
					}
				}
				
				if(agrupacion.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
					 
					parametros.put(campo.getNombreCampo(), campo.getValor());
					
					if(campo.getNombreCampo().equalsIgnoreCase("FECHA REGISTRO")) {
						list_contenedora_equipos.put(campo.getNombreCampo(), campo.getValor());
						System.out.println("Se mando la fecha a checklist");
						tablaBitacora.put(campo.getNombreCampo(), campo.getValor());
						parametros.put("FECHA", campo.getValor());
					}
					//else if(campo.getNombreCampo().contains("PARAMETROS MUESTRA")) {}
				}else {
						if(!campo.getTipoCampo().equalsIgnoreCase("FIRMA")) {
							if(campo.getAgrupacion().contains("REVISION DE EQUIPOS MUESTRA")) {
								
								ConcurrentHashMap<String,Object> mapTemp = new ConcurrentHashMap<String,Object>();
								
								if(campo.getNombreCampo().contains("TOMA DE MUESTRA 1")) {
									mapTemp.put("NOMBRE_EQUIPO", campo.getNombreCampo());
									nameEquipos.add(mapTemp);
									mapTemp = new ConcurrentHashMap<String,Object>();
									mapTemp.put("CHECKBOX_HORA_1", campo.getValor());
									listCheckbox1.add(mapTemp);
								}else if(campo.getNombreCampo().contains("TOMA DE MUESTRA 2")) {
									mapTemp = new ConcurrentHashMap<String,Object>();
									mapTemp.put("CHECKBOX_HORA_2", campo.getValor());
									listCheckbox2.add(mapTemp);
								}else if(campo.getNombreCampo().contains("TOMA DE MUESTRA 3")) {
									mapTemp = new ConcurrentHashMap<String,Object>();
									mapTemp.put("CHECKBOX_HORA_3", campo.getValor());
									listCheckbox3.add(mapTemp);
								}else if(campo.getNombreCampo().contains("TOMA DE MUESTRA 4")) {
									mapTemp = new ConcurrentHashMap<String,Object>();
									mapTemp.put("CHECKBOX_HORA_4", campo.getValor());
									listCheckbox4.add(mapTemp);
								}else if(campo.getNombreCampo().contains("TOMA DE MUESTRA 5")) {
									mapTemp = new ConcurrentHashMap<String,Object>();
									mapTemp.put("CHECKBOX_HORA_5", campo.getValor());
									listCheckbox5.add(mapTemp);
									iterar = true;
								}
								
								if(iterar) {								
									JRBeanCollectionDataSource listNames = new JRBeanCollectionDataSource(nameEquipos);
									JRBeanCollectionDataSource listHr1 = new JRBeanCollectionDataSource(listCheckbox1);
									JRBeanCollectionDataSource listHr2 = new JRBeanCollectionDataSource(listCheckbox2);
									JRBeanCollectionDataSource listHr3 = new JRBeanCollectionDataSource(listCheckbox3);
									JRBeanCollectionDataSource listHr4 = new JRBeanCollectionDataSource(listCheckbox4);
									JRBeanCollectionDataSource listHr5 = new JRBeanCollectionDataSource(listCheckbox5);
									System.out.println("Creacion de JRC de los nombres de los equipos y hrs");
									list_contenedora_equipos.put("LIST_NAME_EQUIPOS", listNames);
									list_contenedora_equipos.put("LIST_HORA_1_CHECKBOX", listHr1);
									list_contenedora_equipos.put("LIST_HORA_2_CHECKBOX", listHr2);
									list_contenedora_equipos.put("LIST_HORA_3_CHECKBOX", listHr3);
									list_contenedora_equipos.put("LIST_HORA_4_CHECKBOX", listHr4);
									list_contenedora_equipos.put("LIST_HORA_5_CHECKBOX", listHr5);
									System.out.println("Se agregan los equipos a listEquipos");

									iterar = false; 
									
								}
							}
							String campoPlantilla = campo.getNombreCampo().replaceAll("[()]", "");
							System.out.println(campoPlantilla+":"+ campo.getValor());
							tablaBitacora.put(campoPlantilla, campo.getValor());
							
						}
				}
			}	
			
			if(agrupacion.getAgrupacion().equalsIgnoreCase("DATOS DEL REGISTRO")) {
				listEquipos.add(list_contenedora_equipos);
				//nameEquipos = new ArrayList<ConcurrentHashMap<String,Object>>();
				
				//listCheckbox1 = new ArrayList<ConcurrentHashMap<String,Object>>();
				//listCheckbox2= new ArrayList<ConcurrentHashMap<String,Object>>();
				//listCheckbox3 = new ArrayList<ConcurrentHashMap<String,Object>>();
				//listCheckbox4 = new ArrayList<ConcurrentHashMap<String,Object>>();
				//listCheckbox5 = new ArrayList<ConcurrentHashMap<String,Object>>();
				
			

			}
			
					
			
		}
		
		for(firmasdocumento firma : firmas) {
			if(firma.getCamposProyecto().getCampo() != null) {
				URL url = new URL(firma.getUrl());
				tablaBitacora.put(firma.getCamposProyecto().getCampo(), url);
			}
		}
		
		// EMPEZAMOS A CREAR EL PARAMETRO DE FECHAS 
		
		List<Map<String, Object>> bitacora = new ArrayList<Map<String, Object>>();
		
		System.out.println("Tabla bitacora: " + tablaBitacora);
		 
		
		bitacora.add(tablaBitacora);
		
		JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(bitacora);
		
		//Creamos la coleccion de listas de las listas de equipos
		JRBeanCollectionDataSource jrcEquiposFinal = new JRBeanCollectionDataSource(listEquipos);
		System.out.println("Creacion y asignacion de los equipos");
		
		System.out.println("List equipos: ----- " + listEquipos);	
		
		
		

		
		URL logoCliente = new URL (sede.getCliente().getUrllogo());
		System.out.println("URL TEXT: " + sede.getCliente().getUrllogo());
		System.out.println("URL: " + logoCliente);
		System.out.println("NOMBRE ALBERCA" +  alberca.getNombrealberca());
		
		parametros.put("ALBERCA", alberca.getNombrealberca());
		
		parametros.put("VOLUMEN", alberca.getCapacidad());
		
		//parametros.put("OBSERVACIONES", alberca.getObservaciones());
		
		parametros.put("LOGO CLIENTE", logoCliente);
		
		parametros.put("LIST_EQUIPOS", jrcEquiposFinal);

		parametros.put("CollectionBeanParam", jrc);
		
		pdfFile = File.createTempFile("ReporteCell", ".pdf");
		
		System.out.println("Crear pdf");
		
		try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
			System.out.println("Nombre Proyecto: "+ nombreProyecto);
			final JasperReport report = loadTemplate("BITACORA DIARIA");
//			final Map<String, Object> parameters = parameters(listaAgrupaciones, firmas, nombreProyecto, idInventario);
			System.out.println("Se cargo la plantilla correctamente");

			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					Collections.singletonList("Invoice"));
			System.out.println("JRB data source");
			try {
			JasperReportsUtils.renderAsPdf(report, parametros, dataSource, pos);
			}catch(Exception e) {
				System.out.println("Error en render as pdf: " + e);
			}
			System.out.println("Render de pdf");
			respuesta = new HashMap<String, Object>();
			respuesta.put("archivo", pdfFile);
			respuesta.put("nombre", nombrePdf);
			return respuesta;

		} catch (final Exception e) {
			System.out.println(String.format("An error occured during PDF creation: %s", e));
			return null;
		}
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/generar/nuevo/documento/{idProyecto}")
	public String volverAGenerarDocumento(@PathVariable(value = "idProyecto") String idproyecto, @RequestBody List<Integer> idInventario) {

		try {
//			Inventario inventario = this.inventario.obtenerPorIdInventario(Integer.parseInt(idInventario));
			List<Inventario> listaInventarios = this.inventario.findAllById(idInventario);
			for(Inventario inventario : listaInventarios) {
				List<Agrupaciones> listaAgrupaciones = getDatosRegistro(inventario.getIdinventario(),
						inventario.getProyecto().getIdproyecto());

				List<firmasdocumento> firmas = obtenerFirmasP(listaAgrupaciones, inventario.getIdinventario());

				Map<String, Object> pdf = generateInvoiceFor(listaAgrupaciones, firmas,
						inventario.getProyecto().getProyecto(), inventario);
				System.out.println("Despues de obtener el documento, GenerarNuevo Documento ");

				File invoicePdf = (File) pdf.get("archivo");
				String nombreEvidencia = pdf.get("nombre").toString();

				System.out.println("Nombre del documento: " + nombreEvidencia);
				System.out.println("Generated successfully...");
				String respuesta;

				respuesta = guardarEvidencia(inventario, invoicePdf, nombreEvidencia);
				List<Documentogenerado> lista = this.documentoGenerado.obtenerDocumento(inventario.getIdinventario());
				Documentogenerado documento = new Documentogenerado(0, nombreEvidencia, respuesta, inventario);
				if (lista.isEmpty()) {
					this.documentoGenerado.save(documento);
				} else {
					documento.setIddocumento(lista.get(0).getIddocumento());
					this.documentoGenerado.save(documento);
				}


//				HttpHeaders httpHeaders = getHttpHeaders(12, "formato", invoicePdf);
//				return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, HttpStatus.OK);
			}
			return "Documentos Generados";

		} catch (Exception e) {
			System.out.println("Exception in Controller :: " + e.getMessage());
//			return (ResponseEntity<InputStreamResource>) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
			return "";
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/actualizar/documento/{idinventario}")
	public String actualizarDocumento(@PathVariable(value = "idinventario") String idinventario,
			@RequestBody Evidencia evidencia) {
		String respuesta = "";

		byte[] evidenciaByte = new byte[evidencia.getEvidencia().size()];

		int ind = 0;
		for (int item : evidencia.getEvidencia()) {
			evidenciaByte[ind] = (byte) item;
			ind++;
		}
		Documentogenerado documentoActual = this.documentoGenerado.obtenerDocumento(Integer.parseInt(idinventario))
				.get(0);
		String nombreDocumento = documentoActual.getNombreDocumento();

		try {
			File evidenciaFile = File.createTempFile(nombreDocumento, ".pdf");

			FileUtils.writeByteArrayToFile(evidenciaFile, evidenciaByte);

			Inventario inventario = this.inventario.obtenerPorIdInventario(Integer.parseInt(idinventario));

			String urlEvidencia = guardarEvidencia(inventario, evidenciaFile, nombreDocumento);

			respuesta = urlEvidencia;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return respuesta;

	}

	private List<Agrupaciones> getDatosRegistro(int idRegistro, int idProyecto) {
		System.out.println(idRegistro);
		System.out.println(idProyecto);
		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
		List<String> agrupaciones = new ArrayList<String>();
		List<Campos> campos = new ArrayList<Campos>();
		Campos campo = new Campos();
		List<Valore> lista = this.valores.obtenerDatosCampoPorProyecto(idRegistro, idProyecto);
		for (Valore item : lista) {
			campo = new Campos();
			campo.setIdCampo(item.getCamposproyecto().getIdcamposproyecto());
			campo.setNombreCampo(item.getCamposproyecto().getCampo());
			campo.setTipoCampo(item.getCamposproyecto().getTipocampo());
			campo.setRestriccion(item.getCamposproyecto().getPattern());
			campo.setAlerta(item.getCamposproyecto().getAlerta());
			campo.setAgrupacion(item.getCamposproyecto().getAgrupacion().getAgrupacion());
			campo.setLongitud(item.getCamposproyecto().getLongitud());
			campo.setValor(item.getValor());

			if (agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
				agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
				campos.add(campo);
				listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(),
						item.getCamposproyecto().getAgrupacion().getAgrupacion(), campos));
			} else {
				if (agrupaciones.contains(item.getCamposproyecto().getAgrupacion().getAgrupacion())) {
					List<Campos> aux = new ArrayList<Campos>();
					aux = listaAgrupaciones
							.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion()))
							.getCampos();
					aux.add(campo);
					listaAgrupaciones
							.get(agrupaciones.indexOf(item.getCamposproyecto().getAgrupacion().getAgrupacion()))
							.setCampos(aux);
				} else {
					agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
					campos = new ArrayList<Campos>();
					campos.add(campo);
					listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(),
							item.getCamposproyecto().getAgrupacion().getAgrupacion(), campos));
				}
			}
		}
		return listaAgrupaciones;
	}

	@Async("threadPoolTaskExecutor")
	public String guardarEvidencia(Inventario inventario, File file, String nombrePdf) {
		try {

			//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
			FileInputStream serviceAccount = new FileInputStream(descargarRecurso(url, "google-service-descarga.json"));
			//String bucketName = "isae-de6da.appspot.com";
			String bucketName = "dr-pool-eca1c.appspot.com";
			boolean bandera = true;
			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();

			if (file != null) {
				String nombre = nombrePdf.length() >= 2 ? nombrePdf
						: inventario.getIdinventario() + "-" + inventario.getFolio();
				String objectName = "Proyectos/" + inventario.getProyecto().getIdproyecto() + "-"
						+ inventario.getProyecto().getProyecto().toUpperCase() + "/" + inventario.getIdinventario()
						+ "-" + inventario.getFolio() + "/Evidencias/" + nombre;

				Map<String, String> map = new HashMap<>();
				map.put("firebaseStorageDownloadTokens", nombrePdf.replace(" ", "") + ".pdf");

				BlobId blobId = BlobId.of(bucketName, objectName);
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").setMetadata(map)
						.build();

				Blob b = storage.create(blobInfo, new FileInputStream(file.getAbsolutePath()),
						new Storage.BlobWriteOption[0]);

				System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);

// Sirve para crear un link temporal para la descarga del archivo almacenado en Firebase
//				URL urlFirma = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

				String[] direccionTemporal = b.getSelfLink().split("/");

				String urlFirma = generarUrl(direccionTemporal, b.getMetadata().get("firebaseStorageDownloadTokens"));

				return urlFirma;
			}

		} catch (IOException e) {

			System.out.println("Exception " + e.getMessage());
			return "Error al guardar la firma";
		}
		return "";
	}

	private String generarUrl(String[] direccionTemporal, String token) {
		//String url = "https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/";
		String url = "https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/";

		return url + direccionTemporal[8] + "?alt=media&token=" + token;
	}

	private String descargarRecurso(URL url, String localFilename) throws IOException {
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

	private HttpHeaders getHttpHeaders(long idinventario, String lang, File invoicePdf) {
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_PDF);
		respHeaders.setContentLength(invoicePdf.length());
		respHeaders.setContentDispositionFormData("attachment",
				String.format("%s-%s.pdf", new Object[] { Long.valueOf(idinventario), lang }));
		return respHeaders;
	}

	private void descargarRecursos() {
		System.out.println("Dentro del metodo de descarga del recurso :)");
		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(
				//"https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc"
				"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA."
				)
						.openStream());
				FileOutputStream fileOS = new FileOutputStream("google-service-descarga.json")) {
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
		} catch (IOException e) {
			// handles IO exceptions
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

	private String descargarFrima(URL url, String localFilename) throws IOException {
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

	private Map<String, Object> generateInvoiceFor(List<Agrupaciones> listaAgrupaciones, List<firmasdocumento> firmas,
			String nombreProyecto, Inventario inventario) throws IOException {

		// File pdfFile = new File("ReporteCell.pdf");
		final Map<String, Object> parameters = parameters(listaAgrupaciones, firmas, nombreProyecto,
				inventario.getIdinventario());
		File pdfFile;
		String nombrePdf = "";
		if (inventario.getProyecto().getProyecto().contains("ISSSTE")) {
			String codigoBarras = parameters.get("CODIGOLABEL").toString().replace("*", "");
			String noSerieBreak = (String) parameters.get("NO BREAK TIPO I NUMERO DE SERIE");
			System.out.println("Respuesta noSerie: " + noSerieBreak);
			if(noSerieBreak == null && inventario.getProyecto().getProyecto().equals("ISSSTE DVD")) {
				noSerieBreak = (String) parameters.get("LECTOR DVD NUMERO DE SERIE");
				System.out.println("Respuesta DVD: " + noSerieBreak);
			}else if(noSerieBreak == null) {
				noSerieBreak = "DVD";
			}
			String etiquetaCandado = (String) parameters
					.get("CANDADO PARA EQUIPO DE COMPUTO DE ESCRITORIO O PORTATIL HOSTNAME/ETIQUETA");
			if (noSerieBreak.isEmpty() && !noSerieBreak.equals("-") && !noSerieBreak.equals(" -")
					&& !noSerieBreak.equals("- ") && !noSerieBreak.equals(" - ")) {
				nombrePdf = codigoBarras + "_" + etiquetaCandado;
			} else {
				nombrePdf = codigoBarras + "_" + noSerieBreak;
			}
			System.out.println("Nombre del pdf: " + nombrePdf);
			pdfFile = File.createTempFile(nombrePdf, ".pdf");
		} else {
			nombrePdf = "";
			pdfFile = File.createTempFile("ReporteCell", ".pdf");
		}

		try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
			System.out.println("Nombre Proyecto: "+ nombreProyecto);
			final JasperReport report = loadTemplate(nombreProyecto);
//			final Map<String, Object> parameters = parameters(listaAgrupaciones, firmas, nombreProyecto, idInventario);

			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					Collections.singletonList("Invoice"));
			JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
			Map<String, Object> respuesta = new HashMap<String, Object>();
			respuesta.put("archivo", pdfFile);
			respuesta.put("nombre", nombrePdf);
			return respuesta;

		} catch (final Exception e) {
			System.out.println(String.format("An error occured during PDF creation: %s", e));
			return null;
		}

	}

	private Map<String, Object> parameters(List<Agrupaciones> listaAgrupaciones, List<firmasdocumento> firmas,
			String nombreProyecto, int idInventario) throws IOException {
		final Map<String, Object> parameters = new HashMap<>();
		
		
		System.out.println("Idinventario:  " + idInventario);
		
		//Proyecto pro = this.proyecto.getById(idInventario);
		
		//System.out.println("Proyecto:  " + pro);
		//Alberca alberca = this.alberca.getById(pro.getAlberca().getIdalberca());
		
		//parameters.put("ALBERCA", alberca.getNombrealberca());

		int indFirma = 0;
		List<Fotoevidencia> evidencias;
		List<EvidenciaCheckList> datos = new ArrayList<EvidenciaCheckList>();
		Proyecto proyecto;
		String codigoDeBarras = "AF";
		String candado = "-";
		String ups = "-";
		String dvd = "-";
		
		for (Agrupaciones agrupacion : listaAgrupaciones) {
			for (Campos campo : agrupacion.getCampos()) {
				
				if (campo.getNombreCampo().equalsIgnoreCase(
						"CANDADO PARA EQUIPO DE COMPUTO DE ESCRITORIO O PORTATIL HOSTNAME/ETIQUETA")) {
					if (!campo.getValor().isEmpty() && campo.getValor() != null && campo.getValor().length() > 9) {
						candado = campo.getValor();
					} else {
						candado = "-";
					}
				}
				if (campo.getNombreCampo().equalsIgnoreCase("NO BREAK TIPO I HOSTNAME/ETIQUETA")) {
					if (!campo.getValor().isEmpty() && campo.getValor() != null && campo.getValor().length() > 9) {
						ups = campo.getValor();
					} else {
						ups = "-";
					}
				}
				if(campo.getNombreCampo().equalsIgnoreCase("LECTOR DVD HOSTNAME/ETIQUETA")) {
					if (!campo.getValor().isEmpty() && campo.getValor() != null && campo.getValor().length() > 9) {
						dvd = campo.getValor();
					} else {
						dvd = "-";
					}
				}

				switch (campo.getTipoCampo()) {

				case "FIRMA":
					if (!firmas.isEmpty()) {
						System.out.println("Firma: " + firmas.get(indFirma).getUrl());
						URL url = new URL(firmas.get(indFirma).getUrl());
						parameters.put(campo.getNombreCampo(), url);
					}
					indFirma++;
					break;
//        		case "CODIGOBARRAS":
//        			try {
//        				System.out.println("Dentro del generador de codigo");
//						BufferedImage codigo = generarCodigoBarras(campo.getValor());
//						parameters.put("CODIGO", codigo);
//						parameters.put("CODIGOLABEL", "* "+campo.getValor()+" *");
//						
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//        			//BufferedImage codigo = ImageIO.read(getClass().getResource(firmas.get(indFirma).getAbsolutePath()));
//        			//InputStream stream = new FileInputStream(firmas.get(indFirma).getAbsolutePath());
//        			//URL url = this.getClass().getClassLoader().getResource(firmas.get(indFirma).getAbsolutePath());
//        			
//        			break;
				default:
    			System.out.println("Valor: " + campo.getValor());
    			 
					parameters.put(campo.getNombreCampo(), campo.getValor());
					break;

				}
			}
		}
		if (!ups.equals("-")) {
			codigoDeBarras += obtenerCodigo(ups, "UPS");
		}else {
			if (!candado.equals("-")) {
				codigoDeBarras += obtenerCodigo(candado, "CANDADO");
			}else {
				if(!dvd.equals("-")) {
					codigoDeBarras += obtenerCodigo(dvd,"DVD");
				}
			}
		}
		
		parameters.put("CODIGOLABEL", "*" + codigoDeBarras + "*");
		System.out.println("CODIGO DE BARRAS: *" +codigoDeBarras + "*");
		
		Inventario inventario = this.inventario.findById(idInventario).get();
		
		

		//proyecto = this.proyecto.obtenerProyectoPorNombre(nombreProyecto);
		
		proyecto = inventario.getProyecto();
		
		Alberca alberca = proyecto.getAlberca();
		
		parameters.put("ALBERCA", alberca.getNombrealberca());
		
		parameters.put("VOLUMEN", alberca.getCapacidad());
		
		URL urlCli = new URL(alberca.getSede().getCliente().getUrllogo());

		parameters.put("LOGO CLIENTE", urlCli);
		
		if(proyecto.getProyecto().equalsIgnoreCase("ANAM LAPTOP") || proyecto.getProyecto().equalsIgnoreCase("ANAM EQUIPO LIGERO")) {
			
			int idCampo = listaAgrupaciones.get(0).getCampos().get(0).getIdCampo();

			String folioAcuse = this.valores.obtenerIdValorValores(idCampo, idInventario);
			
			if(folioAcuse != null) {
				parameters.put("FOLIO ACUSE", folioAcuse);
			}else{
				
				String estado = parameters.get("ESTADO").toString();
				
				String conteoEstado = this.valores.obtenerConteoEstadosANAM(proyecto.getIdproyecto(),estado);
				
				String conteoFinal = String.valueOf(Integer.parseInt(conteoEstado) + 1);
				
				parameters.put("FOLIO ACUSE", "00"+conteoFinal);
			}
			
		}

		evidencias = obtenerCheckBoxEvidencia(proyecto.getIdproyecto(), idInventario);
		datos = new ArrayList<EvidenciaCheckList>();
		for (Fotoevidencia item : evidencias) {
			if (!item.getUrl().isEmpty()) {
				URL url = new URL(item.getUrl());
				datos.add(new EvidenciaCheckList(item.getNombrefoto(), url));
			}
		}
		if (!datos.isEmpty()) {
			System.out.println("Con evidencia");
			JRBeanCollectionDataSource itemJRBean = new JRBeanCollectionDataSource(datos);
			parameters.put("ColeccionBeanParam", itemJRBean);
		} else {
			System.out.println("Sin evidencia");
		}

		return parameters;
	}

	private String obtenerCodigo(String etiqueta, String tipo) {
		String respuesta = "";
		respuesta += etiqueta.substring(etiqueta.length() - 2, etiqueta.length());
		String codigoNoBreak = etiqueta.substring(3, etiqueta.length() - 2);
		int cantidadDeCeros = 7 - codigoNoBreak.length();
		System.out.println("Cantidad de ceros: " + cantidadDeCeros);
		//TODO: Saber la estructura del codigo de barras del DVD
		if (tipo.equals("CANDADO")) {
			respuesta += "CN" + codigoNoBreak;

		} else {
			if(tipo.equals("UPS")) {
				for (int i = 0; i < cantidadDeCeros; i++) {
					respuesta += "0";
				}
				respuesta += codigoNoBreak;
			}else if(tipo.equals("DVD")){
				if(etiqueta.length()> 10) {
					respuesta =  "21LD0" + etiqueta.substring(etiqueta.length()-4, etiqueta.length());
				}else {
					respuesta += "LD" + codigoNoBreak;
				}
				
			}
			
		}
		return respuesta;
	}

	private BufferedImage generarCodigoBarras(final String barcodeText) throws Exception {
		final Barcode barcode = BarcodeFactory.createCode128(barcodeText);

		barcode.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

		return BarcodeImageHandler.getImage(barcode);
	}

	private List<Fotoevidencia> obtenerCheckBoxEvidencia(int idProyecto, int idInventario) {
		List<Camposproyecto> lista = this.camposProyecto.obtenerCheckBoxEvidenciaPorProyecto(idProyecto);

		List<Fotoevidencia> evidencias = new ArrayList<Fotoevidencia>();
		for (Camposproyecto item : lista) {
			List<Fotoevidencia> evidencia = this.fotoEvidencia.obtenerFotoPorInventarioCampo(idInventario,
					item.getIdcamposproyecto());

			if (evidencia.isEmpty()) {
				evidencias
						.add(new Fotoevidencia(0, item.getCampo(), "", "", new Usuario(),
								new Inventario(idInventario, new Date(), "", "",
										new Proyecto(0, new Date(), "", new Tipoproyecto(0, ""))),
								new Camposproyecto()));
			} else {
				for (Fotoevidencia foto : evidencia) {
					evidencias.add(foto);
				}
			}
		}
		return evidencias;
	}

	private JasperReport loadTemplate(String nombreProyecto) throws JRException, Exception {
		System.out.println(String.format("Inventario load template!", new Object[0]));

		//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
		URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
		FileInputStream serviceAccount = new FileInputStream(descargarConector(url, "google-service-descarga.json"));
		String file_format = downloadTemplate(serviceAccount, nombreProyecto.contains("REPORTE SEMANAL") ? "REPORTE SEMANAL" : nombreProyecto);
		System.out.println("Plantilla proyecto: " + file_format);
		InputStream reportInputStream = null;
		InputStream employeeReportStream = null;

		if (file_format != null) {
			System.out.println(String.format("Inventario Proyect Template path : %s", new Object[] { file_format }));
			File file = new File(file_format);
			employeeReportStream = new FileInputStream(file);
			reportInputStream = employeeReportStream;
			JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

			return JasperCompileManager.compileReport(jasperDesign);
		}

		return null;
	}

	private String downloadTemplate(FileInputStream serviceAccount, String nombreProyecto) {
		try {

			String objectNameDef = "Plantillas/" + nombreProyecto + ".jrxml";
			System.out.println(objectNameDef);

			Storage storage = (Storage) getStrogaeOptions(serviceAccount).getService();
			File plantilla = File.createTempFile(nombreProyecto, ".jrxml");

			Blob blob1 = storage.get(BlobId.of("isae-de6da.appspot.com", objectNameDef));

			if (blob1 != null) {
				blob1.downloadTo(plantilla.toPath());
			}

			return plantilla.getPath();
		} catch (IOException e) {

			System.out.println("Exception [downloadTemplate()] :: " + e.getMessage());
			return null;
		}
	}

	private StorageOptions getStrogaeOptions(FileInputStream serviceAccount) throws IOException {
		StorageOptions storageOptions = ((StorageOptions.Builder) ((StorageOptions.Builder) StorageOptions.newBuilder()
				.setProjectId("isae-de6da")).setCredentials((Credentials) GoogleCredentials.fromStream(serviceAccount)))
						.build();
		return storageOptions;
	}

	private List<String> obtenerNombreFirmas(List<Agrupaciones> listaAgrupaciones) {
		List<String> listaFirmas = new ArrayList<String>();

		for (Agrupaciones agrupacion : listaAgrupaciones) {
			for (Campos campo : agrupacion.getCampos()) {
				if (campo.getTipoCampo().equals("FIRMA")) {
					listaFirmas.add(campo.getNombreCampo());
				}
			}
		}

		return listaFirmas;
	}

	private List<Integer> obtenerIdCampoFirmas(List<Agrupaciones> listaAgrupaciones) {
		List<Integer> listaIdCampoFirmas = new ArrayList<Integer>();

		for (Agrupaciones agrupacion : listaAgrupaciones) {
			for (Campos campo : agrupacion.getCampos()) {
				if (campo.getTipoCampo().equals("FIRMA")) {
					listaIdCampoFirmas.add(campo.getIdCampo());
				}
			}
		}

		return listaIdCampoFirmas;
	}

	private List<File> obtenerFirmas(List<Agrupaciones> listaAgrupaciones, int idInventario)
			throws MalformedURLException, IOException {
		List<File> firmas = new ArrayList<File>();

		List<String> listaNombreFirmas = obtenerNombreFirmas(listaAgrupaciones);

		List<Integer> listaIdCampoFirmas = obtenerIdCampoFirmas(listaAgrupaciones);

		List<firmasdocumento> listaDatosFirmas = new ArrayList<firmasdocumento>();

		for (int i = 0; i < listaNombreFirmas.size(); i++) {
			List<firmasdocumento> lista = this.firmas.obtenerConcidencia(listaNombreFirmas.get(i),
					listaIdCampoFirmas.get(i), idInventario);
			listaDatosFirmas.add(lista.get(0));
		}

		for (firmasdocumento datosFirma : listaDatosFirmas) {
			firmas.add(new File(descargarFrima(new URL(datosFirma.getUrl()), datosFirma.getNombrefirma() + ".png")));
		}

		return firmas;
	}

	private List<firmasdocumento> obtenerFirmasP(List<Agrupaciones> listaAgrupaciones, int idInventario) {
		List<File> firmas = new ArrayList<File>();

		List<String> listaNombreFirmas = obtenerNombreFirmas(listaAgrupaciones);

		List<Integer> listaIdCampoFirmas = obtenerIdCampoFirmas(listaAgrupaciones);

		List<firmasdocumento> listaDatosFirmas = new ArrayList<firmasdocumento>();

		for (int i = 0; i < listaNombreFirmas.size(); i++) {
			List<firmasdocumento> lista = this.firmas.obtenerConcidencia(listaNombreFirmas.get(i),
					listaIdCampoFirmas.get(i), idInventario);
			if (!lista.isEmpty()) {
				listaDatosFirmas.add(lista.get(0));
			} else {
				listaDatosFirmas.add(new firmasdocumento(0, listaNombreFirmas.get(i),
						"https://donantesdememoria.files.wordpress.com/2021/02/17541.jpg", new Camposproyecto(),
						new Inventario()));
			}
		}

		return listaDatosFirmas;
	}
	
	
	

	@CrossOrigin(origins = "*")
	@PostMapping("/generar/reporte/mensual")
	public void generarReporteMensualDRpool(@RequestBody Map<String, Object> listReport) throws IOException{
		
		Alberca alberca = new ObjectMapper().convertValue(listReport.get("ALBERCA"), Alberca.class);
		
		Sede sede = alberca.getSede();
		
		Reportemensual reportM = new Reportemensual();
		
		reportM = new Reportemensual(listReport.get("FOLIO").toString(), sede, alberca,listReport.get("FECHA").toString(), listReport.get("FIRSTDATE").toString(), listReport.get("LASTDATE").toString(), listReport.get("ALCALDIA").toString(), listReport.get("TIPOALBERCA").toString(),
				listReport.get("CARACTERISTICA").toString(), listReport.get("REALIZO").toString(),listReport.get("REVISO").toString(), "");
		
		System.out.println("Datos del reportM: " + reportM);
		
		int idreportemensual = (int) listReport.get("idreportemensual");
		
		System.out.println("ID reporte mensual recibido: " + idreportemensual);
		
		if(idreportemensual!=0) {
			reportM.setIdreportemensual(idreportemensual);
			
			List<Actividades> acti = this.actividades.getActividadesID(reportM.getIdreportemensual());
			
			for(Actividades ac : acti) {
				List<Actividadimagenes> actimg = this.actividadimagenes.getActividadImagenesID(ac.getIdactividades());
				
				for(Actividadimagenes aimg : actimg) {
					this.actividadimagenes.delete(aimg);
				}
				
				this.actividades.delete(ac);
			
			}
			
		}
		
		try {
			// Se guardan los datos del reporte mensual en su respectiva tabla, aun no se asigna el url del documento.
		    this.reportemensual.save(reportM);
		} catch (Exception e) {
		    e.printStackTrace(); // O loggea el mensaje de error
		}
		//Se descarga la plantilla de jasper
		try {
			//URL urlJSON = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
			URL urlJSON = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
			FileInputStream serviceAccount = new FileInputStream(descargarRecurso(urlJSON, "google-service-descarga.json"));
			//FileInputStream serviceAccount = new FileInputStream("google-services.json");
			String file_format = downloadTemplate(serviceAccount, "REPORTEMENSUAL");
			
			System.out.print("Se leyo el archivo");
			
			InputStream reportInputStream = null; 
			InputStream employeeReportStream = null; 
			
			if(file_format != null) {
				System.out.println("String.format(\"Inventario Proyect Template path : %s\", new Object[] { file_format })");
				
				File file = new File(file_format);
				employeeReportStream = new FileInputStream(file);
				reportInputStream = employeeReportStream;
				
				JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
				System.out.println("Se leyo el jasper design");
				
				JasperReport report = JasperCompileManager.compileReport(jasperDesign);
				System.out.println("Se creo el informe jasper");
				
				final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
						Collections.singletonList("Invoice"));
				
				//File reportFile = File.createTempFile("reporte", ".pdf", new File("/Users/isae_1/Desktop/"));
				//Se crea el documento
				File reportFile = File.createTempFile("reporte", ".pdf");

				
				//Creando el map a enviar para el reporte 
				
				Map<String, Object> parameters = new HashMap<>();
				
				//URL urlCliente = new URL((String) listReport.get("LOGO CLIENTE"));
				
				parameters.put("FECHA", listReport.get("FECHA"));
				parameters.put("FIRSTDATE", listReport.get("FIRSTDATE"));
				parameters.put("LASTDATE", listReport.get("LASTDATE"));
				//parameters.put("SEDE", listReport.get("SEDE"));
				parameters.put("ALCALDIA", listReport.get("ALCALDIA"));
				parameters.put("TIPOALBERCA", listReport.get("TIPOALBERCA"));
				//parameters.put("ALBERCA", listReport.get("ALBERCA"));
				parameters.put("REALIZO", listReport.get("REALIZO"));
				parameters.put("REVISO", listReport.get("REVISO"));
				parameters.put("CARACTERISTICA", listReport.get("CARACTERISTICA"));
				//parameters.put("LOGO CLIENTE", sede.getCliente().getUrllogo());
				
				URL urlLog = new URL(sede.getCliente().getUrllogo()); 
				parameters.put("LOGO CLIENTE", urlLog);
				
				parameters.put("SEDE", sede.getNombre());
				parameters.put("ALBERCA", alberca.getNombrealberca());
			
				
				//List de actividades
				
				List<Map<String,String>> listActivities = new ArrayList<Map<String,String>>();
				
				//Segunda hoja -------------------
				
				List<Map<String, Object>> list_page_images = new ArrayList<Map<String, Object>>();
				
				List<List<Object>> reportImages = (List<List<Object>>) listReport.get("REPORT_LIST_IMAGES");
				
				
				
				for(List<Object> rpt:reportImages) {
					//Creamos MAP temporales
					Map<String, Object> mapTemp = new HashMap<>();
					Map<String, String> mapTempActivities = new HashMap<>();
					List<Map<String, Object>> images_1 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> images_2 = new ArrayList<Map<String, Object>>();
					
					
					// LLenamos la lista de actividades de acuerdo a solicitado en el reporte 
					mapTempActivities.put("LIST",  ((Map<String,String>) rpt.get(0)).get("ACTIVITY"));
					listActivities.add(mapTempActivities);
					
					//Obtenemos el nombre de la actvidad de cada hoja
					mapTemp.put("ACTIVITY",  ((Map<String,Object>) rpt.get(0)).get("ACTIVITY"));
										
					
					String act = ((Map<String, Object>) rpt.get(0)).get("ACTIVITY").toString();
					String timg = ((Map<String,Object>) rpt.get(2)).get("TEXT_IMAGES").toString();
					String obs = ((Map<String,Object>) rpt.get(3)).get("OBSERVACIONES").toString();
					
					Actividades acti = new Actividades(reportM, act , timg, obs);
					//Guardamos en la base de datos la actividad
					this.actividades.save(acti);
					
					Actividadimagenes ai;

					//Obtemos una lista de imagenes 
					List<String> images = (List<String>) ((Map<String, Object>) rpt.get(1)).get("IMAGES");
					
					System.out.println("Report list images" + images);
					int cont = 1;
					//Convertimos cada String de url a un objecto Tipo URL
					for(String url : images) {
						URL img = new URL(url);
						//Mapa temporal de imagenes
						Map<String, Object> imgTemp = new HashMap<>();
						//Agregamos la imagen dependiendo de si es par o impar a una lista diferente, una por cada columna
						if(cont % 2 != 0) {
							imgTemp.put("IMG1", img);
							images_1.add(imgTemp);
							//Guardos en la base de datos las imagenes de la activdiad
							ai = new Actividadimagenes(acti, url);
							this.actividadimagenes.save(ai); 
						}else {
							imgTemp.put("IMG2", img);
							images_2.add(imgTemp);
							//Guardos en la base de datos las imagenes de la activdiad
							ai = new Actividadimagenes(acti, url);
							this.actividadimagenes.save(ai);
						}
						cont++;
					}
					
					//Creamos la coleccion de imagenes por cada columna para jasper report
					JRBeanCollectionDataSource jrcImages1 = new JRBeanCollectionDataSource(images_1);
					JRBeanCollectionDataSource jrcImages2 = new JRBeanCollectionDataSource(images_2);
					
					mapTemp.put("IMAGES_1", jrcImages1);
					mapTemp.put("IMAGES_2", jrcImages2);
					
					mapTemp.put("TEXT_IMAGES",  ((Map<String,Object>) rpt.get(2)).get("TEXT_IMAGES"));
					mapTemp.put("OBSERVACIONES",  ((Map<String,Object>) rpt.get(3)).get("OBSERVACIONES"));
					//Agregamos en mapTemp a la lista, cada uno es una hoja
					list_page_images.add(mapTemp);
					
				}
				
				JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(listActivities);
				
				JRBeanCollectionDataSource jrcImages = new JRBeanCollectionDataSource(list_page_images);
				
				parameters.put("CollectionBeanParam", jrc);
				
				parameters.put("REPORT_LIST_IMAGES", jrcImages);
			
				System.out.println(parameters);
				//Generamos el pdf en la ubicacion  de reportFile
				try(FileOutputStream fos = new FileOutputStream(reportFile)){
					JasperReportsUtils.renderAsPdf(report, parameters, dataSource, fos);
					System.out.println("Se creo el pdf");
					
				}
				
				String urlFirma = "";
				//Guardamos en firebase storage el pdf
				try {

					//URL url = new URL("https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
					URL url = new URL(
					"https://firebasestorage.googleapis.com/v0/b/dr-pool-eca1c.appspot.com/o/service%2Fdr-pool-eca1c-firebase-adminsdk-few7f-5b04f2906c.json?alt=media&token=e0caf9de-daa9-479d-904c-c1f323cd5012&_gl=1*1veermv*_ga*NTM3NzEyMjI5LjE2OTU5MzIzODU.*_ga_CW55HF8NVT*MTY5NTkzMjM4NS4xLjEuMTY5NTkzNDY0NS40NS4wLjA.");
					FileInputStream serviceAccountt = new FileInputStream(descargarRecurso(url, "google-service-descarga.json"));
					//String bucketName = "isae-de6da.appspot.com";
					String bucketName = "dr-pool-eca1c.appspot.com";
					boolean bandera = true;
					Storage storage = (Storage) getStrogaeOptions(serviceAccountt).getService();

					if (reportFile != null) {
						String nombre = "REPORTEMENSUAL";
						String objectName = "Proyectos/" + sede.getNombre() + "-" + alberca.getNombrealberca()  + "/Evidencias/" + nombre;

						Map<String, String> map = new HashMap<>();
						map.put("firebaseStorageDownloadTokens", nombre.replace(" ", "") + ".pdf");

						BlobId blobId = BlobId.of(bucketName, objectName);
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").setMetadata(map)
								.build();
						//El file se debe cambiar por el nombre del documento generado
						Blob b = storage.create(blobInfo, new FileInputStream(reportFile.getAbsolutePath()),
								new Storage.BlobWriteOption[0]);

						System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);

						String[] direccionTemporal = b.getSelfLink().split("/");

						urlFirma = generarUrl(direccionTemporal, b.getMetadata().get("firebaseStorageDownloadTokens"));

						System.out.println("URL del documento" + urlFirma);
						
						reportM.setUrl(urlFirma);
						//Se guarda la url en el mismo registro creado al principio 
						this.reportemensual.save(reportM);
					}

				} catch (IOException e) {
					System.out.println("Exception " + e.getMessage());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		
		
	}

	
	
	


/*
	@CrossOrigin("*")
	@PostMapping("/generar/reporte/mensual")
	private void generarReporteMensualDRpool(@RequestBody Map<String, Object> listReport) {
		
		System.out.println("Lista reporte mensual: "+ ((Map<String, Object>) listReport.get("SEDE")).get("idsede"));
		Sede sede = new Sede();
		Alberca alberca = new Alberca();
		Reportemensual rm = new Reportemensual();
		try {
		
		 sede = this.sede.getById((Integer) ((Map<String, Object>) listReport.get("SEDE")).get("idsede"));
		 
		 System.out.println("Se esta verificando que cargue la sede: " + sede);
		
		 alberca = this.alberca.getById((Integer) ((Map<String, Object>) listReport.get("ALBERCA")).get("idalberca"));
		 
		 System.out.println("Se esta verificando que cargue la alberca: " + alberca);

		
		rm = new Reportemensual(sede, alberca, listReport.get("FECHA").toString(), listReport.get("FIRSTDATE").toString(), listReport.get("LASTDATE").toString(), listReport.get("ALCALDIA").toString(), listReport.get("TIPOALBERCA").toString(),
				listReport.get("CARACTERISTICA").toString(), listReport.get("REALIZO").toString(),listReport.get("REVISO").toString(), "prueba url");
		
		
		this.reportemensual.save(rm);
		
		int idrm = rm.getIdreportemensual();
		}catch (Exception e) {
			// 
			System.out.println("Algo salio mal aqui: " + e);
		}
		
		try {
			FileInputStream serviceAccount = new FileInputStream("google-services.json");
			String file_format = downloadTemplate(serviceAccount, "REPORTEMENSUAL");
			
			System.out.print("Se leyo el archivo");
			
			InputStream reportInputStream = null; 
			InputStream employeeReportStream = null; 
			
			if(file_format != null) {
				System.out.println("String.format(\"Inventario Proyect Template path : %s\", new Object[] { file_format })");
				
				File file = new File(file_format);
				employeeReportStream = new FileInputStream(file);
				reportInputStream = employeeReportStream;
				
				JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
				System.out.println("Se leyo el jasper design");
				
				JasperReport report = JasperCompileManager.compileReport(jasperDesign);
				System.out.println("Se creo el informe jasper");
				
				final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
						Collections.singletonList("Invoice"));
				
				//File reportFile = File.createTempFile("reporte", ".pdf", new File("/Users/isae_1/Desktop/"));
				File reportFile = File.createTempFile("reporte", ".pdf");

				
				//Creando el map a enviar para el reporte 
				
				Map<String, Object> parameters = new HashMap<>();
				
				//URL urlCliente = new URL((String) listReport.get("LOGO CLIENTE"));
				
				parameters.put("FECHA", listReport.get("FECHA"));
				parameters.put("FIRSTDATE", listReport.get("FIRSTDATE"));
				parameters.put("LASTDATE", listReport.get("LASTDATE"));
				parameters.put("SEDE", listReport.get("SEDE"));
				parameters.put("ALCALDIA", listReport.get("ALCALDIA"));
				parameters.put("TIPOALBERCA", listReport.get("TIPOALBERCA"));
				parameters.put("ALBERCA", listReport.get("ALBERCA"));
				parameters.put("REALIZO", listReport.get("REALIZO"));
				parameters.put("REVISO", listReport.get("REVISO"));
				parameters.put("CARACTERISTICA", listReport.get("CARACTERISTICA"));
				//parameters.put("LOGO CLIENTE", urlCliente);
			
				
				//List de actividades
				
				List<Map<String,String>> listActivities = new ArrayList<Map<String,String>>();
				
				//Segunda hoja -------------------
				
				List<Map<String, Object>> list_page_images = new ArrayList<Map<String, Object>>();
				
				List<List<Object>> reportImages = (List<List<Object>>) listReport.get("REPORT_LIST_IMAGES");
				
				for(List<Object> rpt:reportImages) {
					//Creamos MAP temporales
					Map<String, Object> mapTemp = new HashMap<>();
					Map<String, String> mapTempActivities = new HashMap<>();
					List<Map<String, Object>> images_1 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> images_2 = new ArrayList<Map<String, Object>>();
					
					
					// LLenamos la lista de actividades de acuerdo a solicitado en el reporte 
					mapTempActivities.put("LIST",  ((Map<String,String>) rpt.get(0)).get("ACTIVITY"));
					listActivities.add(mapTempActivities);
					
					//Obtenemos el nombre de la actvidad de cada hoja
					mapTemp.put("ACTIVITY",  ((Map<String,Object>) rpt.get(0)).get("ACTIVITY"));
										
					
					String act = ((Map<String, Object>) rpt.get(0)).get("ACTIVITY").toString();
					String timg = ((Map<String,Object>) rpt.get(2)).get("TEXT_IMAGES").toString();
					String obs = ((Map<String,Object>) rpt.get(3)).get("OBSERVACIONES").toString();
					
					Actividades acti = new Actividades(rm, act , timg, obs);
					
					this.actividades.save(acti);
					
					Actividadimagenes ai;
					
					
					
					//Obtemos una lista de imagenes 
					List<String> images = (List<String>) ((Map<String, Object>) rpt.get(1)).get("IMAGES");
					int cont = 1;
					//Convertimos cada String de url a un objecto Tipo URL
					for(String url : images) {
						URL img = new URL(url);
						//Mapa temporal de imagenes
						Map<String, Object> imgTemp = new HashMap<>();
						//Agregamos la imagen dependiendo de si es par o impar a una lista diferente, una por cada columna
						if(cont % 2 != 0) {
							imgTemp.put("IMG1", img);
							images_1.add(imgTemp);
							ai = new Actividadimagenes(acti, url);
							this.actividadimagenes.save(ai); 
						}else {
							imgTemp.put("IMG2", img);
							images_2.add(imgTemp);
							ai = new Actividadimagenes(acti, url);
							this.actividadimagenes.save(ai);
						}
						cont++;
					}
					
					//Creamos la coleccion de imagenes por cada columna para jasper report
					JRBeanCollectionDataSource jrcImages1 = new JRBeanCollectionDataSource(images_1);
					JRBeanCollectionDataSource jrcImages2 = new JRBeanCollectionDataSource(images_2);
					
					mapTemp.put("IMAGES_1", jrcImages1);
					mapTemp.put("IMAGES_2", jrcImages2);
					
					mapTemp.put("TEXT_IMAGES",  ((Map<String,Object>) rpt.get(2)).get("TEXT_IMAGES"));
					mapTemp.put("OBSERVACIONES",  ((Map<String,Object>) rpt.get(3)).get("OBSERVACIONES"));
					//Agregamos en mapTemp a la lista, cada uno es una hoja
					list_page_images.add(mapTemp);
					
					

					
				}
				
				JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(listActivities);
				
				JRBeanCollectionDataSource jrcImages = new JRBeanCollectionDataSource(list_page_images);
				
				parameters.put("CollectionBeanParam", jrc);
				
				parameters.put("REPORT_LIST_IMAGES", jrcImages);
			
				System.out.println(parameters);
				//Generamos el pdf en la ubicacion  de reportFile
				try(FileOutputStream fos = new FileOutputStream(reportFile)){
					JasperReportsUtils.renderAsPdf(report, parameters, dataSource, fos);
					System.out.println("Se creo el pdf");
					
				}
			*/
				//respuesta.put("nombre", );
				/*modificaciones
				respuesta = guardarEvidencia(inventario, invoicePdf, nombreEvidencia);
				List<Documentogenerado> lista = this.documentoGenerado.obtenerDocumento(Integer.parseInt(idInventario));
				Documentogenerado documento = new Documentogenerado(0, nombreEvidencia, respuesta, inventario);
				if (lista.isEmpty()) {
					this.documentoGenerado.save(documento);
				} else {
					documento.setIddocumento(lista.get(0).getIddocumento());
					this.documentoGenerado.save(documento);
				}
				*/
				/*
				try {

					URL url = new URL(
							"https://firebasestorage.googleapis.com/v0/b/isae-de6da.appspot.com/o/Services%2Fgoogle-services.json?alt=media&token=142d6393-2405-44d4-bc20-6de945e391bc");
					FileInputStream serviceAccountt = new FileInputStream(descargarRecurso(url, "google-service-descarga.json"));
					String bucketName = "isae-de6da.appspot.com";
					boolean bandera = true;
					Storage storage = (Storage) getStrogaeOptions(serviceAccountt).getService();

					if (file != null) {
						String nombre = "REPORTEMENSUAL" + "-" +sede.getNombre();
						String objectName = "Proyectos/" + sede.getNombre() + "-"
								+ alberca.getNombrealberca().toUpperCase() + "/Evidencias/" + nombre;

						Map<String, String> map = new HashMap<>();
						map.put("firebaseStorageDownloadTokens", "REPORTE MENSUAL PRUEBA".replace(" ", "") + ".pdf");

						BlobId blobId = BlobId.of(bucketName, objectName);
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").setMetadata(map)
								.build();

						Blob b = storage.create(blobInfo, new FileInputStream(file.getAbsolutePath()),
								new Storage.BlobWriteOption[0]);

						System.out.println("File format uploaded to bucket " + bucketName + " as " + objectName);

		// Sirve para crear un link temporal para la descarga del archivo almacenado en Firebase
//						URL urlFirma = storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

						String[] direccionTemporal = b.getSelfLink().split("/");

						String urlFirma = generarUrl(direccionTemporal, b.getMetadata().get("firebaseStorageDownloadTokens"));
						
						System.out.println("URL del documento: " + urlFirma);

					}

				} catch (IOException e) {

					System.out.println("Exception " + e.getMessage());
				
				}

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	} */
}

