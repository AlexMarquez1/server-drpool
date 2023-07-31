package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalogorelacionado database table.
 * 
 */
@Entity
@Table(name="catalogorelacionado")
@NamedQuery(name="Catalogorelacionado.findAll", query="SELECT c FROM Catalogorelacionado c")
public class Catalogorelacionado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Catalogo
	@ManyToOne
	@JoinColumn(name="catalogopadre")
	private Catalogo catalogo1;

	//bi-directional many-to-one association to Catalogo
	@ManyToOne
	@JoinColumn(name="catalogohijo")
	private Catalogo catalogo2;

	public Catalogorelacionado() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Catalogo getCatalogo1() {
		return this.catalogo1;
	}

	public void setCatalogo1(Catalogo catalogo1) {
		this.catalogo1 = catalogo1;
	}

	public Catalogo getCatalogo2() {
		return this.catalogo2;
	}

	public void setCatalogo2(Catalogo catalogo2) {
		this.catalogo2 = catalogo2;
	}

}