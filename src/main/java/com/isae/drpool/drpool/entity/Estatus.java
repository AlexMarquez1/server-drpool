package com.isae.drpool.drpool.entity;

public class Estatus {
	
	private String estatus;
	
	private String agrupacion;
	
	private Inventario inventario;
	
	private String motivo;
	
	private String descripcion;

	public Estatus(String estatus, String agrupacion, Inventario inventario, String motivo, String descripcion) {
		this.estatus = estatus;
		this.agrupacion = agrupacion;
		this.inventario = inventario;
		this.motivo = motivo;
		this.descripcion = descripcion;
	}

	public Estatus() {
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Estatus [estatus=" + estatus + ", agrupacion=" + agrupacion + ", inventario=" + inventario + ", motivo="
				+ motivo + ", descripcion=" + descripcion + "]";
	}

}
