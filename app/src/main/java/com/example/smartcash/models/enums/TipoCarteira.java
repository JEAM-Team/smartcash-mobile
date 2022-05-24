package com.example.smartcash.models.enums;


public enum TipoCarteira {

	PESSOAL("Pessoal"),
	COMERCIAL("Comercial");

	private final String nome;

	TipoCarteira(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "TipoCarteira{" +
				"nome='" + nome + '\'' +
				'}';
	}
}