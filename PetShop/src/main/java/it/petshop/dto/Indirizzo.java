package it.petshop.dto;

import java.io.Serializable;

public class Indirizzo implements Serializable {

	private static final long serialVersionUID = -7413258288344222169L;

	private int id;
	private String alias;
	private String via;
	private String numero;
	private String citta;
	private String codicePostale;
	private String provincia;
	private String paese;
	private int idUtente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCodicePostale() {
		return codicePostale;
	}

	public void setCodicePostale(String codicePostale) {
		this.codicePostale = codicePostale;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPaese() {
		return paese;
	}

	public void setPaese(String paese) {
		this.paese = paese;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

}
