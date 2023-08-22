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
@Table(name="proyectoalberca")
@NamedQuery(name="ProyectoAlberca.findAll", query="SELECT p FROM ProyectoAlberca p")
public class ProyectoAlberca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproyectoalberca;
	
	private String nombreproyectoalberca;
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	private String tiposervicio;
	private String instrumentosmedicion;
	private String estatus;
	
	public ProyectoAlberca() {
		super();
	}

	public ProyectoAlberca(int idproyectoalberca, String nombreproyectoalberca, Alberca alberca, String tiposervicio,
			String instrumentosmedicion, String estatus) {
		super();
		this.idproyectoalberca = idproyectoalberca;
		this.nombreproyectoalberca = nombreproyectoalberca;
		this.alberca = alberca;
		this.tiposervicio = tiposervicio;
		this.instrumentosmedicion = instrumentosmedicion;
		this.estatus = estatus;
	}

	public int getIdproyectoalberca() {
		return idproyectoalberca;
	}

	public void setIdproyectoalberca(int idproyectoalberca) {
		this.idproyectoalberca = idproyectoalberca;
	}

	public String getNombreproyectoalberca() {
		return nombreproyectoalberca;
	}

	public void setNombreproyectoalberca(String nombreproyectoalberca) {
		this.nombreproyectoalberca = nombreproyectoalberca;
	}

	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public String getTiposervicio() {
		return tiposervicio;
	}

	public void setTiposervicio(String tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public String getInstrumentosmedicion() {
		return instrumentosmedicion;
	}

	public void setInstrumentosmedicion(String instrumentosmedicion) {
		this.instrumentosmedicion = instrumentosmedicion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	@Override
	public String toString() {
		return "ProyectoAlberca [idproyectoalberca=" + idproyectoalberca + ", nombreproyectoalberca="
				+ nombreproyectoalberca + ", alberca=" + alberca + ", tiposervicio=" + tiposervicio
				+ ", instrumentosmedicion=" + instrumentosmedicion + ", estatus=" + estatus + "]";
	}

		
}