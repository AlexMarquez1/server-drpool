package com.isae.drpool.drpool.entity;

public class AsistenciaUsuario {

	private int id;
	private String usuario;
	private String horaEntrada;
	private String horaSalida;
	private String dia;
	
	public AsistenciaUsuario(int id, String usuario, String horaEntrada, String horaSalida, String dia) {
		this.id = id;
		this.usuario = usuario;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.dia = dia;
	}

	public AsistenciaUsuario() {
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	@Override
	public String toString() {
		return "Asistencia [id=" + id + ", usuario=" + usuario + ", horaEntrada=" + horaEntrada + ", horaSalida="
				+ horaSalida + ", dia=" + dia + "]";
	}

}
