package it.petshop.model;

import java.io.Serializable;

public class MetodoPagamento implements Serializable {

	private static final long serialVersionUID = 1946934677241912933L;

	private int id;
	private String tipo;
	private String token;
	private String dettagliVisualizzazione;
	private int idUtente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDettagliVisualizzazione() {
		return dettagliVisualizzazione;
	}

	public void setDettagliVisualizzazione(String dettagliVisualizzazione) {
		this.dettagliVisualizzazione = dettagliVisualizzazione;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

}
