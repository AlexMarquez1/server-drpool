package com.isae.drpool.drpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="documentogenerado")
@NamedQuery(name="Documentogenerado.findAll", query="SELECT u FROM Documentogenerado u")
public class Documentogenerado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddocumento ;
	
	@Column(name = "nombredocumento")
	private String nombreDocumento;
	
	@Column(name = "url")
	private String url;
	
	@OneToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;

	public Documentogenerado(int iddocumento, String nombreDocumento, String url, Inventario inventario) {
		this.iddocumento = iddocumento;
		this.nombreDocumento = nombreDocumento;
		this.url = url;
		this.inventario = inventario;
	}

	public Documentogenerado() {
	}

	public int getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(int iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "Documentogenerado [iddocumento=" + iddocumento + ", nombreDocumento=" + nombreDocumento + ", url=" + url
				+ ", inventario=" + inventario + "]";
	}
	

}
