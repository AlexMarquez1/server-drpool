package com.isae.drpool.drpool.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="vista_catalogos_relacionados_id")
@NamedQuery(name="VistaCatalogoRelacionado.findAll", query="SELECT v FROM VistaCatalogoRelacionado v")
public class VistaCatalogoRelacionado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "tipoPadre")
	private String tipoPadre;
	
	@Column(name = "catalogoPadre")
	private String catalogoPadre;
	
	@Column(name = "tipoHijo")
	private String tipoHijo;
	
	@Column(name = "catalogoHijo")
	private String catalogoHijo;
	
	@Column(name = "idproyecto")
	private int idproyecto;

	public VistaCatalogoRelacionado() {
	}

	public VistaCatalogoRelacionado(int id, String tipoPadre, String catalogoPadre, String tipoHijo,
			String catalogoHijo, int idproyecto) {
		this.id = id;
		this.tipoPadre = tipoPadre;
		this.catalogoPadre = catalogoPadre;
		this.tipoHijo = tipoHijo;
		this.catalogoHijo = catalogoHijo;
		this.idproyecto = idproyecto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoPadre() {
		return tipoPadre;
	}

	public void setTipoPadre(String tipoPadre) {
		this.tipoPadre = tipoPadre;
	}

	public String getCatalogoPadre() {
		return catalogoPadre;
	}

	public void setCatalogoPadre(String catalogoPadre) {
		this.catalogoPadre = catalogoPadre;
	}

	public String getTipoHijo() {
		return tipoHijo;
	}

	public void setTipoHijo(String tipoHijo) {
		this.tipoHijo = tipoHijo;
	}

	public String getCatalogoHijo() {
		return catalogoHijo;
	}

	public void setCatalogoHijo(String catalogoHijo) {
		this.catalogoHijo = catalogoHijo;
	}



	public int getIdproyecto() {
		return idproyecto;
	}

	public void setIdproyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	@Override
	public String toString() {
		return "VistaCatalogoRelacionado [tipoPadre=" + tipoPadre + ", catalogoPadre=" + catalogoPadre + ", tipoHijo="
				+ tipoHijo + ", catalogoHijo=" + catalogoHijo + ", proyecto=" + idproyecto + "]";
	}
	
	
	
}
