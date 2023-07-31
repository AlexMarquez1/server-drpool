package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipocatalogo database table.
 * 
 */
@Entity
@Table(name="tipocatalogo")
@NamedQuery(name="Tipocatalogo.findAll", query="SELECT t FROM Tipocatalogo t")
public class Tipocatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipo;

	private String tipo;

	public Tipocatalogo() {
	}
	
	public Tipocatalogo(int idtipo, String tipo) {
		super();
		this.idtipo = idtipo;
		this.tipo = tipo;
	}

	public int getIdtipo() {
		return this.idtipo;
	}

	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}