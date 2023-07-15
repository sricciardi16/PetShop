package it.petshop.dto;

import java.io.Serializable;

public class Amministratore implements Serializable {

	private static final long serialVersionUID = 3757754963470549015L;

	private int id;
	private String nomeUtente;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
