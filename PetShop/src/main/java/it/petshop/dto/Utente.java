package it.petshop.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nomeUtente;
	private String password;
	private String nome;
	private String cognome;
	private Timestamp dataRegistrazione;
	private String email;
	private String telefono;

	public Utente() {
		
	}
	
	public Utente (int id) {
		this.id = id;
	}
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Timestamp getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
