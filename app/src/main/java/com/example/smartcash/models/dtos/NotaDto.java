package com.example.smartcash.models.dtos;

import java.time.LocalDate;

public class NotaDto {

    private String titulo;

    private Double valor;

    private LocalDate data;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public NotaDto() {
    }

    public NotaDto(String titulo, Double valor, LocalDate data) {
        this.titulo = titulo;
        this.valor = valor;
        this.data = data;
    }

    @Override
    public String toString() {
        return "NotaDto{" +
                "titulo='" + titulo + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}
