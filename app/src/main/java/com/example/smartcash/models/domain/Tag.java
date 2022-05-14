package com.example.smartcash.models.domain;

public class Tag {

	private Long id;

	private String titulo;

	public Tag(Long id, String titulo) {
		this.id = id;
		this.titulo = titulo;
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

	@Override
	public String toString() {
		return "Tag{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				'}';
	}
}