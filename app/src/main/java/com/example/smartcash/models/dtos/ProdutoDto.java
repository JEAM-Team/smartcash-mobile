package com.example.smartcash.models.dtos;


public class ProdutoDto {

    private String nome;

    private String codigo;

    private Double valor;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ProdutoDto(String nome, String codigo, Double valor) {
        this.nome = nome;
        this.codigo = codigo;
        this.valor = valor;
    }

    public ProdutoDto() {
    }

    @Override
    public String toString() {
        return "ProdutoDto{" +
                "nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", valor=" + valor +
                '}';
    }
}
