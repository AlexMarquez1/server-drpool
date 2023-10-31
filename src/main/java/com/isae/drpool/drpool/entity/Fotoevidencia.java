package com.isae.drpool.drpool.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="fotoevidencia")
@NamedQuery(name="Fotoevidencia.findAll", query="SELECT f FROM Fotoevidencia f")
public class Fotoevidencia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfoto;
	
	private String nombrefoto;
	
	private String url;
	
	private String coordenadas;
	
	private String fechacreacion;
	
//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne()
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idinventario")
	private Inventario inventario;

	@ManyToOne
	@JoinColumn(name="idcampo")
	private Camposproyecto campoProyecto;

	public Fotoevidencia(int idfoto, String nombrefoto, String url, String coordenadas,
			Usuario usuario, Inventario inventario, Camposproyecto campoProyecto) {
		super();
		this.idfoto = idfoto;
		this.nombrefoto = nombrefoto;
		this.url = url;
		this.coordenadas = coordenadas;
		this.usuario = usuario;
		this.inventario = inventario;
		this.campoProyecto = campoProyecto;
	}

	public Fotoevidencia() {
	}

	public int getIdfoto() {
		return idfoto;
	}

	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}

	public String getNombrefoto() {
		return nombrefoto;
	}

	public void setNombrefoto(String nombrefoto) {
		this.nombrefoto = nombrefoto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Camposproyecto getCampoProyecto() {
		return campoProyecto;
	}

	public void setCampoProyecto(Camposproyecto campoProyecto) {
		this.campoProyecto = campoProyecto;
	}

	public String getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(String fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	@Override
	public String toString() {
		return "Fotoevidencia [idfoto=" + idfoto + ", nombrefoto=" + nombrefoto + ", url=" + url + ", coordenadas="
				+ coordenadas + ", fechacreacion=" + fechacreacion + ", usuario=" + usuario + ", inventario="
				+ inventario + ", campoProyecto=" + campoProyecto + "]";
	}

	
	@PrePersist
	public void setFechaInsert() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechacreacion((dateFormat.format(new Date())).toString());
	}
	
	
	@PreUpdate
	public void setFechaUpdate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setFechacreacion((dateFormat.format(new Date())).toString());
	}

}
