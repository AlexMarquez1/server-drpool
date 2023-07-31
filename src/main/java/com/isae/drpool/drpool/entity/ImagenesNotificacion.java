package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="imagenesnotificacion")
@NamedQuery(name="ImagenesNotificacion.findAll", query="SELECT i FROM ImagenesNotificacion i")
public class ImagenesNotificacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idimagen;
	
	private String nombre;
	
	private String url;
	
	@ManyToOne
	@JoinColumn(name="idnotificacion")
	private Notificaciones notificacion;

	public ImagenesNotificacion() {
	}

	public ImagenesNotificacion(int idimagen, String nombre, String url, Notificaciones notificacion) {
		this.idimagen = idimagen;
		this.nombre = nombre;
		this.url = url;
		this.notificacion = notificacion;
	}

	public int getIdimagen() {
		return idimagen;
	}

	public void setIdimagen(int idimagen) {
		this.idimagen = idimagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Notificaciones getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificaciones notificacion) {
		this.notificacion = notificacion;
	}

	@Override
	public String toString() {
		return "ImagenesNotificacion [idimagen=" + idimagen + ", nombre=" + nombre + ", url=" + url + ", notificacion="
				+ notificacion + "]";
	}

}
