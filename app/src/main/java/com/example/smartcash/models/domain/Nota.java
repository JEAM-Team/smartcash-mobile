package com.example.smartcash.models.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.smartcash.models.enums.TipoNota;
import com.example.smartcash.models.domain.Produto;
import com.example.smartcash.models.domain.Tag;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class Nota {

	private Long id;
	private String titulo;
	private Double valor;
	private Boolean repeticao;
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;
	private Integer qtdVezes;
	private TipoNota tipo;
	private Tag tag;
	private Produto produto;
	private Conta conta;
	private Carteira carteira;

	public Nota() {
	}

	public Nota(Long id, String titulo, Double valor, Boolean repeticao, LocalDate data, Integer qtdVezes, TipoNota tipo, Tag tag, Produto produto, Conta conta, Carteira carteira) {
		this.id = id;
		this.titulo = titulo;
		this.valor = valor;
		this.repeticao = repeticao;
		this.data = data;
		this.qtdVezes = qtdVezes;
		this.tipo = tipo;
		this.tag = tag;
		this.produto = produto;
		this.conta = conta;
		this.carteira = carteira;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean getRepeticao() {
		return repeticao;
	}

	public void setRepeticao(Boolean repeticao) {
		this.repeticao = repeticao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Integer getQtdVezes() {
		return qtdVezes;
	}

	public void setQtdVezes(Integer qtdVezes) {
		this.qtdVezes = qtdVezes;
	}

	public TipoNota getTipo() {
		return tipo;
	}

	public void setTipo(TipoNota tipo) {
		this.tipo = tipo;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	@Override
	public String toString() {
		return "Nota{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", valor=" + valor +
				", repeticao=" + repeticao +
				", data=" + data +
				", qtdVezes=" + qtdVezes +
				", tipo=" + tipo +
				", tag=" + tag +
				", produto=" + produto +
				", conta=" + conta +
				", carteira=" + carteira +
				'}';
	}
}