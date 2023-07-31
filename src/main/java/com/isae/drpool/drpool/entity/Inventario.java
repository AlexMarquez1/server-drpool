package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the inventario database table.
 * 
 */
@Entity
@Table(name="inventario")
@NamedQuery(name="Inventario.findAll", query="SELECT i FROM Inventario i")
public class Inventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idinventario;

	@Temporal(TemporalType.DATE)
//	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date fechacreacion;

	private String folio;
	
	private String estatus;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;

	public Inventario() {
	}

	public Inventario(int idinventario) {
		this.idinventario = idinventario;
	}

	public Inventario(int idinventario, Date fechacreacion, String folio, String estatus, Proyecto proyecto) {
		this.idinventario = idinventario;
		this.fechacreacion = fechacreacion;
		this.folio = folio;
		this.estatus = estatus;
		this.proyecto = proyecto;
	}
	

	public int getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(int idinventario) {
		this.idinventario = idinventario;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "Inventario [idinventario=" + idinventario + ", fechacreacion=" + fechacreacion + ", folio=" + folio
				+ ", estatus=" + estatus + ", proyecto=" + proyecto + "]";
	}
	
	
	
	

}