package com.isae.drpool.drpool.entity;

import javax.persistence.*;

@Entity
@Table(name="equipocalentamiento")
@NamedQuery(name="Equipocalentamiento.finAll",query="SELECT e FROM Equipocalentamiento e")
public class Equipocalentamiento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcalentamiento; 
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String numero; 
	
	private String marcabomba; 
	
	private String modelobomba; 
	
	private String capacidadbomba;
	
	private String voltajebomba;
	
	private String numerofasesbomba; 
	
	private String marcacaldera;
	
	private String modelocaldera; 
	
	private String capacidadcaldera; 
	
	private String combustiblecaldera; 
	
	private String marcacolector; 
	
	private String modelocolector; 
	
	private String numeropaneles;
	
	private String numerointercambiador; 
	
	private String modelointercambiador; 
	
	private String capacidadintercambiador; 
	
	private String informacionintercambiador;
	
	private String estatus;
	
	private String observaciones; 
	
	private String fecha_ultimo_mantenimiento;

	public int getIdcalentamiento() {
		return idcalentamiento;
	}

	public void setIdcalentamiento(int idcalentamiento) {
		this.idcalentamiento = idcalentamiento;
	}
	
	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMarcabomba() {
		return marcabomba;
	}

	public void setMarcabomba(String marcabomba) {
		this.marcabomba = marcabomba;
	}

	public String getModelobomba() {
		return modelobomba;
	}

	public void setModelobomba(String modelobomba) {
		this.modelobomba = modelobomba;
	}

	public String getCapacidadbomba() {
		return capacidadbomba;
	}

	public void setCapacidadbomba(String capacidadbomba) {
		this.capacidadbomba = capacidadbomba;
	}

	public String getVoltajebomba() {
		return voltajebomba;
	}

	public void setVoltajebomba(String voltajebomba) {
		this.voltajebomba = voltajebomba;
	}

	public String getNumerofasesbomba() {
		return numerofasesbomba;
	}

	public void setNumerofasesbomba(String numerofasesbomba) {
		this.numerofasesbomba = numerofasesbomba;
	}

	public String getMarcacaldera() {
		return marcacaldera;
	}

	public void setMarcacaldera(String marcacaldera) {
		this.marcacaldera = marcacaldera;
	}

	public String getModelocaldera() {
		return modelocaldera;
	}

	public void setModelocaldera(String modelocaldera) {
		this.modelocaldera = modelocaldera;
	}

	public String getCapacidadcaldera() {
		return capacidadcaldera;
	}

	public void setCapacidadcaldera(String capacidadcaldera) {
		this.capacidadcaldera = capacidadcaldera;
	}

	public String getCombustiblecaldera() {
		return combustiblecaldera;
	}

	public void setCombustiblecaldera(String combustiblecaldera) {
		this.combustiblecaldera = combustiblecaldera;
	}

	public String getMarcacolector() {
		return marcacolector;
	}

	public void setMarcacolector(String marcacolector) {
		this.marcacolector = marcacolector;
	}

	public String getModelocolector() {
		return modelocolector;
	}

	public void setModelocolector(String modelocolector) {
		this.modelocolector = modelocolector;
	}

	public String getNumeropaneles() {
		return numeropaneles;
	}

	public void setNumeropaneles(String numeropaneles) {
		this.numeropaneles = numeropaneles;
	}

	public String getNumerointercambiador() {
		return numerointercambiador;
	}

	public void setNumerointercambiador(String numerointercambiador) {
		this.numerointercambiador = numerointercambiador;
	}

	public String getModelointercambiador() {
		return modelointercambiador;
	}

	public void setModelointercambiador(String modelointercambiador) {
		this.modelointercambiador = modelointercambiador;
	}

	public String getCapacidadintercambiador() {
		return capacidadintercambiador;
	}

	public void setCapacidadintercambiador(String capacidadintercambiador) {
		this.capacidadintercambiador = capacidadintercambiador;
	}

	public String getInformacionintercambiador() {
		return informacionintercambiador;
	}

	public void setInformacionintercambiador(String informacionintercambiador) {
		this.informacionintercambiador = informacionintercambiador;
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

	public Equipocalentamiento(int idcalentamiento, Alberca alberca, String numero, String marcabomba,
			String modelobomba, String capacidadbomba, String voltajebomba, String numerofasesbomba,
			String marcacaldera, String modelocaldera, String capacidadcaldera, String combustiblecaldera,
			String marcacolector, String modelocolector, String numeropaneles, String numerointercambiador,
			String modelointercambiador, String capacidadintercambiador, String informacionintercambiador,
			String estatus, String observaciones, String fecha_ultimo_mantenimiento) {
		super();
		this.idcalentamiento = idcalentamiento;
		this.alberca = alberca;
		this.numero = numero;
		this.marcabomba = marcabomba;
		this.modelobomba = modelobomba;
		this.capacidadbomba = capacidadbomba;
		this.voltajebomba = voltajebomba;
		this.numerofasesbomba = numerofasesbomba;
		this.marcacaldera = marcacaldera;
		this.modelocaldera = modelocaldera;
		this.capacidadcaldera = capacidadcaldera;
		this.combustiblecaldera = combustiblecaldera;
		this.marcacolector = marcacolector;
		this.modelocolector = modelocolector;
		this.numeropaneles = numeropaneles;
		this.numerointercambiador = numerointercambiador;
		this.modelointercambiador = modelointercambiador;
		this.capacidadintercambiador = capacidadintercambiador;
		this.informacionintercambiador = informacionintercambiador;
		this.estatus = estatus;
		this.observaciones = observaciones;
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	public Equipocalentamiento() {}

	@Override
	public String toString() {
		return "Equipocalentamiento [idcalentamiento=" + idcalentamiento + ", alberca=" + alberca + ", numero=" + numero
				+ ", marcabomba=" + marcabomba + ", modelobomba=" + modelobomba + ", capacidadbomba=" + capacidadbomba
				+ ", voltajebomba=" + voltajebomba + ", numerofasesbomba=" + numerofasesbomba + ", marcacaldera="
				+ marcacaldera + ", modelocaldera=" + modelocaldera + ", capacidadcaldera=" + capacidadcaldera
				+ ", combustiblecaldera=" + combustiblecaldera + ", marcacolector=" + marcacolector
				+ ", modelocolector=" + modelocolector + ", numeropaneles=" + numeropaneles + ", numerointercambiador="
				+ numerointercambiador + ", modelointercambiador=" + modelointercambiador + ", capacidadintercambiador="
				+ capacidadintercambiador + ", informacionintercambiador=" + informacionintercambiador + ", estatus="
				+ estatus + ", observaciones=" + observaciones + ", fecha_ultimo_mantenimiento="
				+ fecha_ultimo_mantenimiento + "]";
	}



	
	
	
}
