package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
public class ReporteFotografico {
	
	@CrossOrigin(origins = "*")
	@GetMapping("/pruebareporte")
	public void generateReport() throws MalformedURLException {
		
		Map<String, Object> parameters = new HashMap<>();
		Map<String, String> parameters1 = new HashMap<>();
		Map<String, String> parameters2 = new HashMap<>();
		Map<String, String> parameters3 = new HashMap<>();
		Map<String, String> parameters4 = new HashMap<>();
		Map<String, String> parameters5 = new HashMap<>();
		Map<String, String> parameters6 = new HashMap<>();
		List<Map<String, String>> parametersAll = new ArrayList<Map<String,String>>();
		
		
		
		InputStream reportInputStream = null;
		InputStream employeeReportStream = null;
		
		parameters.put("FECHA", "13/09/2023");
		parameters.put("FIRSTDATE", "14/09/2023");
		parameters.put("LASTDATE", "30/11/2023");
		parameters.put("SEDE", "TLAHUAC");
		parameters.put("ALCALDIA", "EDOMEX");
		parameters.put("TIPOALBERCA", "REDONDA");
		parameters1.put("LIST", "ELEMENTOOOOOO 1");
		parameters2.put("LIST", "ELEMENTOOOOOO 2");
		parameters3.put("LIST", "ELEMENTOOOOOO 3");
		parameters4.put("LIST", "ELEMENTOOOOOO 4");
		parameters5.put("LIST", "ELEMENTOOOOOO 5");
		parameters6.put("LIST", "ELEMENTOOOOOO 6");
		
		parametersAll.add(parameters1);
		parametersAll.add(parameters2);
		parametersAll.add(parameters3);
		parametersAll.add(parameters4);
		parametersAll.add(parameters5);
		parametersAll.add(parameters6);
		
		JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(parametersAll);
		
		parameters.put("CollectionBeanParam", jrc);
		//Lista principal -> contiene las demas listas
		List<Map<String, Object>> list_page_images = new ArrayList<Map<String,Object>>();
		
		//lista secundaria que contiene las imagenes de la columna 1
		List<Map<String, Object>> images_1 = new ArrayList<Map<String, Object>>();
		//Images de la lista 1
		Map<String, Object> img1 = new HashMap<>();
		Map<String, Object> img2 = new HashMap<>();
		Map<String, Object> img3 = new HashMap<>();
		
		URL url = new URL("https://i0.wp.com/a0.muscache.com/im/pictures/miso/Hosting-52985510/original/1df75da2-c198-4f53-b7a9-4c848a3a4c39.jpeg");
		img1.put("IMG1", url);
		img2.put("IMG1", url);
		img3.put("IMG1", url);
		
		//CARGAR IMAGENES COLUMNA 1
		images_1.add(img1);
		images_1.add(img2);
		images_1.add(img3);
		
		//Lista secundaria que contiene las imagenes de la columna 2
		List<Map<String, Object>> images_2 = new ArrayList<Map<String, Object>>();
		//Imagenes de la lista 2
		Map<String, Object> img4 = new HashMap<>();
		img4.put("IMG2", url);
		
		//CARGAR IMAGENES COLUMNA 2
		images_2.add(img4);
		
		JRBeanCollectionDataSource jrcImages1 = new JRBeanCollectionDataSource(images_1);
		JRBeanCollectionDataSource jrcImages2 = new JRBeanCollectionDataSource(images_2);
		
		Map<String, Object> imagenesCollection = new HashMap<>();
		
		Map<String, Object> imagenesCollection2 = new HashMap<>();
		
		
		//ESTA ES LA BUENA
		 
		imagenesCollection.put("ACTIVITY","PRUEBA PAGINA 1 DE ACTIVIDAD"); //String
		imagenesCollection.put("IMAGES_1", jrcImages1); // list
		imagenesCollection.put("IMAGES_2", jrcImages2); // list
		imagenesCollection.put("TEXT_IMAGES", "Texto de prueba que estara debajo de las imagenes, este texto contendra una pequeña descripcion sobre las actividades que se realizaron en el lugar de las fotos"); // String
		
		imagenesCollection2.put("ACTIVITY","PRUEBA PAGINA 2 DE ACTIVIDAD");
		
		list_page_images.add(imagenesCollection);
		list_page_images.add(imagenesCollection2);
		

		
		JRBeanCollectionDataSource jrcImages = new JRBeanCollectionDataSource(list_page_images);
		
		parameters.put("REPORT_LIST_IMAGES", jrcImages);
		
		System.out.println(parameters);
		
		
		
		try {
			File file = new File("/Users/isae_1/JaspersoftWorkspace/Prueba/reporteMensual.jrxml");
			
			employeeReportStream = new FileInputStream(file);
			reportInputStream = employeeReportStream;
			
			JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
			System.out.println("Se leyo el jasper design");
			
			JasperReport report = JasperCompileManager.compileReport(jasperDesign);
			System.out.println("Se creo el informe.jasper");
			
			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					Collections.singletonList("Invoice"));
			
			File reportFile = File.createTempFile("reporte", ".pdf", new File("/Users/isae_1/Desktop/"));
			
			try(FileOutputStream fos = new FileOutputStream(reportFile)){
				JasperReportsUtils.renderAsPdf(report, parameters, dataSource, fos);
				System.out.println("Se creo el pdf");
			}

			

		}catch(Exception e) {
			System.out.print(e);
		}
		
	}
	
}
