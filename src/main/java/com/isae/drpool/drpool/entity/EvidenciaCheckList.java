package com.isae.drpool.drpool.entity;

import java.net.URL;

public class EvidenciaCheckList {
	
	private String evidencia;
	private URL url;
	
	public EvidenciaCheckList(String evidencia, URL url) {
		this.evidencia = evidencia;
		this.url = url;
	}

	public EvidenciaCheckList() {
	}

	public String getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "EvidenciaCheckList [evidencia=" + evidencia + ", url=" + url + "]";
	}
	

}
