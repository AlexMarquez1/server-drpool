package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name="direccionsede")
@NamedQuery (name="DireccionSede.findAll", query="SELECT d FROM DireccionSede d")
public class DireccionSede {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iddireccion;
	private String calle;
	private String callenumero;
	private String estado;
	private String alcaldia;
	private String colonia;
	private String codigopostal;
	
	public DireccionSede() {
		super();
	}

	public DireccionSede(int iddireccion, String calle, String callenumero, String estado, String alcaldia,
			String colonia, String codigopostal) {
		super();
		this.iddireccion = iddireccion;
		this.calle = calle;
		this.callenumero = callenumero;
		this.estado = estado;
		this.alcaldia = alcaldia;
		this.colonia = colonia;
		this.codigopostal = codigopostal;
	}

	public int getIddireccion() {
		return iddireccion;
	}

	public void setIddireccion(int iddireccion) {
		this.iddireccion = iddireccion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCallenumero() {
		return callenumero;
	}

	public void setCallenumero(String callenumero) {
		this.callenumero = callenumero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}

	@Override
	public String toString() {
		return "DireccionSede [iddireccion=" + iddireccion + ", calle=" + calle + ", callenumero=" + callenumero
				+ ", estado=" + estado + ", alcaldia=" + alcaldia + ", colonia=" + colonia + ", codigopostal="
				+ codigopostal + "]";
	}

}
