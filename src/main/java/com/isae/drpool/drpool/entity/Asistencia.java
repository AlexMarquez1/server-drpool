package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the asistencia database table.
 * 
 */
@Entity
@Table(name="asistencia")
@NamedQuery(name="Asistencia.findAll", query="SELECT a FROM Asistencia a")
public class Asistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idasistencia;

	private String horaentrada;

	private String horasalida;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Fotosasistencia
	@ManyToOne
	@JoinColumn(name="idfoto")
	private Fotosasistencia fotosasistencia;

	public Asistencia() {
	}

	public int getIdasistencia() {
		return this.idasistencia;
	}

	public void setIdasistencia(int idasistencia) {
		this.idasistencia = idasistencia;
	}

	public String getHoraentrada() {
		return this.horaentrada;
	}

	public void setHoraentrada(String horaentrada) {
		this.horaentrada = horaentrada;
	}

	public String getHorasalida() {
		return this.horasalida;
	}

	public void setHorasalida(String horasalida) {
		this.horasalida = horasalida;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Fotosasistencia getFotosasistencia() {
		return this.fotosasistencia;
	}

	public void setFotosasistencia(Fotosasistencia fotosasistencia) {
		this.fotosasistencia = fotosasistencia;
	}

	@Override
	public String toString() {
		return "Asistencia [idasistencia=" + idasistencia + ", horaentrada=" + horaentrada + ", horasalida="
				+ horasalida + ", usuario=" + usuario + ", fotosasistencia=" + fotosasistencia + "]";
	}
	
	

}