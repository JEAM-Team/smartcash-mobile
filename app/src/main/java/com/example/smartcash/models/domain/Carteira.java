package com.example.smartcash.models.domain;

import com.example.smartcash.models.enums.TipoCarteira;

public class Carteira {

	public Carteira() {
	}

	public Carteira(Long id, TipoCarteira tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	private Long id;
	private TipoCarteira tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoCarteira getTipo() {
		return tipo;
	}

	public void setTipo(TipoCarteira tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Carteira{" +
				"id=" + id +
				", tipo=" + tipo +
				'}';
	}
}