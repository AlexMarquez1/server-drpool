package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="tipoproyecto")
@NamedQuery(name="Tipoproyecto.findAll", query="SELECT t FROM Tipoproyecto t")
public class Tipoproyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipo;

	private String descripcion;

	public Tipoproyecto() {
	}

	public Tipoproyecto(int idtipo, String descripcion) {
		this.idtipo = idtipo;
		this.descripcion = descripcion;
	}

	public int getIdtipo() {
		return this.idtipo;
	}

	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Tipoproyecto [idtipo=" + idtipo + ", descripcion=" + descripcion + "]";
	}

}