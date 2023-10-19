package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="actividadimagenes")
@NamedQuery(name = "Actividadeimagenes.findAll", query = "SELECT a FROM Actividadimagenes a")
public class Actividadimagenes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idactividadimagenes;
	
	@OneToOne
	@JoinColumn(name = "idactividades")
	private Actividades actividades; 
	
	private String url;

	public int getIdactividadimagenes() {
		return idactividadimagenes;
	}

	public void setIdactividadimaganes(int idactividadimaganes) {
		this.idactividadimagenes = idactividadimaganes;
	}

	

	public Actividades getActividades() {
		return actividades;
	}

	public void setActividades(Actividades actividades) {
		this.actividades = actividades;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public Actividadimagenes(int idactividadimagenes, Actividades actividades, String url) {
		super();
		this.idactividadimagenes = idactividadimagenes;
		this.actividades = actividades;
		this.url = url;
	}
	
	

	public Actividadimagenes(Actividades actividades, String url) {
		super();
		this.actividades = actividades;
		this.url = url;
	}

	public Actividadimagenes() {
		super();
	}

	@Override
	public String toString() {
		return "Actividadimagenes [idactividadimagenes=" + idactividadimagenes + ", actividades=" + actividades
				+ ", url=" + url + "]";
	}

	
	
	

}
