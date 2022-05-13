package com.example.smartcash.models.domain;

import com.example.smartcash.models.domain.Nota;

public class Atividade {

	private Long id;
	private Nota nota;
	private Carteira carteira;

	@Override
	public String toString() {
		return "Atividade{" +
				"id=" + id +
				", nota=" + nota +
				", carteira=" + carteira +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public Atividade(Long id, Nota nota, Carteira carteira) {
		this.id = id;
		this.nota = nota;
		this.carteira = carteira;
	}

	public Atividade() {
	}
}