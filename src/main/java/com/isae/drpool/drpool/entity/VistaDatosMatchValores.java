package com.isae.drpool.drpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="vista_datos_match_valores")
@NamedQuery(name="VistaDatosMatchValores.findAll", query="SELECT v FROM VistaDatosMatchValores v")
public class VistaDatosMatchValores {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddatoavalidar;
	
//	@ManyToOne
//	@JoinColumn(name="idinventario")
//	private Inventario inventario;
	
	@Column(name="idinventario")
	private String idinventario;
	
	@Column(name="dato" )
	private String dato;
	
	@Column(name="valor")
	private String valor;

	public VistaDatosMatchValores() {
	}

	public VistaDatosMatchValores(int iddatoavalidar, String idinventario, String dato, String valor) {
		this.iddatoavalidar = iddatoavalidar;
		this.idinventario = idinventario;
		this.dato = dato;
		this.valor = valor;
	}

	public int getIddatoavalidar() {
		return iddatoavalidar;
	}

	public void setIddatoavalidar(int iddatoavalidar) {
		this.iddatoavalidar = iddatoavalidar;
	}

	public String getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(String idinventario) {
		this.idinventario = idinventario;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "VistaDatosMatchValores [iddatoavalidar=" + iddatoavalidar + ", idinventario=" + idinventario + ", dato="
				+ dato + ", valor=" + valor + "]";
	}

}
