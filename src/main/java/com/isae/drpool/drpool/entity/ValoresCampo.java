package com.isae.drpool.drpool.entity;

public class ValoresCampo {

	private String valor;
	private int idCampo;
	private int idInventario;
	
	public ValoresCampo(String valor, int idCampo, int idInventario) {
		this.valor = valor;
		this.idCampo = idCampo;
		this.idInventario = idInventario;
	}

	public ValoresCampo() {
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	public int getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(int idInventario) {
		this.idInventario = idInventario;
	}

	@Override
	public String toString() {
		return "ValoresCampo [valor=" + valor + ", idCampo=" + idCampo + ", idInventario=" + idInventario + "]";
	}
	
	
}
