package com.isae.drpool.drpool.entity;

import java.util.List;

public class Evidencia {
	
	private int idEvidencia;
	private String nombreEvidencia;
	private List<Integer> evidencia;
	private Camposproyecto camposProyecto;
	private Inventario inventario;
	private String coordenadas;
	
	public Evidencia(int idEvidencia, String nombreEvidencia, List<Integer> evidencia, Camposproyecto camposProyecto,
			Inventario inventario) {
		this.idEvidencia = idEvidencia;
		this.nombreEvidencia = nombreEvidencia;
		this.evidencia = evidencia;
		this.camposProyecto = camposProyecto;
		this.inventario = inventario;
	}

	public Evidencia() {
	}

	public int getIdEvidencia() {
		return idEvidencia;
	}

	public void setIdEvidencia(int idEvidencia) {
		this.idEvidencia = idEvidencia;
	}

	public String getNombreEvidencia() {
		return nombreEvidencia;
	}

	public void setNombreEvidencia(String nombreEvidencia) {
		this.nombreEvidencia = nombreEvidencia;
	}

	public List<Integer> getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(List<Integer> evidencia) {
		this.evidencia = evidencia;
	}

	public Camposproyecto getCamposProyecto() {
		return camposProyecto;
	}

	public void setCamposProyecto(Camposproyecto camposProyecto) {
		this.camposProyecto = camposProyecto;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	@Override
	public String toString() {
		return "Evidencia [idEvidencia=" + idEvidencia + ", nombreEvidencia=" + nombreEvidencia + ", evidencia="
				+ evidencia + ", camposProyecto=" + camposProyecto + ", inventario=" + inventario + ", coordenadas="
				+ coordenadas + "]";
	}

}
