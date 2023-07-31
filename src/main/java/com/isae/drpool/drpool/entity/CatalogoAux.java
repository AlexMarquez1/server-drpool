package com.isae.drpool.drpool.entity;

import java.util.List;


public class CatalogoAux {

	private String tipoCatalogo;
	private Proyecto proyecto;
	private List<String> catalogo;
	
	
	public CatalogoAux(String tipoCatalogo, Proyecto proyecto, List<String> catalogo) {
		this.tipoCatalogo = tipoCatalogo;
		this.proyecto = proyecto;
		this.catalogo = catalogo;
	}


	public CatalogoAux() {
	}


	public String getTipoCatalogo() {
		return tipoCatalogo;
	}


	public void setTipoCatalogo(String tipoCatalogo) {
		this.tipoCatalogo = tipoCatalogo;
	}


	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}


	public List<String> getCatalogo() {
		return catalogo;
	}


	public void setCatalogo(List<String> catalogo) {
		this.catalogo = catalogo;
	}


	@Override
	public String toString() {
		return "Catalogo [tipoCatalogo=" + tipoCatalogo + ", proyecto=" + proyecto + ", catalogo=" + catalogo + "]";
	}
}
