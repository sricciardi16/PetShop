package it.petshop.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Ordine implements Serializable {

	private static final long serialVersionUID = 563421827419925639L;

	private int id;
	private Timestamp dataOra;
	private double prezzo;
	private String stato;
	private int idUtente;
	private int idMetodoPagamento;
	private int idMetodoSpedizione;
	private int idIndirizzo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDataOra() {
		return dataOra;
	}

	public void setDataOra(Timestamp dataOra) {
		this.dataOra = dataOra;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdMetodoPagamento() {
		return idMetodoPagamento;
	}

	public void setIdMetodoPagamento(int idMetodoPagamento) {
		this.idMetodoPagamento = idMetodoPagamento;
	}

	public int getIdMetodoSpedizione() {
		return idMetodoSpedizione;
	}

	public void setIdMetodoSpedizione(int idMetodoSpedizione) {
		this.idMetodoSpedizione = idMetodoSpedizione;
	}

	public int getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
}
