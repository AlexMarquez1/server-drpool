package com.isae.drpool.drpool.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="notificaciones")
@NamedQuery(name="Notificaciones.findAll", query ="SELECT i FROM Notificaciones i")
public class Notificaciones implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idnotificacion;
	
	private String titulo;
	
	private String contenido;
	
	private String prioridad;
	
	private String remitente;
	
	private String fecha;
	
	private String hora;
	
	private String atendido;
	
	@OneToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	public Notificaciones() {
	}

	public Notificaciones(int idnotificacion, String titulo, String contenido, String prioridad, String remitente,
			String fecha, String hora, Usuario usuario, String atendido) {
		this.idnotificacion = idnotificacion;
		this.titulo = titulo;
		this.contenido = contenido;
		this.prioridad = prioridad;
		this.remitente = remitente;
		this.fecha = fecha;
		this.hora = hora;
		this.atendido = atendido;
		this.usuario = usuario;
	}

	public int getIdnotificacion() {
		return idnotificacion;
	}

	public void setIdnotificacion(int idnotificacion) {
		this.idnotificacion = idnotificacion;
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

	public String getAtendido() {
		return atendido;
	}

	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Notificaciones [idnotificacion=" + idnotificacion + ", titulo=" + titulo + ", contenido=" + contenido
				+ ", prioridad=" + prioridad + ", remitente=" + remitente + ", fecha=" + fecha + ", hora=" + hora
				+ ", atendido=" + atendido + ", usuario=" + usuario + "]";
	}

}
