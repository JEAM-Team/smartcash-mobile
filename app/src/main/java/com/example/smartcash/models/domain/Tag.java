package com.example.smartcash.models.domain;

public class Tag {

	private Long id;

	private String titulo;

	private Carteira carteira;

	public Tag() {
	}

	public Tag(Long id, String titulo, Carteira carteira) {
		this.id = id;
		this.titulo = titulo;
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

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	@Override
	public String toString() {
		return "Tag{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", carteira=" + carteira +
				'}';
	}
}