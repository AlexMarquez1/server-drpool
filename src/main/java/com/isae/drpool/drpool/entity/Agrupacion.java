package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the agrupacion database table.
 * 
 */
@Entity
@Table(name="agrupacion")
@NamedQuery(name="Agrupacion.findAll", query="SELECT a FROM Agrupacion a")
public class Agrupacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idagrupacion;

	private String agrupacion;

	public Agrupacion() {
	}
	
	public Agrupacion(int idagrupacion) {
		super();
		this.idagrupacion = idagrupacion;
	}

	public Agrupacion(int idagrupacion, String agrupacion) {
		super();
		this.idagrupacion = idagrupacion;
		this.agrupacion = agrupacion;
	}

	public int getIdagrupacion() {
		return this.idagrupacion;
	}

	public void setIdagrupacion(int idagrupacion) {
		this.idagrupacion = idagrupacion;
	}

	public String getAgrupacion() {
		return this.agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}

	@Override
	public String toString() {
		return "Agrupacion [idagrupacion=" + idagrupacion + ", agrupacion=" + agrupacion + "]";
	}

	

}