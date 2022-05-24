package com.example.smartcash.models.dtos;

import com.example.smartcash.models.enums.TipoCarteira;

public class NotaTotalDto {

    private TipoCarteira tipoCarteira;
    private Double totalSaldo;
    private Double totalPagamento;

    public NotaTotalDto() {
    }

    public NotaTotalDto(TipoCarteira tipoCarteira, Double totalSaldo, Double totalPagamento) {
        this.tipoCarteira = tipoCarteira;
        this.totalSaldo = totalSaldo;
        this.totalPagamento = totalPagamento;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public void setTipoCarteira(TipoCarteira tipoCarteira) {
        this.tipoCarteira = tipoCarteira;
    }

    public Double getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public Double getTotalPagamento() {
        return totalPagamento;
    }

    public void setTotalPagamento(Double totalPagamento) {
        this.totalPagamento = totalPagamento;
    }

    @Override
    public String toString() {
        return "NotaTotal{" +
                "tipoCarteira=" + tipoCarteira +
                ", totalSaldo=" + totalSaldo +
                ", totalPagamento=" + totalPagamento +
                '}';
    }
}
