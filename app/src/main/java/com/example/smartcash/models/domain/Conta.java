package com.example.smartcash.models.domain;

import com.example.smartcash.models.domain.Carteira;
import com.example.smartcash.models.enums.TipoConta;

public class Conta {

	private Long id;

	private String nome;

	private Double valorTotal;

	private TipoConta tipoConta;

	private Carteira carteira;

	public Conta() {
	}

	public Conta(Long id, String nome, Double valorTotal, TipoConta tipoConta, Carteira carteira) {
		this.id = id;
		this.nome = nome;
		this.valorTotal = valorTotal;
		this.tipoConta = tipoConta;
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

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
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
				", tipoConta=" + tipoConta +
				", carteira=" + carteira +
				'}';
	}
}