package com.isae.drpool.drpool.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="equipodosificador")
@NamedQuery(name="Equipodosificador.finAll", query="SELECT e FROM Equipocontrolador e")
public class Equipodosificador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iddosificador; 
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String tipoequipo;
	
	private String numero; 
	
	private String marcasolido; 
	
	private String modelosolido; 
	
	private String pastillasolido; 
	
	private String marcaliquido; 
	
	private String modeloliquido; 
	
	private String flujoliquido;  
	
	private String marcaclorador; 
	
	private String modeloclorador; 
	
	private String capacidadmaxclorador;
	
	private String voltajeclorador; 
	
	private String estatus; 
	
	private String observaciones; 
	
	private String fecha_ultimo_mantenimiento;

	public int getIddosificador() {
		return iddosificador;
	}

	public void setIddosificador(int iddosificador) {
		this.iddosificador = iddosificador;
	}

	public Alberca getAlberca() {
		return alberca;
	}

	public void setAlberca(Alberca alberca) {
		this.alberca = alberca;
	}

	public String getTipoequipo() {
		return tipoequipo;
	}

	public void setTipoequipo(String tipoequipo) {
		this.tipoequipo = tipoequipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMarcasolido() {
		return marcasolido;
	}

	public void setMarcasolido(String marcasolido) {
		this.marcasolido = marcasolido;
	}

	public String getModelosolido() {
		return modelosolido;
	}

	public void setModelosolido(String modelosolido) {
		this.modelosolido = modelosolido;
	}

	public String getPastillasolido() {
		return pastillasolido;
	}

	public void setPastillasolido(String pastillasolido) {
		this.pastillasolido = pastillasolido;
	}

	public String getMarcaliquido() {
		return marcaliquido;
	}

	public void setMarcaliquido(String marcaliquido) {
		this.marcaliquido = marcaliquido;
	}

	public String getModeloliquido() {
		return modeloliquido;
	}

	public void setModeloliquido(String modeloliquido) {
		this.modeloliquido = modeloliquido;
	}

	public String getFlujoliquido() {
		return flujoliquido;
	}

	public void setFlujoliquido(String flujoliquido) {
		this.flujoliquido = flujoliquido;
	}

	public String getMarcaclorador() {
		return marcaclorador;
	}

	public void setMarcaclorador(String marcaclorador) {
		this.marcaclorador = marcaclorador;
	}

	public String getModeloclorador() {
		return modeloclorador;
	}

	public void setModeloclorador(String modeloclorador) {
		this.modeloclorador = modeloclorador;
	}

	public String getCapacidadmaxclorador() {
		return capacidadmaxclorador;
	}

	public void setCapacidadmaxclorador(String capacidadmaxclorador) {
		this.capacidadmaxclorador = capacidadmaxclorador;
	}

	public String getVoltajeclorador() {
		return voltajeclorador;
	}

	public void setVoltajeclorador(String voltajeclorador) {
		this.voltajeclorador = voltajeclorador;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecha_ultimo_mantenimiento() {
		return fecha_ultimo_mantenimiento;
	}

	public void setFecha_ultimo_mantenimiento(String fecha_ultimo_mantenimiento) {
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	public Equipodosificador() {
		super();
	}

	public Equipodosificador(int iddosificador, Alberca alberca, String tipoequipo, String numero, String marcasolido,
			String modelosolido, String pastillasolido, String marcaliquido, String modeloliquido, String flujoliquido,
			String marcaclorador, String modeloclorador, String capacidadmaxclorador, String voltajeclorador,
			String estatus, String observaciones, String fecha_ultimo_mantenimiento) {
		super();
		this.iddosificador = iddosificador;
		this.alberca = alberca;
		this.tipoequipo = tipoequipo;
		this.numero = numero;
		this.marcasolido = marcasolido;
		this.modelosolido = modelosolido;
		this.pastillasolido = pastillasolido;
		this.marcaliquido = marcaliquido;
		this.modeloliquido = modeloliquido;
		this.flujoliquido = flujoliquido;
		this.marcaclorador = marcaclorador;
		this.modeloclorador = modeloclorador;
		this.capacidadmaxclorador = capacidadmaxclorador;
		this.voltajeclorador = voltajeclorador;
		this.estatus = estatus;
		this.observaciones = observaciones;
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	@Override
	public String toString() {
		return "Equipodosificador [iddosificador=" + iddosificador + ", alberca=" + alberca + ", tipoequipo="
				+ tipoequipo + ", numero=" + numero + ", marcasolido=" + marcasolido + ", modelosolido=" + modelosolido
				+ ", pastillasolido=" + pastillasolido + ", marcaliquido=" + marcaliquido + ", modeloliquido="
				+ modeloliquido + ", flujoliquido=" + flujoliquido + ", marcaclorador=" + marcaclorador
				+ ", modeloclorador=" + modeloclorador + ", capacidadmaxclorador=" + capacidadmaxclorador
				+ ", voltajeclorador=" + voltajeclorador + ", estatus=" + estatus + ", observaciones=" + observaciones
				+ ", fecha_ultimo_mantenimiento=" + fecha_ultimo_mantenimiento + "]";
	}

}
