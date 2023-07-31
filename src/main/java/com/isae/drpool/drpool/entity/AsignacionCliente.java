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
@Table(name="asignacioncliente")
@NamedQuery(name="AsignacionCliente.findAll", query="SELECT a FROM AsignacionCliente a")
public class AsignacionCliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idasignacion;
	
	@OneToOne
	@JoinColumn(name="idcliente")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;

	public AsignacionCliente() {
		super();
	}

	public AsignacionCliente(int idasignacion, Cliente cliente, Proyecto proyecto) {
		super();
		this.idasignacion = idasignacion;
		this.cliente = cliente;
		this.proyecto = proyecto;
	}

	public int getIdasignacion() {
		return idasignacion;
	}

	public void setIdasignacion(int idasignacion) {
		this.idasignacion = idasignacion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "AsignacionCliente [idasignacion=" + idasignacion + ", cliente=" + cliente + ", proyecto=" + proyecto
				+ "]";
	}
	
}
