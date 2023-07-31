package com.isae.drpool.drpool.entity;

public class Campos {
	
	private int idCampo;
	private String agrupacion;
	private String nombreCampo;
	private String validarduplicidad;
	private String tipoCampo;
	private String restriccion;
	private String editable;
	private String alerta;
	private int longitud;
	private String valor;
	private String pordefecto;
	
	public Campos() {
		
	}

	public Campos(int idCampo, String agrupacion, String nombreCampo, String validarduplicidad, String tipoCampo,
			String restriccion, String editable, String alerta, int longitud, String valor, String pordefecto) {
		this.idCampo = idCampo;
		this.agrupacion = agrupacion;
		this.nombreCampo = nombreCampo;
		this.validarduplicidad = validarduplicidad;
		this.tipoCampo = tipoCampo;
		this.restriccion = restriccion;
		this.editable = editable;
		this.alerta = alerta;
		this.longitud = longitud;
		this.valor = valor;
		this.pordefecto = pordefecto;
	}

	public int getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	public String getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public String getValidarduplicidad() {
		return validarduplicidad;
	}

	public void setValidarduplicidad(String validarduplicidad) {
		this.validarduplicidad = validarduplicidad;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String getRestriccion() {
		return restriccion;
	}

	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPordefecto() {
		return pordefecto;
	}

	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}

	@Override
	public String toString() {
		return "Campos [idCampo=" + idCampo + ", agrupacion=" + agrupacion + ", nombreCampo=" + nombreCampo
				+ ", validarduplicidad=" + validarduplicidad + ", tipoCampo=" + tipoCampo + ", restriccion="
				+ restriccion + ", editable=" + editable + ", alerta=" + alerta + ", longitud=" + longitud + ", valor="
				+ valor + ", pordefecto=" + pordefecto + "]";
	}
}
