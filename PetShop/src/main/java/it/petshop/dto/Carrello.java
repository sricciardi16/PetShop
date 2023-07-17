package it.petshop.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Prodotto, Integer> prodotti;
	private int numeroProdotti;
	private double totale;

	public Carrello() {
		prodotti = new LinkedHashMap<>();
		numeroProdotti = 0;
		totale = 0.0;
	}

	public Map<Prodotto, Integer> getProdotti() {
		return prodotti;
	}

	public void setProdotti(Map<Prodotto, Integer> prodotti) {
		this.prodotti = prodotti;
	}

	public int getNumeroProdotti() {
		return numeroProdotti;
	}

	public void setNumeroProdotti(int numeroProdotti) {
		this.numeroProdotti = numeroProdotti;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}
}
