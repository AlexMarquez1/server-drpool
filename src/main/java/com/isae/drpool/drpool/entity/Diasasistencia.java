package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diasasistencia database table.
 * 
 */
@Entity
@Table(name="diasasistencia")
@NamedQuery(name="Diasasistencia.findAll", query="SELECT d FROM Diasasistencia d")
public class Diasasistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddias;

	private int asistenciacompleta;

	//bi-directional many-to-one association to Dia
	@ManyToOne
	@JoinColumn(name="iddia")
	private Dia dia;

	//bi-directional many-to-one association to Asistencia
	@ManyToOne
	@JoinColumn(name="idasistencia")
	private Asistencia asistencia;

	public Diasasistencia() {
	}

	public int getIddias() {
		return this.iddias;
	}

	public void setIddias(int iddias) {
		this.iddias = iddias;
	}

	public int getAsistenciacompleta() {
		return this.asistenciacompleta;
	}

	public void setAsistenciacompleta(byte asistenciacompleta) {
		this.asistenciacompleta = asistenciacompleta;
	}

	public Dia getDia() {
		return this.dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Asistencia getAsistencia() {
		return this.asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}

	@Override
	public String toString() {
		return "Diasasistencia [iddias=" + iddias + ", asistenciacompleta=" + asistenciacompleta + ", dia=" + dia
				+ ", asistencia=" + asistencia + "]";
	}

}