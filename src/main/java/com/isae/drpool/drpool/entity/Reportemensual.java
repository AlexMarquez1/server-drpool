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
@Table(name="reportenmensual")
@NamedQuery(name="Reportemensual.finAll", query="SELECT r FROM Reportemensual r")
public class Reportemensual {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idreportemensual;
	
	@OneToOne
	@JoinColumn(name="idsede")
	private Sede sede;
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca; 
	
	private String fecha;
	
	private String periodoinicial;
	
	private String periodofinal; 
	
	private String alcaldia;
	
	private String tipoalberca; 
	
	private String caracteristicaalberca;
	
	private String realizo; 
	
	private String reviso;
	
	private String url;

	public int getIdreportemensual() {
		return idreportemensual;
	}

	public void setIdreportemensual(int idreportemensual) {
		this.idreportemensual = idreportemensual;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPeriodoinicial() {
		return periodoinicial;
	}

	public void setPeriodoinicial(String periodoinicial) {
		this.periodoinicial = periodoinicial;
	}

	public String getPeriodofinal() {
		return periodofinal;
	}

	public void setPeriodofinal(String periodofinal) {
		this.periodofinal = periodofinal;
	}

	public String getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	public String getTipoalberca() {
		return tipoalberca;
	}

	public void setTipoalberca(String tipoalberca) {
		this.tipoalberca = tipoalberca;
	}

	public String getCaracteristicaalberca() {
		return caracteristicaalberca;
	}

	public void setCaracteristicaalberca(String caracteristicaalberca) {
		this.caracteristicaalberca = caracteristicaalberca;
	}

	public String getRealizo() {
		return realizo;
	}

	public void setRealizo(String realizo) {
		this.realizo = realizo;
	}

	public String getReviso() {
		return reviso;
	}

	public void setReviso(String reviso) {
		this.reviso = reviso;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Reportemensual(int idreportemensual, Sede sede, Alberca alberca, String fecha, String periodoinicial,
			String periodofinal, String alcaldia, String tipoalberca, String caracteristicaalberca, String realizo,
			String reviso, String url) {
		super();
		this.idreportemensual = idreportemensual;
		this.sede = sede;
		this.alberca = alberca;
		this.fecha = fecha;
		this.periodoinicial = periodoinicial;
		this.periodofinal = periodofinal;
		this.alcaldia = alcaldia;
		this.tipoalberca = tipoalberca;
		this.caracteristicaalberca = caracteristicaalberca;
		this.realizo = realizo;
		this.reviso = reviso;
		this.url = url;
	}
	
	

	public Reportemensual(Sede sede, Alberca alberca, String fecha, String periodoinicial, String periodofinal,
			String alcaldia, String tipoalberca, String caracteristicaalberca, String realizo, String reviso,
			String url) {
		super();
		this.sede = sede;
		this.alberca = alberca;
		this.fecha = fecha;
		this.periodoinicial = periodoinicial;
		this.periodofinal = periodofinal;
		this.alcaldia = alcaldia;
		this.tipoalberca = tipoalberca;
		this.caracteristicaalberca = caracteristicaalberca;
		this.realizo = realizo;
		this.reviso = reviso;
		this.url = url;
	}

	public Reportemensual() {
		super();
	}

	@Override
	public String toString() {
		return "Reportemensual [idreportemensual=" + idreportemensual + ", sede=" + sede + ", alberca=" + alberca
				+ ", fecha=" + fecha + ", periodoinicial=" + periodoinicial + ", periodofinal=" + periodofinal
				+ ", alcaldia=" + alcaldia + ", tipoalberca=" + tipoalberca + ", caracteristicaalberca="
				+ caracteristicaalberca + ", realizo=" + realizo + ", reviso=" + reviso + ", url=" + url + "]";
	}
	
	
	
}
