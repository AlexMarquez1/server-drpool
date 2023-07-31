package com.isae.drpool.drpool.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class AsistenciaAux implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Usuario usuario;
    private String horaDeEntrada;
    private String horaDeSalida;
    private String dia;
    private String urlFoto;
    private String coordenadasFoto;
    
	public AsistenciaAux(Usuario usuario, String horaDeEntrada, String horaDeSalida, String dia, String urlFoto,
			String coordenadasFoto) {
		this.usuario = usuario;
		this.horaDeEntrada = horaDeEntrada;
		this.horaDeSalida = horaDeSalida;
		this.dia = dia;
		this.urlFoto = urlFoto;
		this.coordenadasFoto = coordenadasFoto;
	}

	public AsistenciaAux() {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getHoraDeEntrada() {
		return horaDeEntrada;
	}

	public void setHoraDeEntrada(String horaDeEntrada) {
		this.horaDeEntrada = horaDeEntrada;
	}

	public String getHoraDeSalida() {
		return horaDeSalida;
	}

	public void setHoraDeSalida(String horaDeSalida) {
		this.horaDeSalida = horaDeSalida;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getCoordenadasFoto() {
		return coordenadasFoto;
	}

	public void setCoordenadasFoto(String coordenadasFoto) {
		this.coordenadasFoto = coordenadasFoto;
	}

	@Override
	public String toString() {
		return "Asistencia [usuario=" + usuario + ", horaDeEntrada=" + horaDeEntrada + ", horaDeSalida=" + horaDeSalida
				+ ", dia=" + dia + ", urlFoto=" + urlFoto + ", coordenadasFoto=" + coordenadasFoto + "]";
	}
}
