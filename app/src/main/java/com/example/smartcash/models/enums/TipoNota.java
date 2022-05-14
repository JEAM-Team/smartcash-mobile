package com.example.smartcash.models.enums;

public enum TipoNota {

	PAGAMENTO("Pagamento"),
	RECEBIMENTO("Recebimento");

	private final String nome;

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "TipoNota{" +
				"nome='" + nome + '\'' +
				'}';
	}

	TipoNota(String nome) {
		this.nome = nome;
	}


}