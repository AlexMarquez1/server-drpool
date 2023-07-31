package com.isae.drpool.drpool.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="datosissste")
@NamedQuery(name="Datosissste.findAll", query="SELECT d FROM Datosissste d")
public class Datosissste implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddatoissste;
	
	private String estado;
	private String clue;
	private String clavepresupuestal;
	private String idunico;
	private String nombreusuario;
	private String numerousuario;
	private String unidadmedicaadministrativausuario;
	private String areaadscripcionusuario;
	private String centrotrabajousuario;
	private String domiciliousuario;
	private String correoinstitucionalusuario;
	private String numeroredusuario;
	private String nombreenlace;
	private String numeroenlace;
	private String unidadmedicaadministrativaenlace;
	private String areaadscripcionenlace;
	private String ubicacionenlace;
	private String correoinstitucionalenlace;
	private String numeroredenlace;
	private String apoyoenlace;
	private String numeroredapoyoenlace;
	
	public Datosissste() {
	}

	public Datosissste(int iddatoissste, String estado, String clue, String clavepresupuestal, String idunico,
			String nombreusuario, String numerousuario, String unidadmedicaadministrativausuario,
			String areaadscripcionusuario, String centrotrabajousuario, String domiciliousuario,
			String correoinstitucionalusuario, String numeroredusuario, String nombreenlace, String numeroenlace,
			String unidadmedicaadministrativaenlace, String areaadscripcionenlace, String ubicacionenlace,
			String correoinstitucionalenlace, String numeroredenlace, String apoyoenlace, String numeroredapoyoenlace) {
		this.iddatoissste = iddatoissste;
		this.estado = estado;
		this.clue = clue;
		this.clavepresupuestal = clavepresupuestal;
		this.idunico = idunico;
		this.nombreusuario = nombreusuario;
		this.numerousuario = numerousuario;
		this.unidadmedicaadministrativausuario = unidadmedicaadministrativausuario;
		this.areaadscripcionusuario = areaadscripcionusuario;
		this.centrotrabajousuario = centrotrabajousuario;
		this.domiciliousuario = domiciliousuario;
		this.correoinstitucionalusuario = correoinstitucionalusuario;
		this.numeroredusuario = numeroredusuario;
		this.nombreenlace = nombreenlace;
		this.numeroenlace = numeroenlace;
		this.unidadmedicaadministrativaenlace = unidadmedicaadministrativaenlace;
		this.areaadscripcionenlace = areaadscripcionenlace;
		this.ubicacionenlace = ubicacionenlace;
		this.correoinstitucionalenlace = correoinstitucionalenlace;
		this.numeroredenlace = numeroredenlace;
		this.apoyoenlace = apoyoenlace;
		this.numeroredapoyoenlace = numeroredapoyoenlace;
	}

	public int getIddatoissste() {
		return iddatoissste;
	}

	public void setIddatoissste(int iddatoissste) {
		this.iddatoissste = iddatoissste;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public String getClavepresupuestal() {
		return clavepresupuestal;
	}

	public void setClavepresupuestal(String clavepresupuestal) {
		this.clavepresupuestal = clavepresupuestal;
	}

	public String getIdunico() {
		return idunico;
	}

	public void setIdunico(String idunico) {
		this.idunico = idunico;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getNumerousuario() {
		return numerousuario;
	}

	public void setNumerousuario(String numerousuario) {
		this.numerousuario = numerousuario;
	}

	public String getUnidadmedicaadministrativausuario() {
		return unidadmedicaadministrativausuario;
	}

	public void setUnidadmedicaadministrativausuario(String unidadmedicaadministrativausuario) {
		this.unidadmedicaadministrativausuario = unidadmedicaadministrativausuario;
	}

	public String getAreaadscripcionusuario() {
		return areaadscripcionusuario;
	}

	public void setAreaadscripcionusuario(String areaadscripcionusuario) {
		this.areaadscripcionusuario = areaadscripcionusuario;
	}

	public String getCentrotrabajousuario() {
		return centrotrabajousuario;
	}

	public void setCentrotrabajousuario(String centrotrabajousuario) {
		this.centrotrabajousuario = centrotrabajousuario;
	}

	public String getDomiciliousuario() {
		return domiciliousuario;
	}

	public void setDomiciliousuario(String domiciliousuario) {
		this.domiciliousuario = domiciliousuario;
	}

	public String getCorreoinstitucionalusuario() {
		return correoinstitucionalusuario;
	}

	public void setCorreoinstitucionalusuario(String correoinstitucionalusuario) {
		this.correoinstitucionalusuario = correoinstitucionalusuario;
	}

	public String getNumeroredusuario() {
		return numeroredusuario;
	}

	public void setNumeroredusuario(String numeroredusuario) {
		this.numeroredusuario = numeroredusuario;
	}

	public String getNombreenlace() {
		return nombreenlace;
	}

	public void setNombreenlace(String nombreenlace) {
		this.nombreenlace = nombreenlace;
	}

	public String getNumeroenlace() {
		return numeroenlace;
	}

	public void setNumeroenlace(String numeroenlace) {
		this.numeroenlace = numeroenlace;
	}

	public String getUnidadmedicaadministrativaenlace() {
		return unidadmedicaadministrativaenlace;
	}

	public void setUnidadmedicaadministrativaenlace(String unidadmedicaadministrativaenlace) {
		this.unidadmedicaadministrativaenlace = unidadmedicaadministrativaenlace;
	}

	public String getAreaadscripcionenlace() {
		return areaadscripcionenlace;
	}

	public void setAreaadscripcionenlace(String areaadscripcionenlace) {
		this.areaadscripcionenlace = areaadscripcionenlace;
	}

	public String getUbicacionenlace() {
		return ubicacionenlace;
	}

	public void setUbicacionenlace(String ubicacionenlace) {
		this.ubicacionenlace = ubicacionenlace;
	}

	public String getCorreoinstitucionalenlace() {
		return correoinstitucionalenlace;
	}

	public void setCorreoinstitucionalenlace(String correoinstitucionalenlace) {
		this.correoinstitucionalenlace = correoinstitucionalenlace;
	}

	public String getNumeroredenlace() {
		return numeroredenlace;
	}

	public void setNumeroredenlace(String numeroredenlace) {
		this.numeroredenlace = numeroredenlace;
	}

	public String getApoyoenlace() {
		return apoyoenlace;
	}

	public void setApoyoenlace(String apoyoenlace) {
		this.apoyoenlace = apoyoenlace;
	}

	public String getNumeroredapoyoenlace() {
		return numeroredapoyoenlace;
	}

	public void setNumeroredapoyoenlace(String numeroredapoyoenlace) {
		this.numeroredapoyoenlace = numeroredapoyoenlace;
	}

	@Override
	public String toString() {
		return "Datosissste [iddatoissste=" + iddatoissste + ", estado=" + estado + ", clue=" + clue
				+ ", clavepresupuestal=" + clavepresupuestal + ", idunico=" + idunico + ", nombreusuario="
				+ nombreusuario + ", numerousuario=" + numerousuario + ", unidadmedicaadministrativausuario="
				+ unidadmedicaadministrativausuario + ", areaadscripcionusuario=" + areaadscripcionusuario
				+ ", centrotrabajousuario=" + centrotrabajousuario + ", domiciliousuario=" + domiciliousuario
				+ ", correoinstitucionalusuario=" + correoinstitucionalusuario + ", numeroredusuario="
				+ numeroredusuario + ", nombreenlace=" + nombreenlace + ", numeroenlace=" + numeroenlace
				+ ", unidadmedicaadministrativaenlace=" + unidadmedicaadministrativaenlace + ", areaadscripcionenlace="
				+ areaadscripcionenlace + ", ubicacionenlace=" + ubicacionenlace + ", correoinstitucionalenlace="
				+ correoinstitucionalenlace + ", numeroredenlace=" + numeroredenlace + ", apoyoenlace=" + apoyoenlace
				+ ", numeroredapoyoenlace=" + numeroredapoyoenlace + "]";
	}
}
