package com.isae.drpool.drpool.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isae.drpool.drpool.dao.IActividadesDAO;
import com.isae.drpool.drpool.dao.IActividadimagenesDAO;
import com.isae.drpool.drpool.dao.IReportemensualDAO;
import com.isae.drpool.drpool.entity.Actividades;
import com.isae.drpool.drpool.entity.Actividadimagenes;
import com.isae.drpool.drpool.entity.Reportemensual;



@RestController
public class ReporteMensualRestController {
	
	
	@Autowired
	private IReportemensualDAO reportemensual;
	
	@Autowired
	private IActividadimagenesDAO actividadimagenes;
	
	@Autowired
	private IActividadesDAO actividades;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/obtener/reportesmensuales")
	public List<Map<String, Object>> obtenerRM(){
		
		List<Map<String, Object>> rmList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> rm;
		
		List<Map<String, Object>> actividadesList;
		List<List<Map<String, Object>>> actividadesListFinal;
		Map<String, Object> actividadesRM;
		
		List<Reportemensual> listReportMensual = this.reportemensual.findAll();
		
		/*for(Reportemensual reportemensual : listReportMensual) {
			
			rm = new HashMap<>();
			actividadesListFinal = new ArrayList<Map<String,Object>>();
			
			
			int idreportemensual = reportemensual.getIdreportemensual();
			
			rm.put("IDREPORTEMENSUAL", reportemensual.getIdreportemensual());
			rm.put("FECHA", reportemensual.getFecha());
			rm.put("FIRSTDATE", reportemensual.getPeriodoinicial());
			rm.put("LASTDATE", reportemensual.getPeriodofinal());
			rm.put("SEDE", reportemensual.getSede());
			rm.put("ALCALDIA", reportemensual.getAlcaldia());
			rm.put("ALBERCA", reportemensual.getAlberca());
			rm.put("TIPOALBERCA", reportemensual.getTipoalberca());
			rm.put("CARACTERISTICA", reportemensual.getCaracteristicaalberca());
			rm.put("REALIZO", reportemensual.getRealizo());
			rm.put("REVISO", reportemensual.getReviso());
			
			List<Actividades> actividades = this.actividades.getActividadesID(idreportemensual);
			
			for(Actividades acti : actividades) {
				
				actividadesRM = new HashMap<String, Object>();
				
				
				int idactividad = acti.getIdactividades();				
				
				List<Actividadimagenes> actividadimg = this.actividadimagenes.getActividadImagenesID(idactividad);
				
				List<String> imgs = new ArrayList<String>();
				
				for(Actividadimagenes img : actividadimg) {
					imgs.add(img.getUrl());
				}
				
				actividadesList = new ArrayList<Map<String,Object>>();
				
				actividadesRM.put("ACTIVITY", acti.getTipoactividad());
				actividadesList.add(actividadesRM);
				actividadesRM = new HashMap<String, Object>();
				actividadesRM.put("IMAGES", imgs);
				actividadesList.add(actividadesRM);
				actividadesRM = new HashMap<String, Object>();
				actividadesRM.put("TEXT_IMAGES", acti.getTextoimagenes());
				actividadesList.add(actividadesRM);
				actividadesRM = new HashMap<String, Object>();
				actividadesRM.put("OBSERVACIONES", acti.getObservaciones());
				actividadesList.add(actividadesRM);
				actividadesRM = new HashMap<String, Object>();
				
				
				actividadesListFinal.add(actividadesList);
				
				
	
			}

			
			rm.put("REPORT_LIST_IMAGES", actividadesListFinal);
			
			rmList.add(rm);
			
			
		}*/
		
		for (Reportemensual reportemensual : listReportMensual) {
		    rm = new HashMap<>();
		    actividadesListFinal = new ArrayList<List<Map<String, Object>>>(); // Usar List<List> en lugar de List

		    int idreportemensual = reportemensual.getIdreportemensual();

		    rm.put("IDREPORTEMENSUAL", reportemensual.getIdreportemensual());
		    rm.put("FECHA", reportemensual.getFecha());
		    rm.put("FIRSTDATE", reportemensual.getPeriodoinicial());
		    rm.put("LASTDATE", reportemensual.getPeriodofinal());
		    rm.put("SEDE", reportemensual.getSede());
		    rm.put("ALCALDIA", reportemensual.getAlcaldia());
		    rm.put("ALBERCA", reportemensual.getAlberca());
		    rm.put("TIPOALBERCA", reportemensual.getTipoalberca());
		    rm.put("CARACTERISTICA", reportemensual.getCaracteristicaalberca());
		    rm.put("REALIZO", reportemensual.getRealizo());
		    rm.put("REVISO", reportemensual.getReviso());
		    rm.put("URLPDF", reportemensual.getUrl());

		    List<Actividades> actividades = this.actividades.getActividadesID(idreportemensual);

		    for (Actividades acti : actividades) {
		        actividadesRM = new HashMap<String, Object>();

		        int idactividad = acti.getIdactividades();

		        List<Actividadimagenes> actividadimg = this.actividadimagenes.getActividadImagenesID(idactividad);

		        List<String> imgs = new ArrayList<String>();

		        for (Actividadimagenes img : actividadimg) {
		            imgs.add(img.getUrl());
		        }
		        List<Map<String, Object>> actividadSubLista = new ArrayList<Map<String, Object>>();

		        actividadesRM.put("ACTIVITY", acti.getTipoactividad());
		        actividadSubLista.add(actividadesRM);
		        actividadesRM = new HashMap<String, Object>();
		        actividadesRM.put("IMAGES", imgs);
		        actividadSubLista.add(actividadesRM);
		        actividadesRM = new HashMap<String, Object>();
		        actividadesRM.put("TEXT_IMAGES", acti.getTextoimagenes());
		        actividadSubLista.add(actividadesRM);
		        actividadesRM = new HashMap<String, Object>();
		        actividadesRM.put("OBSERVACIONES", acti.getObservaciones());
		        actividadSubLista.add(actividadesRM);
		        actividadesRM = new HashMap<String, Object>();
		        
//		        actividadSubLista.add(actividadesRM);

		        // Agrega la sub-lista a actividadesListFinal
		        actividadesListFinal.add(actividadSubLista);
		    }

		    // Envuelve todas las sub-listas en un conjunto de corchetes [] y colócalas en "REPORT_LIST_IMAGES"
		    rm.put("REPORT_LIST_IMAGES", actividadesListFinal);
		    rmList.add(rm);
		}

		
		
		return rmList; 
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/editar/reportemensual")
	public void updateRM() {
		
		
		
	}
	
	/*
	 * List<Map<String, Object>> listRM = new ArrayList<Map<String,Object>>();
		
		List<Actividadimagenes> listReport = this.actividadimagenes.findAll();
		
		//Map<String, Object> rm = new HashMap<String, Object>();
		
		List<Map<String, Object>> report_list_images = new ArrayList<Map<String, Object>>();
		
		Map<Integer, Map<String, Object>> mapPorIdReporte = new HashMap<>();
		
		
		System.out.println("Lista: " + listReport);

		
		for(Actividadimagenes actividadimagenes : listReport) {
			
			int idReporteMensual = actividadimagenes.getActividades().getReportemensual().getIdreportemensual();
		    
		    // Verifica si ya existe un mapa para ese idreportemensual
		    if (!mapPorIdReporte.containsKey(idReporteMensual)) {
		        mapPorIdReporte.put(idReporteMensual, new HashMap<>());
		    }

		    Map<String, Object> rm = mapPorIdReporte.get(idReporteMensual);
			
			
			rm.put("FECHA", actividadimagenes.getActividades().getReportemensual().getFecha());
			rm.put("FIRSTDATE", actividadimagenes.getActividades().getReportemensual().getPeriodoinicial());
			rm.put("LASTDATE", actividadimagenes.getActividades().getReportemensual().getPeriodofinal());
			rm.put("SEDE", actividadimagenes.getActividades().getReportemensual().getSede());
			rm.put("ALCALDIA", actividadimagenes.getActividades().getReportemensual().getAlcaldia());
			rm.put("ALBERCA", actividadimagenes.getActividades().getReportemensual().getAlberca());
			rm.put("TIPOALBERCA", actividadimagenes.getActividades().getReportemensual().getTipoalberca());
			rm.put("CARACTERISTICA", actividadimagenes.getActividades().getReportemensual().getCaracteristicaalberca());
			rm.put("REALIZO", actividadimagenes.getActividades().getReportemensual().getRealizo());
			rm.put("REVISO", actividadimagenes.getActividades().getReportemensual().getRealizo());
			
			
		}
		
		System.out.println("Lista de idrm: " + mapPorIdReporte);
		
		return listRM;
	 * */

}
