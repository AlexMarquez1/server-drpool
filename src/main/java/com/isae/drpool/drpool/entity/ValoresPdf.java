package com.isae.drpool.drpool.entity;

public class ValoresPdf {

	private String nombreCampo;
	private String valor;
	
	
	public ValoresPdf(String nombreCampo, String valor) {
		this.nombreCampo = nombreCampo;
		this.valor = valor;
	}


	public ValoresPdf() {
	}


	public String getNombreCampo() {
		return nombreCampo;
	}


	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	@Override
	public String toString() {
		return "ValoresPdf [nombreCampo=" + nombreCampo + ", valor=" + valor + "]";
	}
	
	
	
}
