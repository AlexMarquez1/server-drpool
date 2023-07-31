package com.isae.drpool.drpool.utils;

import java.util.ArrayList;
import java.util.List;

import com.isae.drpool.drpool.dao.IValoresDAO;
import com.isae.drpool.drpool.entity.Agrupaciones;
import com.isae.drpool.drpool.entity.Campos;
import com.isae.drpool.drpool.entity.Valore;

public class ObtenerDatos {
	
	public List<Agrupaciones> getDatosRegistro(String idRegistro, String idProyecto, IValoresDAO valores) {
		System.out.println(idRegistro);
		System.out.println(idProyecto);
		List<Agrupaciones> listaAgrupaciones = new ArrayList<Agrupaciones>();
		List<String> agrupaciones = new ArrayList<String>();
		List<Campos> campos = new ArrayList<Campos>();
		Campos campo = new Campos();
		List<Valore> lista = valores.obtenerDatosCampoPorProyecto(Integer.parseInt(idRegistro),
				Integer.parseInt(idProyecto));
		for (Valore item : lista) {
			campo = new Campos();
			campo.setIdCampo(item.getCamposproyecto().getIdcamposproyecto());
			campo.setNombreCampo(item.getCamposproyecto().getCampo());
			campo.setValidarduplicidad(item.getCamposproyecto().getValidarduplicidad());
			campo.setEditable(item.getCamposproyecto().getEditable());
			campo.setTipoCampo(item.getCamposproyecto().getTipocampo());
			campo.setRestriccion(item.getCamposproyecto().getPattern());
			campo.setAlerta(item.getCamposproyecto().getAlerta());
			campo.setAgrupacion(item.getCamposproyecto().getAgrupacion().getAgrupacion());
			campo.setLongitud(item.getCamposproyecto().getLongitud());
			campo.setValor(item.getValor());
			campo.setPordefecto(item.getCamposproyecto().getPordefecto());

			if (agrupaciones.isEmpty() && listaAgrupaciones.isEmpty()) {
				agrupaciones.add(item.getCamposproyecto().getAgrupacion().getAgrupacion());
				campos.add(campo);
				listaAgrupaciones.add(new Agrupaciones(item.getCamposproyecto().getAgrupacion().getIdagrupacion(),
						item.getCamposproyecto().getAgrupacion().getAgrupacion(), item.getInventario().getIdinventario(), campos));
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
							item.getCamposproyecto().getAgrupacion().getAgrupacion(), item.getInventario().getIdinventario(), campos));
				}
			}
		}
		return listaAgrupaciones;
	}

}
