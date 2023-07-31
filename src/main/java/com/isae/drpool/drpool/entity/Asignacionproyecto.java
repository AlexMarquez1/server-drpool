package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacionproyecto database table.
 * 
 */
@Entity
@Table(name="asignacionproyecto")
@NamedQuery(name="Asignacionproyecto.findAll", query="SELECT a FROM Asignacionproyecto a")
public class Asignacionproyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idasignacion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;

	public Asignacionproyecto() {
	}

	public Asignacionproyecto(int idasignacion, Usuario usuario, Proyecto proyecto) {
		this.idasignacion = idasignacion;
		this.usuario = usuario;
		this.proyecto = proyecto;
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

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "Asignacionproyecto [idasignacion=" + idasignacion + ", usuario=" + usuario + ", proyecto=" + proyecto
				+ "]";
	}
	
	

}