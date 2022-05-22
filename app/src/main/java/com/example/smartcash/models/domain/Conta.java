package com.example.smartcash.models.domain;

import com.example.smartcash.models.enums.TipoConta;

import java.util.List;

public class Conta {

	private Long id;
	private String nome;
	private Double valorTotal;
	private List<Nota> notas;
	private TipoConta tipo_conta;
	private Carteira carteira;

	public Conta() {
	}

	public Conta(Long id, String nome, Double valorTotal, List<Nota> notas, TipoConta tipo_conta, Carteira carteira) {
		this.id = id;
		this.nome = nome;
		this.valorTotal = valorTotal;
		this.notas = notas;
		this.tipo_conta = tipo_conta;
		this.carteira = carteira;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public TipoConta getTipo_conta() {
		return tipo_conta;
	}

	public void setTipo_conta(TipoConta tipo_conta) {
		this.tipo_conta = tipo_conta;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	@Override
	public String toString() {
		return "Conta{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", valorTotal=" + valorTotal +
				", notas=" + notas +
				", tipo_conta=" + tipo_conta +
				", carteira=" + carteira +
				'}';
	}
}