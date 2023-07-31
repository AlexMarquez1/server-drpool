package com.isae.drpool.drpool.entity;

import java.util.Map;

public class NotificacionesMovil {
	
	private int id;
	private String titulo;
	private String contenido;
	private String prioridad;
	private String remitente;
	private String fecha;
	private String hora;
	private Map<String,String> imagenes;
	private String atendido;
	
	public NotificacionesMovil() {
	}

	public NotificacionesMovil(int id, String titulo, String contenido, String prioridad, String remitente,
			String fecha, String hora, Map<String, String> imagenes, String atendido) {
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.prioridad = prioridad;
		this.remitente = remitente;
		this.fecha = fecha;
		this.hora = hora;
		this.imagenes = imagenes;
		this.atendido = atendido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Map<String, String> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Map<String, String> imagenes) {
		this.imagenes = imagenes;
	}

	public String getAtendido() {
		return atendido;
	}

	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}

	@Override
	public String toString() {
		return "NotificacionesMovil [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", prioridad="
				+ prioridad + ", remitente=" + remitente + ", fecha=" + fecha + ", hora=" + hora + ", imagenes="
				+ imagenes + ", atendido=" + atendido + "]";
	}

}
