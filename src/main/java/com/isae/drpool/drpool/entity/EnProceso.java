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
@Table(name="enproceso")
@NamedQuery(name="EnProceso.findAll", query="SELECT u FROM EnProceso u")
public class EnProceso {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idenproceso;
	
	@OneToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;
	
	private String agrupacion;

	public EnProceso(int idenproceso, Inventario inventario, String agrupacion) {
		this.idenproceso = idenproceso;
		this.inventario = inventario;
		this.agrupacion = agrupacion;
	}

	public EnProceso() {
	}

	public int getIdenproceso() {
		return idenproceso;
	}

	public void setIdenproceso(int idenproceso) {
		this.idenproceso = idenproceso;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public String getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	@Override
	public String toString() {
		return "EnProceso [idenproceso=" + idenproceso + ", inventario=" + inventario + ", agrupacion=" + agrupacion
				+ "]";
	}

}
