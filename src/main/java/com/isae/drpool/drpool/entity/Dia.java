package com.isae.drpool.drpool.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the dias database table.
 * 
 */
@Entity
@Table(name="dias")
@NamedQuery(name="Dia.findAll", query="SELECT d FROM Dia d")
public class Dia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddias;

	@Temporal(TemporalType.DATE)
	private Date dia;

	public Dia() {
	}

	public int getIddias() {
		return this.iddias;
	}

	public void setIddias(int iddias) {
		this.iddias = iddias;
	}

	public Date getDia() {
		return this.dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	@Override
	public String toString() {
		return "Dia [iddias=" + iddias + ", dia=" + dia + "]";
	}


}