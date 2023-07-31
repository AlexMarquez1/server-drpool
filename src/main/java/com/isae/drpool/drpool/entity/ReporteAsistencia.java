package com.isae.drpool.drpool.entity;

public class ReporteAsistencia {
	private String responsable;
	private String proyecto;
	private String usuario;
	private String puesto;
	private String zona;
	private String sueldoMensual;
	private String diaAsistencia;
	
	public ReporteAsistencia(String responsable, String proyecto, String usuario, String puesto, String zona,
			String sueldoMensual, String diaAsistencia) {
		this.responsable = responsable;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.puesto = puesto;
		this.zona = zona;
		this.sueldoMensual = sueldoMensual;
		this.diaAsistencia = diaAsistencia;
	}

	public ReporteAsistencia() {
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getSueldoMensual() {
		return sueldoMensual;
	}

	public void setSueldoMensual(String sueldoMensual) {
		this.sueldoMensual = sueldoMensual;
	}

	public String getDiaAsistencia() {
		return diaAsistencia;
	}

	public void setDiaAsistencia(String diaAsistencia) {
		this.diaAsistencia = diaAsistencia;
	}

	@Override
	public String toString() {
		return "ReporteAsistencia [responsable=" + responsable + ", proyecto=" + proyecto + ", usuario=" + usuario
				+ ", puesto=" + puesto + ", zona=" + zona + ", sueldoMensual=" + sueldoMensual + ", diaAsistencia="
				+ diaAsistencia + "]";
	}
}
