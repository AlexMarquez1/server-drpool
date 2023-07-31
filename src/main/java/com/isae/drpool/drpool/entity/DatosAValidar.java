package com.isae.drpool.drpool.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="datosavalidar")
@NamedQuery(name="DatosAValidar.findAll", query="SELECT d FROM DatosAValidar d")
public class DatosAValidar {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddatoavalidar;
	private String dato;
	private String estatus;
	private String tipodedato;
	
	@ManyToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;
	
	public DatosAValidar() {
	}

	public DatosAValidar(int iddatoavalidar, String dato, String estatus, String tipodedato, Inventario inventario) {
		this.iddatoavalidar = iddatoavalidar;
		this.dato = dato;
		this.estatus = estatus;
		this.tipodedato = tipodedato;
		this.inventario = inventario;
	}

	public int getIddatoavalidar() {
		return iddatoavalidar;
	}

	public void setIddatoavalidar(int iddatoavalidar) {
		this.iddatoavalidar = iddatoavalidar;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getTipodedato() {
		return tipodedato;
	}

	public void setTipodedato(String tipodedato) {
		this.tipodedato = tipodedato;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "DatosAValidar [iddatoavalidar=" + iddatoavalidar + ", dato=" + dato + ", estatus=" + estatus
				+ ", tipodedato=" + tipodedato + ", inventario=" + inventario + "]";
	}

}
