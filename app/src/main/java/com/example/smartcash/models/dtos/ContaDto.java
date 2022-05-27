package com.example.smartcash.models.dtos;

import com.example.smartcash.models.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContaDto {
    private String nome;
    private Double totalValor;
    @JsonProperty("tipo_conta")
    private TipoConta tipo;
    @JsonProperty("carteira_id")
    private Long carteiraId;

    public ContaDto() {
    }

    public ContaDto(String nome, Double totalValor, TipoConta tipo, Long carteiraId) {
        this.nome = nome;
        this.totalValor = totalValor;
        this.tipo = tipo;
        this.carteiraId = carteiraId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTotalValor() {
        return totalValor;
    }

    public void setTotalValor(Double totalValor) {
        this.totalValor = totalValor;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Long getCarteiraId() {
        return carteiraId;
    }

    public void setCarteiraId(Long carteiraId) {
        this.carteiraId = carteiraId;
    }

    @Override
    public String toString() {
        return "ContaDto{" +
                "nome='" + nome + '\'' +
                ", totalValor=" + totalValor +
                ", tipo=" + tipo +
                ", carteiraId=" + carteiraId +
                '}';
    }
}
