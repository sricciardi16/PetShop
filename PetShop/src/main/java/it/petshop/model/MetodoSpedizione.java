package it.petshop.model;

import java.io.Serializable;

public class MetodoSpedizione implements Serializable {

	private static final long serialVersionUID = -3081607817146211318L;

	private int id;
	private String descrizione;
	private double prezzo;
	private int giorniConsegnaPrevisti;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getGiorniConsegnaPrevisti() {
		return giorniConsegnaPrevisti;
	}

	public void setGiorniConsegnaPrevisti(int giorniConsegnaPrevisti) {
		this.giorniConsegnaPrevisti = giorniConsegnaPrevisti;
	}

}
