package com.isae.drpool.drpool.entity;

import javax.persistence.*;

@Entity
@Table(name="equipofiltrado")
@NamedQuery(name="Equipofiltrado.finAll", query="SELECT e FROM Equipofiltrado e")
public class Equipofiltrado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfiltro; 
	
	@OneToOne
	@JoinColumn(name="idalberca")
	private Alberca alberca;
	
	private String tipoequipo;
	
	private String numero; 
	
	private String marcaarena; 
	
	private String modeloarena; 
	
	private String cantidadarena; 
	
	private String cantidadgravaarena; 
	
	private String cantidadmaxarena;
	
	private String marcazeolita; 
	
	private String modelozeolita;
	
	private String cantidadzeolita;
	
	private String cantidadgravazeolita;
	
	private String cantidadmaxzeolita; 
	
	private String marcacartucho; 
	
	private String modelocartucho;
	
	private String cantidadcartucho; 
	
	private String cantidadgravacartucho; 
	
	private String cantidadmaxcartucho;
	
	private String marcaesponja;
	
	private String modeloesponja; 
	
	private String cantidadesponja;
	
	private String cantidadgravaesponja;
	
	private String cantidadmaxesponja; 
	
	private String estatus; 
	
	private String observaciones; 
	
	private String fecha_ultimo_mantenimiento;

	public int getIdfiltro() {
		return idfiltro;
	}

	public void setIdfiltro(int idfiltro) {
		this.idfiltro = idfiltro;
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

	public String getMarcaarena() {
		return marcaarena;
	}

	public void setMarcaarena(String marcaarena) {
		this.marcaarena = marcaarena;
	}

	public String getModeloarena() {
		return modeloarena;
	}

	public void setModeloarena(String modeloarena) {
		this.modeloarena = modeloarena;
	}

	public String getCantidadarena() {
		return cantidadarena;
	}

	public void setCantidadarena(String cantidadarena) {
		this.cantidadarena = cantidadarena;
	}

	public String getCantidadgravaarena() {
		return cantidadgravaarena;
	}

	public void setCantidadgravaarena(String cantidadgravaarena) {
		this.cantidadgravaarena = cantidadgravaarena;
	}

	public String getCantidadmaxarena() {
		return cantidadmaxarena;
	}

	public void setCantidadmaxarena(String cantidadmaxarena) {
		this.cantidadmaxarena = cantidadmaxarena;
	}

	public String getMarcazeolita() {
		return marcazeolita;
	}

	public void setMarcazeolita(String marcazeolita) {
		this.marcazeolita = marcazeolita;
	}

	public String getModelozeolita() {
		return modelozeolita;
	}

	public void setModelozeolita(String modelozeolita) {
		this.modelozeolita = modelozeolita;
	}

	public String getCantidadzeolita() {
		return cantidadzeolita;
	}

	public void setCantidadzeolita(String cantidadzeolita) {
		this.cantidadzeolita = cantidadzeolita;
	}

	public String getCantidadgravazeolita() {
		return cantidadgravazeolita;
	}

	public void setCantidadgravazeolita(String cantidadgravazeolita) {
		this.cantidadgravazeolita = cantidadgravazeolita;
	}

	public String getCantidadmaxzeolita() {
		return cantidadmaxzeolita;
	}

	public void setCantidadmaxzeolita(String cantidadmaxzeolita) {
		this.cantidadmaxzeolita = cantidadmaxzeolita;
	}

	public String getMarcacartucho() {
		return marcacartucho;
	}

	public void setMarcacartucho(String marcacartucho) {
		this.marcacartucho = marcacartucho;
	}

	public String getModelocartucho() {
		return modelocartucho;
	}

	public void setModelocartucho(String modelocartucho) {
		this.modelocartucho = modelocartucho;
	}

	public String getCantidadcartucho() {
		return cantidadcartucho;
	}

	public void setCantidadcartucho(String cantidadcartucho) {
		this.cantidadcartucho = cantidadcartucho;
	}

	public String getCantidadgravacartucho() {
		return cantidadgravacartucho;
	}

	public void setCantidadgravacartucho(String cantidadgravacartucho) {
		this.cantidadgravacartucho = cantidadgravacartucho;
	}

	public String getCantidadmaxcartucho() {
		return cantidadmaxcartucho;
	}

	public void setCantidadmaxcartucho(String cantidadmaxcartucho) {
		this.cantidadmaxcartucho = cantidadmaxcartucho;
	}

	public String getMarcaesponja() {
		return marcaesponja;
	}

	public void setMarcaesponja(String marcaesponja) {
		this.marcaesponja = marcaesponja;
	}

	public String getModeloesponja() {
		return modeloesponja;
	}

	public void setModeloesponja(String modeloesponja) {
		this.modeloesponja = modeloesponja;
	}

	public String getCantidadesponja() {
		return cantidadesponja;
	}

	public void setCantidadesponja(String cantidadesponja) {
		this.cantidadesponja = cantidadesponja;
	}

	public String getCantidadgravaesponja() {
		return cantidadgravaesponja;
	}

	public void setCantidadgravaesponja(String cantidadgravaesponja) {
		this.cantidadgravaesponja = cantidadgravaesponja;
	}

	public String getCantidadmaxesponja() {
		return cantidadmaxesponja;
	}

	public void setCantidadmaxesponja(String cantidadmaxesponja) {
		this.cantidadmaxesponja = cantidadmaxesponja;
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

	public Equipofiltrado() {
		super();
	}

	public Equipofiltrado(int idfiltro, Alberca alberca, String tipoequipo, String numero, String marcaarena,
			String modeloarena, String cantidadarena, String cantidadgravaarena, String cantidadmaxarena,
			String marcazeolita, String modelozeolita, String cantidadzeolita, String cantidadgravazeolita,
			String cantidadmaxzeolita, String marcacartucho, String modelocartucho, String cantidadcartucho,
			String cantidadgravacartucho, String cantidadmaxcartucho, String marcaesponja, String modeloesponja,
			String cantidadesponja, String cantidadgravaesponja, String cantidadmaxesponja, String estatus,
			String observaciones, String fecha_ultimo_mantenimiento) {
		super();
		this.idfiltro = idfiltro;
		this.alberca = alberca;
		this.tipoequipo = tipoequipo;
		this.numero = numero;
		this.marcaarena = marcaarena;
		this.modeloarena = modeloarena;
		this.cantidadarena = cantidadarena;
		this.cantidadgravaarena = cantidadgravaarena;
		this.cantidadmaxarena = cantidadmaxarena;
		this.marcazeolita = marcazeolita;
		this.modelozeolita = modelozeolita;
		this.cantidadzeolita = cantidadzeolita;
		this.cantidadgravazeolita = cantidadgravazeolita;
		this.cantidadmaxzeolita = cantidadmaxzeolita;
		this.marcacartucho = marcacartucho;
		this.modelocartucho = modelocartucho;
		this.cantidadcartucho = cantidadcartucho;
		this.cantidadgravacartucho = cantidadgravacartucho;
		this.cantidadmaxcartucho = cantidadmaxcartucho;
		this.marcaesponja = marcaesponja;
		this.modeloesponja = modeloesponja;
		this.cantidadesponja = cantidadesponja;
		this.cantidadgravaesponja = cantidadgravaesponja;
		this.cantidadmaxesponja = cantidadmaxesponja;
		this.estatus = estatus;
		this.observaciones = observaciones;
		this.fecha_ultimo_mantenimiento = fecha_ultimo_mantenimiento;
	}

	@Override
	public String toString() {
		return "Equipofiltrado [idfiltro=" + idfiltro + ", alberca=" + alberca + ", tipoequipo=" + tipoequipo
				+ ", numero=" + numero + ", marcaarena=" + marcaarena + ", modeloarena=" + modeloarena
				+ ", cantidadarena=" + cantidadarena + ", cantidadgravaarena=" + cantidadgravaarena
				+ ", cantidadmaxarena=" + cantidadmaxarena + ", marcazeolita=" + marcazeolita + ", modelozeolita="
				+ modelozeolita + ", cantidadzeolita=" + cantidadzeolita + ", cantidadgravazeolita="
				+ cantidadgravazeolita + ", cantidadmaxzeolita=" + cantidadmaxzeolita + ", marcacartucho="
				+ marcacartucho + ", modelocartucho=" + modelocartucho + ", cantidadcartucho=" + cantidadcartucho
				+ ", cantidadgravacartucho=" + cantidadgravacartucho + ", cantidadmaxcartucho=" + cantidadmaxcartucho
				+ ", marcaesponja=" + marcaesponja + ", modeloesponja=" + modeloesponja + ", cantidadesponja="
				+ cantidadesponja + ", cantidadgravaesponja=" + cantidadgravaesponja + ", cantidadmaxesponja="
				+ cantidadmaxesponja + ", estatus=" + estatus + ", observaciones=" + observaciones
				+ ", fecha_ultimo_mantenimiento=" + fecha_ultimo_mantenimiento + "]";
	}

}
