package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="camposproyecto")
@NamedQuery(name="Camposproyecto.findAll", query="SELECT c FROM Camposproyecto c")
public class Camposproyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcamposproyecto;

	private String alerta;

	private String campo;
	
	private String validarduplicidad;
	
	private String editable;

	private int longitud;

	private String pattern;

	private String tipocampo;

	//bi-directional many-to-one association to Agrupacion
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idagrupacion")
	private Agrupacion agrupacion;

	//bi-directional many-to-one association to Proyecto
	@ManyToOne
	@JoinColumn(name="idproyecto")
	private Proyecto proyecto;
	
	private String pordefecto;

	public Camposproyecto() {
	}
	

	public Camposproyecto(int idcamposproyecto) {
		this.idcamposproyecto = idcamposproyecto;
	}


	public Camposproyecto(int idcamposproyecto, String alerta, String campo, String validarduplicidad, String editable,
			int longitud, String pattern, String tipocampo, Agrupacion agrupacion, Proyecto proyecto,
			String pordefecto) {
		this.idcamposproyecto = idcamposproyecto;
		this.alerta = alerta;
		this.campo = campo;
		this.validarduplicidad = validarduplicidad;
		this.editable = editable;
		this.longitud = longitud;
		this.pattern = pattern;
		this.tipocampo = tipocampo;
		this.agrupacion = agrupacion;
		this.proyecto = proyecto;
		this.pordefecto = pordefecto;
	}

	public int getIdcamposproyecto() {
		return idcamposproyecto;
	}

	public void setIdcamposproyecto(int idcamposproyecto) {
		this.idcamposproyecto = idcamposproyecto;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValidarduplicidad() {
		return validarduplicidad;
	}

	public void setValidarduplicidad(String validarduplicidad) {
		this.validarduplicidad = validarduplicidad;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTipocampo() {
		return tipocampo;
	}

	public void setTipocampo(String tipocampo) {
		this.tipocampo = tipocampo;
	}

	public Agrupacion getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(Agrupacion agrupacion) {
		this.agrupacion = agrupacion;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getPordefecto() {
		return pordefecto;
	}

	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}

	@Override
	public String toString() {
		return "Camposproyecto [idcamposproyecto=" + idcamposproyecto + ", alerta=" + alerta + ", campo=" + campo
				+ ", validarduplicidad=" + validarduplicidad + ", editable=" + editable + ", longitud=" + longitud
				+ ", pattern=" + pattern + ", tipocampo=" + tipocampo + ", agrupacion=" + agrupacion + ", proyecto="
				+ proyecto + ", pordefecto=" + pordefecto + "]";
	}
	
}