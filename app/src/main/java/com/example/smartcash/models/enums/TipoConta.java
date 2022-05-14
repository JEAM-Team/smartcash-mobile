package com.example.smartcash.models.enums;

public enum TipoConta {

	CC("Conta Corrente"),
	CS("Conta salário"),
	CP("Conta Poupança"),
	DN("Dinheiro");

	private final String nome;

	TipoConta(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "TipoConta{" +
				"nome='" + nome + '\'' +
				'}';
	}
}