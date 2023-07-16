package it.petshop.dto;

import java.io.Serializable;
import java.util.Objects;

public class MetodoPagamento implements Serializable {

	private static final long serialVersionUID = 1946934677241912933L;

	private int id;
	private String tipo;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MetodoPagamento that = (MetodoPagamento) o;
		return id == that.id && Objects.equals(tipo, that.tipo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipo);
	}
}
