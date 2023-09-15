package com.isae.drpool.drpool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
	public void generateReport() {
		
		Map<String, Object> parameters = new HashMap<>();
		Map<String, String> parameters1 = new HashMap<>();
		Map<String, String> parameters2 = new HashMap<>();
		Map<String, String> parameters3 = new HashMap<>();
		List<Map<String, String>> parametersAll = new ArrayList<Map<String,String>>();
		
		
		
		InputStream reportInputStream = null;
		InputStream employeeReportStream = null;
		
		parameters.put("FECHA", "13/09/2023");
		parameters.put("FIRSTDATE", "14/09/2023");
		parameters.put("LASTDATE", "30/11/2023");
		parameters.put("SEDE", "TLAHUAC");
		parameters.put("ALCALDIA", "EDOMEX");
		parameters.put("TIPOALBERCA", "REDONDA");
		parameters1.put("LIST", "ELEMENTO 1");
		parameters2.put("LIST", "ELEMENTO 2");
		parameters3.put("LIST", "ELEMENTO 3");
		
		parametersAll.add(parameters1);
		parametersAll.add(parameters2);
		parametersAll.add(parameters3);
		
		JRBeanCollectionDataSource jrc = new JRBeanCollectionDataSource(parametersAll);
		
		parameters.put("CollectionBeanParam", jrc);
		
		
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
			
			File reportFile = File.createTempFile("reporte", "pdf", new File("/Users/isae_1/Desktop/"));
			
			try(FileOutputStream fos = new FileOutputStream(reportFile)){
				JasperReportsUtils.renderAsPdf(report, parameters, dataSource, fos);
				System.out.println("Se creo el pdf");
			}

			

		}catch(Exception e) {
			System.out.print(e);
		}
		
	}

}
