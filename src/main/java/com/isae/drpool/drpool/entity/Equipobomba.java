package com.isae.drpool.drpool.entity;

import javax.persistence.*;

@Entity
@Table(name="equipobomba")
@NamedQuery(name="Equipobomba.findAll", query="SELECT e FROM Equipobomba e")
public class Equipobomba {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbomba;
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String tipoequipo;
	
	private String numero; 
	
	private String potencia;
	
	private String marca;
	
	private String modelo; 
	
	private String capacidad; 
	
	private String voltaje; 
	
	private String estatus;
	
	private String observaciones; 
	
	private String fecha_ultimo_mantenimiento; 
	
	private String numerofases;

	public int getIdbomba() {
		return idbomba;
	}

	public void setIdbomba(int idbomba) {
		this.idbomba = idbomba;
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

	public String getPotencia() {
		return potencia;
	}

	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getVoltaje() {
		return voltaje;
	}

	public void setVoltaje(String voltaje) {
		this.voltaje = voltaje;
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

	public String getNumerofases() {
		return numerofases;
	}

	public void setNumerofases(String numerofases) {
		this.numerofases = numerofases;
	}

	public Equipobomba() {
		super();
	}

	public Equipobomba(int idbomba, Alberca alberca, String tipoequipo, String numero, String potencia, String marca,
			String modelo, String capacidad, String voltaje, String estatus, String observaciones,
			String fecha_ultimo_mantenimiento, String numerofases) {
		super();
		this.idbomba = idbomba;
		this.alberca = alberca;
		this.tipoequipo = tipoequipo;
		this.numero = numero;
		this.potencia = potencia;
		this.marca = marca;
		this.modelo = modelo;
		this.capacidad = capacidad;
		this.voltaje = voltaje;
		this.estatus = estatus;
		this.observaciones = observaciones;
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
		this.numerofases = numerofases;
	}

	@Override
	public String toString() {
		return "Equipobomba [idbomba=" + idbomba + ", alberca=" + alberca + ", tipoequipo=" + tipoequipo + ", numero="
				+ numero + ", potencia=" + potencia + ", marca=" + marca + ", modelo=" + modelo + ", capacidad="
				+ capacidad + ", voltaje=" + voltaje + ", estatus=" + estatus + ", observaciones=" + observaciones
				+ ", fecha_ultimo_mantenimiento=" + fecha_ultimo_mantenimiento + ", numerofases=" + numerofases + "]";
	}
		
}
