package com.isae.drpool.drpool.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="proyectosede")
@NamedQuery(name="ProyectoSede.findAll", query="SELECT p FROM ProyectoSede p")
public class ProyectoSede {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproyectosedes;
	
	@OneToOne
	@JoinColumn(name="idsede")
    private Sede sede;
	
	
	@ManyToOne
    @JoinColumn(name="idproyecto", nullable = false, updatable = false)
    private ProyectoAlberca proyectoalberca;
	
	public ProyectoSede() {
		super();
	}

	public ProyectoSede(int idproyectosedes, Sede sede, ProyectoAlberca proyectoalberca) {
		super();
		this.idproyectosedes = idproyectosedes;
		this.sede = sede;
		this.proyectoalberca = proyectoalberca;
	}

	public int getIdproyectosedes() {
		return idproyectosedes;
	}

	public void setIdproyectosedes(int idproyectosedes) {
		this.idproyectosedes = idproyectosedes;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}



}
