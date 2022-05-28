package com.example.smartcash.models.enums;

import android.content.res.Resources;

public enum TipoConta {

	CC("Conta Corrente"),
	CS("Conta Salário"),
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
		return nome;
	}

	public static TipoConta tipoContaComValor(String nome){
		for (TipoConta tipo: TipoConta.values()){
			if (tipo.getNome().equals(nome)){
				return tipo;
			}
		}
		throw new Resources.NotFoundException("Não pode ser encontrado nem uma conta com o valor: " + nome);
	}
}