package it.petshop.dto;

import java.io.Serializable;

public class Categoria implements Serializable {

	private static final long serialVersionUID = -6493001295294954287L;

	private int id;
	private String animale;
	private String tipologia;
	private String tipologiaIn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnimale() {
		return animale;
	}

	public void setAnimale(String animale) {
		this.animale = animale;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipologiaIn() {
		return tipologiaIn;
	}

	public void setTipologiaIn(String tipologiaIn) {
		this.tipologiaIn = tipologiaIn;
	}
}
