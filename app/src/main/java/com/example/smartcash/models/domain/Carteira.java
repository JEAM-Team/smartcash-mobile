package com.example.smartcash.models.domain;

import com.example.smartcash.models.enums.TipoCarteira;

import java.util.List;

public class Carteira {

	private Long id;
	private TipoCarteira tipo;
	private List<Conta> contas;
	private List<Tag> tags;

	public Carteira() {
	}

	public Carteira(Long id, TipoCarteira tipo, List<Conta> contas, List<Tag> tags) {
		this.id = id;
		this.tipo = tipo;
		this.contas = contas;
		this.tags = tags;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

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
				", contas=" + contas +
				", tags=" + tags +
				'}';
	}
}