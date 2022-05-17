package com.example.smartcash.models.views;

public class TotalView {

    private Double totalPagamento;
    private Double totalRecebimento;

    public TotalView(Double totalPagamento, Double totalRecebimento) {
        this.totalPagamento = totalPagamento;
        this.totalRecebimento = totalRecebimento;
    }

    public TotalView() {
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
        return "TotalView{" +
                "totalPagamento=" + totalPagamento +
                ", totalRecebimento=" + totalRecebimento +
                '}';
    }
}
