package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the proyecto database table.
 * 
 */
@Entity
@Table(name="proyecto")
@NamedQuery(name="Proyecto.findAll", query="SELECT p FROM Proyecto p")
public class Proyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproyecto;

	@Temporal(TemporalType.DATE)
	private Date fechacreacion;

	private String proyecto;
	
	private String target;

	//bi-directional many-to-one association to Tipoproyecto
	@ManyToOne
	@JoinColumn(name="idtipo")
	private Tipoproyecto tipoproyecto;
	
	@ManyToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String folioautomatico;

	public Proyecto() {
	}

	public Proyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	public Proyecto(int idproyecto, Date fechacreacion, String proyecto, Tipoproyecto tipoproyecto) {
		this.idproyecto = idproyecto;
		this.fechacreacion = fechacreacion;
		this.proyecto = proyecto;
		this.tipoproyecto = tipoproyecto;
	}

	public Proyecto(int idproyecto, Date fechacreacion, String proyecto, String target, Tipoproyecto tipoproyecto,
			Alberca alberca, String folioautomatico) {
		super();
		this.idproyecto = idproyecto;
		this.fechacreacion = fechacreacion;
		this.proyecto = proyecto;
		this.target = target;
		this.tipoproyecto = tipoproyecto;
		this.alberca = alberca;
		this.folioautomatico = folioautomatico;
	}
	
	

	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public int getIdproyecto() {
		return idproyecto;
	}

	public void setIdproyecto(int idproyecto) {
		this.idproyecto = idproyecto;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Tipoproyecto getTipoproyecto() {
		return tipoproyecto;
	}

	public void setTipoproyecto(Tipoproyecto tipoproyecto) {
		this.tipoproyecto = tipoproyecto;
	}

	public String getFolioautomatico() {
		return folioautomatico;
	}

	public void setFolioautomatico(String folioautomatico) {
		this.folioautomatico = folioautomatico;
	}

	@Override
	public String toString() {
		return "Proyecto [idproyecto=" + idproyecto + ", fechacreacion=" + fechacreacion + ", proyecto=" + proyecto
				+ ", target=" + target + ", tipoproyecto=" + tipoproyecto + ", folioautomatico=" + folioautomatico
				+ "]";
	}
}