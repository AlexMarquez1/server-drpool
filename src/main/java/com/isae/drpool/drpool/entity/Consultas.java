package com.isae.drpool.drpool.entity;

import java.util.List;

public class Consultas {
	
	private List<String> proyectos;
	private List<String> tipos;
	
	public Consultas(List<String> proyectos, List<String> tipos) {
		this.proyectos = proyectos;
		this.tipos = tipos;
	}

	public Consultas() {
	}

	public List<String> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<String> proyectos) {
		this.proyectos = proyectos;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	@Override
	public String toString() {
		return "Consultas [proyectos=" + proyectos + ", tipos=" + tipos + "]";
	}
	
	

}
