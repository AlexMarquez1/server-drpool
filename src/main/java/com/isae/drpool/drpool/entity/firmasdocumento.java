package com.isae.drpool.drpool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="firmasdocumento")
@NamedQuery(name="firmasdocumento.findAll", query="SELECT u FROM firmasdocumento u")
public class firmasdocumento implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfirma ;
	
	@Column(name = "nombrefirma")
	private String nombrefirma;
	
	@Column(name = "url")
	private String url;
	
	@OneToOne
	@JoinColumn(name="idcampo")
	private Camposproyecto camposProyecto;
	
	@OneToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;

	public firmasdocumento(int idfirma, String nombrefirma, String url, Camposproyecto camposProyecto,
			Inventario inventario) {
		this.idfirma = idfirma;
		this.nombrefirma = nombrefirma;
		this.url = url;
		this.camposProyecto = camposProyecto;
		this.inventario = inventario;
	}

	public firmasdocumento() {
	}

	public int getIdfirma() {
		return idfirma;
	}

	public void setIdfirma(int idfirma) {
		this.idfirma = idfirma;
	}

	public String getNombrefirma() {
		return nombrefirma;
	}

	public void setNombrefirma(String nombrefirma) {
		this.nombrefirma = nombrefirma;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		return "firmasdocumento [idfirma=" + idfirma + ", nombrefirma=" + nombrefirma + ", url=" + url
				+ ", camposProyecto=" + camposProyecto + ", inventario=" + inventario + "]";
	}
	
	
	
	
}
