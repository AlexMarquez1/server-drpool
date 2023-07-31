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
@Table(name="edicionasignada")
@NamedQuery(name="EdicionAsignada.findAll", query="SELECT u FROM EdicionAsignada u")
public class EdicionAsignada {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idedicion;
	
	@OneToOne
	@JoinColumn(name="idcamposproyecto")
	private Camposproyecto camposProyecto;
	
	@OneToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;

	public EdicionAsignada() {
	}

	public EdicionAsignada(int idedicion, Camposproyecto camposProyecto, Usuario usuario, Inventario inventario) {
		this.idedicion = idedicion;
		this.camposProyecto = camposProyecto;
		this.usuario = usuario;
		this.inventario = inventario;
	}

	public int getIdedicion() {
		return idedicion;
	}

	public void setIdedicion(int idedicion) {
		this.idedicion = idedicion;
	}

	public Camposproyecto getCamposProyecto() {
		return camposProyecto;
	}

	public void setCamposProyecto(Camposproyecto camposProyecto) {
		this.camposProyecto = camposProyecto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "EdicionAsignada [idedicion=" + idedicion + ", camposProyecto=" + camposProyecto + ", usuario=" + usuario
				+ ", inventario=" + inventario + "]";
	}


}
