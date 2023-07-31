package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the valores database table.
 * 
 */
@Entity
@Table(name="valores")
@NamedQuery(name="Valore.findAll", query="SELECT v FROM Valore v")
public class Valore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String idvalor;

	private String valor;

	//bi-directional many-to-one association to Camposproyecto
	@ManyToOne
	@JoinColumn(name="idcampoproyecto")
	private Camposproyecto camposproyecto;

	//bi-directional many-to-one association to Inventario
	@ManyToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;
	

	public Valore() {
	}

	public Valore(int id, String valor, Camposproyecto camposproyecto, Inventario inventario) {
		this.id = id;
		this.valor = valor;
		this.camposproyecto = camposproyecto;
		this.inventario = inventario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdvalor() {
		return idvalor;
	}

	public void setIdvalor(String idvalor) {
		this.idvalor = idvalor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Camposproyecto getCamposproyecto() {
		return camposproyecto;
	}

	public void setCamposproyecto(Camposproyecto camposproyecto) {
		this.camposproyecto = camposproyecto;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "Valore [id=" + id + ", idvalor=" + idvalor + ", valor=" + valor + ", camposproyecto=" + camposproyecto
				+ ", inventario=" + inventario + "]";
	}

}