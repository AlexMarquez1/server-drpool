package com.isae.drpool.drpool.entity;

import java.util.List;

public class Firma {
	
	private int idFirma;
	private String nombreFirma;
	private List<Integer> firma;
	private Camposproyecto camposProyecto;
	private Inventario inventario;
	
	public Firma(int idFirma, String nombreFirma, List<Integer> firma, Camposproyecto camposProyecto,
			Inventario inventario) {
		this.idFirma = idFirma;
		this.nombreFirma = nombreFirma;
		this.firma = firma;
		this.camposProyecto = camposProyecto;
		this.inventario = inventario;
	}

	public Firma() {
	}

	public int getIdFirma() {
		return idFirma;
	}

	public void setIdFirma(int idFirma) {
		this.idFirma = idFirma;
	}

	public String getNombreFirma() {
		return nombreFirma;
	}

	public void setNombreFirma(String nombreFirma) {
		this.nombreFirma = nombreFirma;
	}

	public List<Integer> getFirma() {
		return firma;
	}

	public void setFirma(List<Integer> firma) {
		this.firma = firma;
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

	@Override
	public String toString() {
		return "Firma [idFirma=" + idFirma + ", nombreFirma=" + nombreFirma + ", firma=" + firma + ", camposProyecto="
				+ camposProyecto + ", inventario=" + inventario + "]";
	}

}
