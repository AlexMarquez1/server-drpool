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
@Table(name="equipocontrolador")
@NamedQuery(name="Equipocontrolador.findAll", query="SELECT e FROM Equipocontrolador e")
public class Equipocontrolador {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcontrolador;
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String tipoequipo;
	
	private String numero; 
	
	private String marcacontrolador;
	
	private String modelocontrolador; 
	
	private String numero_equipos_controladores; 
	
	private String estatus; 
	
	private String observaciones; 
	
	private String fecha_ultimo_mantenimiento;

	public int getIdcontrolador() {
		return idcontrolador;
	}

	public void setIdcontrolador(int idcontrolador) {
		this.idcontrolador = idcontrolador;
	}

	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public String getTipoequipo() {
		return tipoequipo;
	}

	public void setTipoequipo(String tipoequipo) {
		this.tipoequipo = tipoequipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMarcacontrolador() {
		return marcacontrolador;
	}

	public void setMarcacontrolador(String marcacontrolador) {
		this.marcacontrolador = marcacontrolador;
	}

	public String getModelocontrolador() {
		return modelocontrolador;
	}

	public void setModelocontrolador(String modelocontrolador) {
		this.modelocontrolador = modelocontrolador;
	}

	public String getNumero_equipos_controladores() {
		return numero_equipos_controladores;
	}

	public void setNumero_equipos_controladores(String numero_equipos_controladores) {
		this.numero_equipos_controladores = numero_equipos_controladores;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecha_ultimo_mantenimiento() {
		return fecha_ultimo_mantenimiento;
	}

	public void setFecha_ultimo_mantenimiento(String fecha_ultimo_mantenimiento) {
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	public Equipocontrolador() {
		super();
	}

	public Equipocontrolador(int idcontrolador, Alberca alberca, String tipoequipo, String numero,
			String marcacontrolador, String modelocontrolador, String numero_equipos_controladores, String estatus,
			String observaciones, String fecha_ultimo_mantenimiento) {
		super();
		this.idcontrolador = idcontrolador;
		this.alberca = alberca;
		this.tipoequipo = tipoequipo;
		this.numero = numero;
		this.marcacontrolador = marcacontrolador;
		this.modelocontrolador = modelocontrolador;
		this.numero_equipos_controladores = numero_equipos_controladores;
		this.estatus = estatus;
		this.observaciones = observaciones;
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	@Override
	public String toString() {
		return "Equipocontrolador [idcontrolador=" + idcontrolador + ", alberca=" + alberca + ", tipoequipo="
				+ tipoequipo + ", numero=" + numero + ", marcacontrolador=" + marcacontrolador + ", modelocontrolador="
				+ modelocontrolador + ", numero_equipos_controladores=" + numero_equipos_controladores + ", estatus="
				+ estatus + ", observaciones=" + observaciones + ", fecha_ultimo_mantenimiento="
				+ fecha_ultimo_mantenimiento + "]";
	}

}
