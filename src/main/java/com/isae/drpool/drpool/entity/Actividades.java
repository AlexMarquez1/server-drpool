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
@Table(name = "activdiades")
@NamedQuery(name="Actividades.finAll", query= "SELECT a FROM Actividades a")
public class Actividades {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idactividades; 
	
	
	@OneToOne
	@JoinColumn(name="idreportemensual")
	private Reportemensual reportemensual; 
	
	private String tipoactividad; 
	
	private String textoimaganes; 
	
	private String observaciones;

	public int getIdactividades() {
		return idactividades;
	}

	public void setIdactividades(int idactividades) {
		this.idactividades = idactividades;
	}

	public Reportemensual getReportemensual() {
		return reportemensual;
	}

	public void setReportemensual(Reportemensual reportemensual) {
		this.reportemensual = reportemensual;
	}

	public String getTipoactividad() {
		return tipoactividad;
	}

	public void setTipoactividad(String tipoactividad) {
		this.tipoactividad = tipoactividad;
	}

	public String getTextoimaganes() {
		return textoimaganes;
	}

	public void setTextoimaganes(String textoimaganes) {
		this.textoimaganes = textoimaganes;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Actividades(int idactividades, Reportemensual reportemensual, String tipoactividad, String textoimaganes,
			String observaciones) {
		super();
		this.idactividades = idactividades;
		this.reportemensual = reportemensual;
		this.tipoactividad = tipoactividad;
		this.textoimaganes = textoimaganes;
		this.observaciones = observaciones;
	}

	public Actividades(Reportemensual reportemensual, String tipoactividad, String textoimaganes,
			String observaciones) {
		super();
		this.reportemensual = reportemensual;
		this.tipoactividad = tipoactividad;
		this.textoimaganes = textoimaganes;
		this.observaciones = observaciones;
	}

	public Actividades() {
		super();
	}

	@Override
	public String toString() {
		return "Actividades [idactividades=" + idactividades + ", reportemensual=" + reportemensual + ", tipoactividad="
				+ tipoactividad + ", textoimaganes=" + textoimaganes + ", observaciones=" + observaciones + "]";
	} 
	
	
	

}
