package com.isae.drpool.drpool.entity;

import javax.persistence.*;

@Entity
@Table(name="acceso")
@NamedQuery(name="Acceso.findAll", query="SELECT a FROM Acceso a")
public class Acceso {
	
	@Id
	int id; 
	
	String pass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Acceso(int id, String pass) {
		super();
		this.id = id;
		this.pass = pass;
	} 
	
	public Acceso() {}

	@Override
	public String toString() {
		return "Acceso [id=" + id + ", pass=" + pass + "]";
	}

}
