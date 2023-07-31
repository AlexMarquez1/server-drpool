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
@Table(name="pendiente")
@NamedQuery(name="Pendiente.findAll", query="SELECT u FROM Pendiente u")
public class Pendiente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpendiente;
	
	@OneToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;
	
	private String motivo;
	
	private String descripcion;
	
	private String agrupacion;

	public Pendiente(int idpendiente, Inventario inventario, String motivo, String descripcion, String agrupacion) {
		this.idpendiente = idpendiente;
		this.inventario = inventario;
		this.motivo = motivo;
		this.descripcion = descripcion;
		this.agrupacion = agrupacion;
	}

	public Pendiente() {
	}

	public int getIdpendiente() {
		return idpendiente;
	}

	public void setIdpendiente(int idpendiente) {
		this.idpendiente = idpendiente;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	@Override
	public String toString() {
		return "Pendiente [idpendiente=" + idpendiente + ", inventario=" + inventario + ", motivo=" + motivo
				+ ", descripcion=" + descripcion + ", agrupacion=" + agrupacion + "]";
	}
	

}
