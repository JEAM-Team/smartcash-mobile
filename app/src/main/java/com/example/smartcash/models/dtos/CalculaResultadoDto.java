package com.example.smartcash.models.dtos;

public class CalculaResultadoDto {

    private Double totalPagamento;
    private Double totalRecebimento;

    public CalculaResultadoDto(Double totalPagamento, Double totalRecebimento) {
        this.totalPagamento = totalPagamento;
        this.totalRecebimento = totalRecebimento;
    }

    public CalculaResultadoDto() {
    }

    public Double getTotalPagamento() {
        return totalPagamento;
    }

    public void setTotalPagamento(Double totalPagamento) {
        this.totalPagamento = totalPagamento;
    }

    public Double getTotalRecebimento() {
        return totalRecebimento;
    }

    public void setTotalRecebimento(Double totalRecebimento) {
        this.totalRecebimento = totalRecebimento;
    }

    @Override
    public String toString() {
        return "CalculaResultadoDto{" +
                "totalPagamento=" + totalPagamento +
                ", totalRecebimento=" + totalRecebimento +
                '}';
    }
}
