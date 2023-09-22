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
@Table (name="alberca")
@NamedQuery (name="Alberca.findAll", query="SELECT a FROM Alberca a")

public class Alberca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idalberca;
	
	private String nombrealberca;
	private String tipoalberca;
	private String capacidad;
	//Recien agregado
	
	private String caracteristica; 
	private String forma; 
	
	private String medidalargo;
	private String medidaancho;
	//Cambio de medidaprofundidad por profundidad minima
	private String profundidadminima;
	//Recien agregado
	private String profundidadmaxima;
	private String observaciones;
	
	
	//private String ubicacion; se elimina ubicacion de la bd
	
	@OneToOne
	@JoinColumn(name="idsede")
	private Sede sede;
	private String estatus;
	private String equiponombre;
	private String tipoequipo;
	
	
	public Alberca() {
		super();
	}

	public Alberca(int idalberca, String nombrealberca, String tipoalberca, String capacidad, String caracteristica,
			String forma, String medidalargo, String medidaancho, String profundidadminima, String profundidadmaxima,
			String observaciones, Sede sede, String estatus, String equiponombre, String tipoequipo) {
		super();
		this.idalberca = idalberca;
		this.nombrealberca = nombrealberca;
		this.tipoalberca = tipoalberca;
		this.capacidad = capacidad;
		this.caracteristica = caracteristica;
		this.forma = forma;
		this.medidalargo = medidalargo;
		this.medidaancho = medidaancho;
		this.profundidadminima = profundidadminima;
		this.profundidadmaxima = profundidadmaxima;
		this.observaciones = observaciones;
		this.sede = sede;
		this.estatus = estatus;
		this.equiponombre = equiponombre;
		this.tipoequipo = tipoequipo;
	}


	public int getIdalberca() {
		return idalberca;
	}


	public void setIdalberca(int idalberca) {
		this.idalberca = idalberca;
	}


	public String getNombrealberca() {
		return nombrealberca;
	}


	public void setNombrealberca(String nombrealberca) {
		this.nombrealberca = nombrealberca;
	}


	public String getTipoalberca() {
		return tipoalberca;
	}


	public void setTipoalberca(String tipoalberca) {
		this.tipoalberca = tipoalberca;
	}


	public String getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}


	public String getMedidalargo() {
		return medidalargo;
	}


	public void setMedidalargo(String medidalargo) {
		this.medidalargo = medidalargo;
	}


	public String getMedidaancho() {
		return medidaancho;
	}


	public void setMedidaancho(String medidaancho) {
		this.medidaancho = medidaancho;
	}


	public String getCaracteristica() {
		return caracteristica;
	}


	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}


	public String getForma() {
		return forma;
	}


	public void setForma(String forma) {
		this.forma = forma;
	}


	public String getProfundidadminima() {
		return profundidadminima;
	}


	public void setProfundidadminima(String profundidadminima) {
		this.profundidadminima = profundidadminima;
	}


	public String getProfundidadmaxima() {
		return profundidadmaxima;
	}


	public void setProfundidadmaxima(String profundidadmaxima) {
		this.profundidadmaxima = profundidadmaxima;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Sede getSede() {
		return sede;
	}


	public void setSede(Sede sede) {
		this.sede = sede;
	}


	public String getEstatus() {
		return estatus;
	}


	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	public String getEquiponombre() {
		return equiponombre;
	}


	public void setEquiponombre(String equiponombre) {
		this.equiponombre = equiponombre;
	}


	public String getTipoequipo() {
		return tipoequipo;
	}

	
	public void setTipoequipo(String tipoequipo) {
		this.tipoequipo = tipoequipo;
	}

	@Override
	public String toString() {
		return "Alberca [idalberca=" + idalberca + ", nombrealberca=" + nombrealberca + ", tipoalberca=" + tipoalberca
				+ ", capacidad=" + capacidad + ", caracteristica=" + caracteristica + ", forma=" + forma
				+ ", medidalargo=" + medidalargo + ", medidaancho=" + medidaancho + ", profundidadminima="
				+ profundidadminima + ", profundidadmaxima=" + profundidadmaxima + ", observaciones=" + observaciones
				+ ", sede=" + sede + ", estatus=" + estatus + ", equiponombre=" + equiponombre + ", tipoequipo="
				+ tipoequipo + "]";
	}
	
	
}
