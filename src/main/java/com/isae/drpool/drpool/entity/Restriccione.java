package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the restricciones database table.
 * 
 */
@Entity
@Table(name="restricciones")
@NamedQuery(name="Restriccione.findAll", query="SELECT r FROM Restriccione r")
public class Restriccione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrestriccion;

	private String longitud;

	private String restriccion;

	public Restriccione() {
	}

	public int getIdrestriccion() {
		return this.idrestriccion;
	}

	public void setIdrestriccion(int idrestriccion) {
		this.idrestriccion = idrestriccion;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getRestriccion() {
		return this.restriccion;
	}

	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}

}