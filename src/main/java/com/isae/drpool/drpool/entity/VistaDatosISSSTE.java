package com.isae.drpool.drpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="vista_datos_issste")
@NamedQuery(name="VistaDatosISSSTE.findAll", query="SELECT v FROM VistaDatosISSSTE v")
public class VistaDatosISSSTE {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idinventario;
	
	@Column(name = "FOLIO")
	private String folio;
	
	@Column(name = "CODIGODEBARRAS")
	private String codigoBarras;
	
	@Column(name = "LARGOCB")
	private int largoCB;
	
	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "CLUE")
	private String clue;
	
	@Column(name = "LARGOCLUE")
	private int largoCLUE;
	
	@Column(name = "CLAVEPRESUPUESTAL")
	private String clavePresupuestal;
	
	@Column(name = "LARGOCLAVE")
	private int largoClavePresupuestal;
	
	@Column(name = "UNIDADMEDICAOADMINISTRATIVA")
	private String unidadMedicaAdministrativa;
	
	@Column(name = "IDUNICO")
	private String idUnico;
	
	@Column(name = "FOLIOUNIDADMEDICA")
	private String folioUnidadMedica;
	
	@Column(name = "FOLIOUNIDADADMINISTRATIVA")
	private String folioUnidadAdministrativa;
	
	@Column(name = "FECHA")
	private String fecha;
	
	@Column(name = "HORA")
	private String hora;
	
	@Column(name = "NOMBREDELEMPLEADO")
	private String nombreEmpleado;
	
	@Column(name = "NUMERODELEMPLEADO")
	private String numeroEmpleado;
	
	@Column(name = "AREADEADSCRIPCION")
	private String areaAdscripcion;
	
	@Column(name = "CENTRODETRABAJO")
	private String centroTrabajo;
	
	@Column(name = "DOMICILIOLABORAL")
	private String domicilioLaboral;
	
	@Column(name = "CORREOINSTITUCIONAL")
	private String correoEmpleado;
	
	@Column(name = "NUMERODERED")
	private String numeroRedEmpleado;
	
	@Column(name = "MARCACANDADO")
	private String marcaCandado;
	
	@Column(name = "MODELOCANDADO")
	private String modeloCandado;
	
	@Column(name = "SERIECANDADO")
	private String serieCandado;
	
	@Column(name = "ETIQUETACANDADO")
	private String etiquetaCandado;
	
	@Column(name = "LARGOCANDADO")
	private int largoCandado;
	
	@Column(name = "MARCANOBREAK")
	private String marcaNoBreak;
	
	@Column(name = "MODELONOBREAK")
	private String modeloNoBreak;
	
	@Column(name = "SERIENOBREAK")
	private String serieNoBreak;
	
	@Column(name = "LARGOSERIE")
	private int largoSerie;
	
	@Column(name = "ETIQUETANOBREAK")
	private String etiquetaNobreak;
	
	@Column(name = "LARGOETIQUETA")
	private int largoEtiqueta;
	
	@Column(name = "NOMBREDELENLACE")
	private String nombreEnlace;
	
	@Column(name = "NUMERODELEMPLEADOENLACE")
	private String numeroEmpleadoEnlace;
	
	@Column(name = "UNIDADMEDICAOADMINISTRATIVAENLACE")
	private String unidadMedicaAdministrativaEnlace;
	
	@Column(name = "ADSCRIPCIONENLACE")
	private String adscripcionEnlace;
	
	@Column(name = "UBICACIONENLACE")
	private String ubicacionEnlace;
	
	@Column(name = "CORREOENLACE")
	private String correoEnlace;
	
	@Column(name = "NUMERODEREDENLACE")
	private String numeroRedEnlace;
	
	@Column(name = "APOYODELENLACE")
	private String apoyoEnlace;
	
	@Column(name = "REDDELAPOYO")
	private String redDelApoyo;
	
	public VistaDatosISSSTE() {
	}

	public VistaDatosISSSTE(int idinventario, String folio, String codigoBarras, int largoCB, String estado,
			String clue, int largoCLUE, String clavePresupuestal, int largoClavePresupuestal,
			String unidadMedicaAdministrativa, String idUnico, String folioUnidadMedica,
			String folioUnidadAdministrativa, String fecha, String hora, String nombreEmpleado, String numeroEmpleado,
			String areaAdscripcion, String centroTrabajo, String domicilioLaboral, String correoEmpleado,
			String numeroRedEmpleado, String marcaCandado, String modeloCandado, String serieCandado,
			String etiquetaCandado, int largoCandado, String marcaNoBreak, String modeloNoBreak, String serieNoBreak,
			int largoSerie, String etiquetaNobreak, int largoEtiqueta, String nombreEnlace, String numeroEmpleadoEnlace,
			String unidadMedicaAdministrativaEnlace, String adscripcionEnlace, String ubicacionEnlace,
			String correoEnlace, String numeroRedEnlace, String apoyoEnlace, String redDelApoyo) {
		this.idinventario = idinventario;
		this.folio = folio;
		this.codigoBarras = codigoBarras;
		this.largoCB = largoCB;
		this.estado = estado;
		this.clue = clue;
		this.largoCLUE = largoCLUE;
		this.clavePresupuestal = clavePresupuestal;
		this.largoClavePresupuestal = largoClavePresupuestal;
		this.unidadMedicaAdministrativa = unidadMedicaAdministrativa;
		this.idUnico = idUnico;
		this.folioUnidadMedica = folioUnidadMedica;
		this.folioUnidadAdministrativa = folioUnidadAdministrativa;
		this.fecha = fecha;
		this.hora = hora;
		this.nombreEmpleado = nombreEmpleado;
		this.numeroEmpleado = numeroEmpleado;
		this.areaAdscripcion = areaAdscripcion;
		this.centroTrabajo = centroTrabajo;
		this.domicilioLaboral = domicilioLaboral;
		this.correoEmpleado = correoEmpleado;
		this.numeroRedEmpleado = numeroRedEmpleado;
		this.marcaCandado = marcaCandado;
		this.modeloCandado = modeloCandado;
		this.serieCandado = serieCandado;
		this.etiquetaCandado = etiquetaCandado;
		this.largoCandado = largoCandado;
		this.marcaNoBreak = marcaNoBreak;
		this.modeloNoBreak = modeloNoBreak;
		this.serieNoBreak = serieNoBreak;
		this.largoSerie = largoSerie;
		this.etiquetaNobreak = etiquetaNobreak;
		this.largoEtiqueta = largoEtiqueta;
		this.nombreEnlace = nombreEnlace;
		this.numeroEmpleadoEnlace = numeroEmpleadoEnlace;
		this.unidadMedicaAdministrativaEnlace = unidadMedicaAdministrativaEnlace;
		this.adscripcionEnlace = adscripcionEnlace;
		this.ubicacionEnlace = ubicacionEnlace;
		this.correoEnlace = correoEnlace;
		this.numeroRedEnlace = numeroRedEnlace;
		this.apoyoEnlace = apoyoEnlace;
		this.redDelApoyo = redDelApoyo;
	}

	public int getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(int idinventario) {
		this.idinventario = idinventario;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public int getLargoCB() {
		return largoCB;
	}

	public void setLargoCB(int largoCB) {
		this.largoCB = largoCB;
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

	public int getLargoCLUE() {
		return largoCLUE;
	}

	public void setLargoCLUE(int largoCLUE) {
		this.largoCLUE = largoCLUE;
	}

	public String getClavePresupuestal() {
		return clavePresupuestal;
	}

	public void setClavePresupuestal(String clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}

	public int getLargoClavePresupuestal() {
		return largoClavePresupuestal;
	}

	public void setLargoClavePresupuestal(int largoClavePresupuestal) {
		this.largoClavePresupuestal = largoClavePresupuestal;
	}

	public String getUnidadMedicaAdministrativa() {
		return unidadMedicaAdministrativa;
	}

	public void setUnidadMedicaAdministrativa(String unidadMedicaAdministrativa) {
		this.unidadMedicaAdministrativa = unidadMedicaAdministrativa;
	}

	public String getIdUnico() {
		return idUnico;
	}

	public void setIdUnico(String idUnico) {
		this.idUnico = idUnico;
	}

	public String getFolioUnidadMedica() {
		return folioUnidadMedica;
	}

	public void setFolioUnidadMedica(String folioUnidadMedica) {
		this.folioUnidadMedica = folioUnidadMedica;
	}

	public String getFolioUnidadAdministrativa() {
		return folioUnidadAdministrativa;
	}

	public void setFolioUnidadAdministrativa(String folioUnidadAdministrativa) {
		this.folioUnidadAdministrativa = folioUnidadAdministrativa;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getAreaAdscripcion() {
		return areaAdscripcion;
	}

	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}

	public String getCentroTrabajo() {
		return centroTrabajo;
	}

	public void setCentroTrabajo(String centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}

	public String getDomicilioLaboral() {
		return domicilioLaboral;
	}

	public void setDomicilioLaboral(String domicilioLaboral) {
		this.domicilioLaboral = domicilioLaboral;
	}

	public String getCorreoEmpleado() {
		return correoEmpleado;
	}

	public void setCorreoEmpleado(String correoEmpleado) {
		this.correoEmpleado = correoEmpleado;
	}

	public String getNumeroRedEmpleado() {
		return numeroRedEmpleado;
	}

	public void setNumeroRedEmpleado(String numeroRedEmpleado) {
		this.numeroRedEmpleado = numeroRedEmpleado;
	}

	public String getMarcaCandado() {
		return marcaCandado;
	}

	public void setMarcaCandado(String marcaCandado) {
		this.marcaCandado = marcaCandado;
	}

	public String getModeloCandado() {
		return modeloCandado;
	}

	public void setModeloCandado(String modeloCandado) {
		this.modeloCandado = modeloCandado;
	}

	public String getSerieCandado() {
		return serieCandado;
	}

	public void setSerieCandado(String serieCandado) {
		this.serieCandado = serieCandado;
	}

	public String getEtiquetaCandado() {
		return etiquetaCandado;
	}

	public void setEtiquetaCandado(String etiquetaCandado) {
		this.etiquetaCandado = etiquetaCandado;
	}

	public int getLargoCandado() {
		return largoCandado;
	}

	public void setLargoCandado(int largoCandado) {
		this.largoCandado = largoCandado;
	}

	public String getMarcaNoBreak() {
		return marcaNoBreak;
	}

	public void setMarcaNoBreak(String marcaNoBreak) {
		this.marcaNoBreak = marcaNoBreak;
	}

	public String getModeloNoBreak() {
		return modeloNoBreak;
	}

	public void setModeloNoBreak(String modeloNoBreak) {
		this.modeloNoBreak = modeloNoBreak;
	}

	public String getSerieNoBreak() {
		return serieNoBreak;
	}

	public void setSerieNoBreak(String serieNoBreak) {
		this.serieNoBreak = serieNoBreak;
	}

	public int getLargoSerie() {
		return largoSerie;
	}

	public void setLargoSerie(int largoSerie) {
		this.largoSerie = largoSerie;
	}

	public String getEtiquetaNobreak() {
		return etiquetaNobreak;
	}

	public void setEtiquetaNobreak(String etiquetaNobreak) {
		this.etiquetaNobreak = etiquetaNobreak;
	}

	public int getLargoEtiqueta() {
		return largoEtiqueta;
	}

	public void setLargoEtiqueta(int largoEtiqueta) {
		this.largoEtiqueta = largoEtiqueta;
	}

	public String getNombreEnlace() {
		return nombreEnlace;
	}

	public void setNombreEnlace(String nombreEnlace) {
		this.nombreEnlace = nombreEnlace;
	}

	public String getNumeroEmpleadoEnlace() {
		return numeroEmpleadoEnlace;
	}

	public void setNumeroEmpleadoEnlace(String numeroEmpleadoEnlace) {
		this.numeroEmpleadoEnlace = numeroEmpleadoEnlace;
	}

	public String getUnidadMedicaAdministrativaEnlace() {
		return unidadMedicaAdministrativaEnlace;
	}

	public void setUnidadMedicaAdministrativaEnlace(String unidadMedicaAdministrativaEnlace) {
		this.unidadMedicaAdministrativaEnlace = unidadMedicaAdministrativaEnlace;
	}

	public String getAdscripcionEnlace() {
		return adscripcionEnlace;
	}

	public void setAdscripcionEnlace(String adscripcionEnlace) {
		this.adscripcionEnlace = adscripcionEnlace;
	}

	public String getUbicacionEnlace() {
		return ubicacionEnlace;
	}

	public void setUbicacionEnlace(String ubicacionEnlace) {
		this.ubicacionEnlace = ubicacionEnlace;
	}

	public String getCorreoEnlace() {
		return correoEnlace;
	}

	public void setCorreoEnlace(String correoEnlace) {
		this.correoEnlace = correoEnlace;
	}

	public String getNumeroRedEnlace() {
		return numeroRedEnlace;
	}

	public void setNumeroRedEnlace(String numeroRedEnlace) {
		this.numeroRedEnlace = numeroRedEnlace;
	}

	public String getApoyoEnlace() {
		return apoyoEnlace;
	}

	public void setApoyoEnlace(String apoyoEnlace) {
		this.apoyoEnlace = apoyoEnlace;
	}

	public String getRedDelApoyo() {
		return redDelApoyo;
	}

	public void setRedDelApoyo(String redDelApoyo) {
		this.redDelApoyo = redDelApoyo;
	}

	@Override
	public String toString() {
		return "VistaDatosISSSTE [idinventario=" + idinventario + ", folio=" + folio + ", codigoBarras=" + codigoBarras
				+ ", largoCB=" + largoCB + ", estado=" + estado + ", clue=" + clue + ", largoCLUE=" + largoCLUE
				+ ", clavePresupuestal=" + clavePresupuestal + ", largoClavePresupuestal=" + largoClavePresupuestal
				+ ", unidadMedicaAdministrativa=" + unidadMedicaAdministrativa + ", idUnico=" + idUnico
				+ ", folioUnidadMedica=" + folioUnidadMedica + ", folioUnidadAdministrativa="
				+ folioUnidadAdministrativa + ", fecha=" + fecha + ", hora=" + hora + ", nombreEmpleado="
				+ nombreEmpleado + ", numeroEmpleado=" + numeroEmpleado + ", areaAdscripcion=" + areaAdscripcion
				+ ", centroTrabajo=" + centroTrabajo + ", domicilioLaboral=" + domicilioLaboral + ", correoEmpleado="
				+ correoEmpleado + ", numeroRedEmpleado=" + numeroRedEmpleado + ", marcaCandado=" + marcaCandado
				+ ", modeloCandado=" + modeloCandado + ", serieCandado=" + serieCandado + ", etiquetaCandado="
				+ etiquetaCandado + ", largoCandado=" + largoCandado + ", marcaNoBreak=" + marcaNoBreak
				+ ", modeloNoBreak=" + modeloNoBreak + ", serieNoBreak=" + serieNoBreak + ", largoSerie=" + largoSerie
				+ ", etiquetaNobreak=" + etiquetaNobreak + ", largoEtiqueta=" + largoEtiqueta + ", nombreEnlace="
				+ nombreEnlace + ", numeroEmpleadoEnlace=" + numeroEmpleadoEnlace
				+ ", unidadMedicaAdministrativaEnlace=" + unidadMedicaAdministrativaEnlace + ", adscripcionEnlace="
				+ adscripcionEnlace + ", ubicacionEnlace=" + ubicacionEnlace + ", correoEnlace=" + correoEnlace
				+ ", numeroRedEnlace=" + numeroRedEnlace + ", apoyoEnlace=" + apoyoEnlace + ", redDelApoyo="
				+ redDelApoyo + "]";
	}

}
