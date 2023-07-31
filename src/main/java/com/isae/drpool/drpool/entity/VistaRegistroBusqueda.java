package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="vista_registro_busqueda")
//@NamedQuery(name="VistaRegistroBusqueda.findAll", query="SELECT v FROM vista_registro_busqueda v")
public class VistaRegistroBusqueda implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idinventario;
	
	@Column(name = "folio")
	private String folio;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechacreacionregistro")
	private Date fechacreacionregistro;
	
	@ManyToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;
	
	@Column(name = "valor")
	private String valor;

	public VistaRegistroBusqueda() {
	}

	public VistaRegistroBusqueda(int idinventario, String folio, Date fechacreacionregistro, Proyecto proyecto,
			String valor) {
		this.idinventario = idinventario;
		this.folio = folio;
		this.fechacreacionregistro = fechacreacionregistro;
		this.proyecto = proyecto;
		this.valor = valor;
	}

	public int getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(int idinventario) {
		this.idinventario = idinventario;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Date getFechacreacionregistro() {
		return fechacreacionregistro;
	}

	public void setFechacreacionregistro(Date fechacreacionregistro) {
		this.fechacreacionregistro = fechacreacionregistro;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
