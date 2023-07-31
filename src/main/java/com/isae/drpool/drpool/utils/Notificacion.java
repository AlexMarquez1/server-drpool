package com.isae.drpool.drpool.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isae.drpool.drpool.entity.Fotoevidencia;
import com.isae.drpool.drpool.entity.Usuario;

public class Notificacion {
    private String titulo;
    private String contenido;
    private String data;
    private String image;
    private List<Usuario> token;
    private List<Fotoevidencia> imagenes;
    
	public Notificacion() {
	}

	public Notificacion(String titulo, String contenido, String data, String image, List<Usuario> token,
			List<Fotoevidencia> imagenes) {
		this.titulo = titulo;
		this.contenido = contenido;
		this.data = data;
		this.image = image;
		this.token = token;
		this.imagenes = imagenes;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Usuario> getToken() {
		return token;
	}

	public void setToken(List<Usuario> token) {
		this.token = token;
	}

	public List<Fotoevidencia> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Fotoevidencia> imagenes) {
		this.imagenes = imagenes;
	}

	@Override
	public String toString() {
		return "Notificacion [titulo=" + titulo + ", contenido=" + contenido + ", data=" + data + ", image=" + image
				+ ", token=" + token + ", imagenes=" + imagenes + "]";
	}
}
