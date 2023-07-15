package it.petshop.dto;

import java.io.Serializable;
import java.util.Objects;

public class Prodotto implements Serializable {

	private static final long serialVersionUID = 3726657101957480832L;

	private int id;
	private String nome;
	private String descrizione;
	private double prezzo;
	private String immagine;
	private int inMagazzino;
	private int idCategoria;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public int getInMagazzino() {
		return inMagazzino;
	}

	public void setInMagazzino(int inMagazzino) {
		this.inMagazzino = inMagazzino;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Prodotto prodotto = (Prodotto) o;
		return id == prodotto.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
