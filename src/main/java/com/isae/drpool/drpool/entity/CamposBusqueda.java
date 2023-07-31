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
@Table(name="camposbusqueda")
@NamedQuery(name="CamposBusqueda.findAll", query="SELECT C FROM CamposBusqueda C")
public class CamposBusqueda {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int idcampobusqueda;
    String campovalor;
    @ManyToOne
	@JoinColumn(name="idcamposproyecto")
    Camposproyecto camposProyecto;
    
	String idagrupacion;

	public CamposBusqueda() {
		super();
	}

	public CamposBusqueda(int idcampobusqueda, String campovalor, Camposproyecto camposProyecto, String idagrupacion) {
		super();
		this.idcampobusqueda = idcampobusqueda;
		this.campovalor = campovalor;
		this.camposProyecto = camposProyecto;
		this.idagrupacion = idagrupacion;
	}
	
	

	public int getIdcampobusqueda() {
		return idcampobusqueda;
	}

	public void setIdcampobusqueda(int idcampobusqueda) {
		this.idcampobusqueda = idcampobusqueda;
	}

	public String getCampovalor() {
		return campovalor;
	}

	public void setCampovalor(String campovalor) {
		this.campovalor = campovalor;
	}

	public Camposproyecto getCamposProyecto() {
		return camposProyecto;
	}

	public void setCamposProyecto(Camposproyecto camposProyecto) {
		this.camposProyecto = camposProyecto;
	}

	public String getIdagrupacion() {
		return idagrupacion;
	}

	public void setIdagrupacion(String idagrupacion) {
		this.idagrupacion = idagrupacion;
	}

	@Override
	public String toString() {
		return "CamposBusqueda [idcampobusqueda=" + idcampobusqueda + ", campovalor=" + campovalor + ", camposProyecto="
				+ camposProyecto + ", idagrupacion=" + idagrupacion + "]";
	}

}
