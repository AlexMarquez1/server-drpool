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
@Table(name="estatusproyecto")
@NamedQuery(name="EstatusProyecto.findAll", query="SELECT e FROM EstatusProyecto e")
public class EstatusProyecto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idestatus;
	
	private String estatus;
	private String validarinformacion;
	private String orden;
	
	@OneToOne
	@JoinColumn(name="idagrupacion")
	private Agrupacion agrupacion;
	
	@OneToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;
	
	private String rgbcolor;

	public EstatusProyecto() {
		super();
	}

	public EstatusProyecto(int idestatus, String estatus, String validarinformacion, String orden,
			Agrupacion agrupacion, Proyecto proyecto, String rgbcolor) {
		super();
		this.idestatus = idestatus;
		this.estatus = estatus;
		this.validarinformacion = validarinformacion;
		this.orden = orden;
		this.agrupacion = agrupacion;
		this.proyecto = proyecto;
		this.rgbcolor = rgbcolor;
	}

	public int getIdestatus() {
		return idestatus;
	}

	public void setIdestatus(int idestatus) {
		this.idestatus = idestatus;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getValidarinformacion() {
		return validarinformacion;
	}

	public void setValidarinformacion(String validarinformacion) {
		this.validarinformacion = validarinformacion;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Agrupacion getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(Agrupacion agrupacion) {
		this.agrupacion = agrupacion;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getRgbcolor() {
		return rgbcolor;
	}

	public void setRgbcolor(String rgbcolor) {
		this.rgbcolor = rgbcolor;
	}

	@Override
	public String toString() {
		return "EstatusProyecto [idestatus=" + idestatus + ", estatus=" + estatus + ", validarinformacion="
				+ validarinformacion + ", orden=" + orden + ", agrupacion=" + agrupacion + ", proyecto=" + proyecto
				+ ", rgbcolor=" + rgbcolor + "]";
	}
	
	
}
