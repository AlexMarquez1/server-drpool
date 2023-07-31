package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacionregistro database table.
 * 
 */
@Entity
@Table(name="asignacionregistro")
@NamedQuery(name="Asignacionregistro.findAll", query="SELECT a FROM Asignacionregistro a")
public class Asignacionregistro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idasignacion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Inventario
	@ManyToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;

	public Asignacionregistro() {
	}

	public Asignacionregistro(int idasignacion, Usuario usuario, Inventario inventario) {
		this.idasignacion = idasignacion;
		this.usuario = usuario;
		this.inventario = inventario;
	}
	

	public int getIdasignacion() {
		return this.idasignacion;
	}

	public void setIdasignacion(int idasignacion) {
		this.idasignacion = idasignacion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Inventario getInventario() {
		return this.inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

}