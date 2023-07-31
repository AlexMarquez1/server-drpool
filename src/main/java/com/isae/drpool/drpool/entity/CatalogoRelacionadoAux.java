package com.isae.drpool.drpool.entity;

import java.util.List;

public class CatalogoRelacionadoAux {
	private String tipoCatalogoPadre;
	private String catalogoPadre;
	private String tipoCatalogoHijo;
	private List<String> catalogoHijo;
	
	
	public CatalogoRelacionadoAux(String tipoCatalogoPadre, String catalogoPadre, String tipoCatalogoHijo,
			List<String> catalogoHijo) {
		this.tipoCatalogoPadre = tipoCatalogoPadre;
		this.catalogoPadre = catalogoPadre;
		this.tipoCatalogoHijo = tipoCatalogoHijo;
		this.catalogoHijo = catalogoHijo;
	}


	public CatalogoRelacionadoAux() {
	}


	public String getTipoCatalogoPadre() {
		return tipoCatalogoPadre;
	}


	public void setTipoCatalogoPadre(String tipoCatalogoPadre) {
		this.tipoCatalogoPadre = tipoCatalogoPadre;
	}


	public String getCatalogoPadre() {
		return catalogoPadre;
	}


	public void setCatalogoPadre(String catalogoPadre) {
		this.catalogoPadre = catalogoPadre;
	}


	public String getTipoCatalogoHijo() {
		return tipoCatalogoHijo;
	}


	public void setTipoCatalogoHijo(String tipoCatalogoHijo) {
		this.tipoCatalogoHijo = tipoCatalogoHijo;
	}


	public List<String> getCatalogoHijo() {
		return catalogoHijo;
	}


	public void setCatalogoHijo(List<String> catalogoHijo) {
		this.catalogoHijo = catalogoHijo;
	}


	@Override
	public String toString() {
		return "CatalogoRelacionado [tipoCatalogoPadre=" + tipoCatalogoPadre + ", catalogoPadre=" + catalogoPadre
				+ ", tipoCatalogoHijo=" + tipoCatalogoHijo + ", catalogoHijo=" + catalogoHijo + "]";
	}

}
