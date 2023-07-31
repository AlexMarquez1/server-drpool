package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fotosasistencia database table.
 * 
 */
@Entity
@Table(name="fotosasistencia")
@NamedQuery(name="Fotosasistencia.findAll", query="SELECT f FROM Fotosasistencia f")
public class Fotosasistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfoto;

	private String coordenadas;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharegistro;

	private String nombrefoto;

	private String url;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	public Fotosasistencia() {
	}

	public int getIdfoto() {
		return this.idfoto;
	}

	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}

	public String getCoordenadas() {
		return this.coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public String getNombrefoto() {
		return this.nombrefoto;
	}

	public void setNombrefoto(String nombrefoto) {
		this.nombrefoto = nombrefoto;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Fotosasistencia [idfoto=" + idfoto + ", coordenadas=" + coordenadas + ", fecharegistro=" + fecharegistro
				+ ", nombrefoto=" + nombrefoto + ", url=" + url + ", usuario=" + usuario + "]";
	}
	
	

}