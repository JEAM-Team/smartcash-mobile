package com.example.smartcash.models.domain;

import com.example.smartcash.models.domain.Carteira;

public class Produto {

	private Long id;

	private String nome;

	private String codigo;

	private String descricao;

	private Double valor;

	private Carteira carteira;

	public Produto(Long id, String nome, String codigo, String descricao, Double valor, Carteira carteira) {
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		this.descricao = descricao;
		this.valor = valor;
		this.carteira = carteira;
	}

	public Produto() {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	@Override
	public String toString() {
		return "Produto{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", codigo='" + codigo + '\'' +
				", descricao='" + descricao + '\'' +
				", valor=" + valor +
				", carteira=" + carteira +
				'}';
	}
}