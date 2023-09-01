package com.isae.drpool.drpool.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="proyectoalberca")
@NamedQuery(name="ProyectoAlberca.findAll", query="SELECT p FROM ProyectoAlberca p")
public class ProyectoAlberca {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproyectoalberca;
	
	private String nombreproyectoalberca;
	private String numeroproyecto;
	private String  tiposervicio;
	private String fechainiciocontrato;
	private String fechafincontrato;
	private String estatus;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoalberca")
    private List<ProyectoSede> proyectoSedes;

	public ProyectoAlberca() {
		super();
	}

	public ProyectoAlberca(int idproyectoalberca, String nombreproyectoalberca, String numeroproyecto,
			String tiposervicio, String fechainiciocontrato, String fechafincontrato, String estatus) {
		super();
		this.idproyectoalberca = idproyectoalberca;
		this.nombreproyectoalberca = nombreproyectoalberca;
		this.numeroproyecto = numeroproyecto;
		this.tiposervicio = tiposervicio;
		this.fechainiciocontrato = fechainiciocontrato;
		this.fechafincontrato = fechafincontrato;
		this.estatus = estatus;
	}

	public int getIdproyectoalberca() {
		return idproyectoalberca;
	}

	public void setIdproyectoalberca(int idproyectoalberca) {
		this.idproyectoalberca = idproyectoalberca;
	}

	public String getNombreproyectoalberca() {
		return nombreproyectoalberca;
	}

	public void setNombreproyectoalberca(String nombreproyectoalberca) {
		this.nombreproyectoalberca = nombreproyectoalberca;
	}

	public String getNumeroproyecto() {
		return numeroproyecto;
	}

	public void setNumeroproyecto(String numeroproyecto) {
		this.numeroproyecto = numeroproyecto;
	}

	public String getTiposervicio() {
		return tiposervicio;
	}

	public void setTiposervicio(String tiposervicio) {
		this.tiposervicio = tiposervicio;
	}

	public String getFechainiciocontrato() {
		return fechainiciocontrato;
	}

	public void setFechainiciocontrato(String fechainiciocontrato) {
		this.fechainiciocontrato = fechainiciocontrato;
	}

	public String getFechafincontrato() {
		return fechafincontrato;
	}

	public void setFechafincontrato(String fechafincontrato) {
		this.fechafincontrato = fechafincontrato;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<ProyectoSede> getProyectoSedes() {
		return proyectoSedes;
	}

	public void setProyectoSedes(List<ProyectoSede> proyectoSedes) {
		this.proyectoSedes = proyectoSedes;
	}

	@Override
	public String toString() {
		return "ProyectoAlberca [idproyectoalberca=" + idproyectoalberca + ", nombreproyectoalberca="
				+ nombreproyectoalberca + ", numeroproyecto=" + numeroproyecto + ", tiposervicio=" + tiposervicio
				+ ", fechainiciocontrato=" + fechainiciocontrato + ", fechafincontrato=" + fechafincontrato
				+ ", estatus=" + estatus + "]";
	}
}
