package com.example.smartcash.models.dtos;

import com.example.smartcash.models.enums.TipoCarteira;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotaTotalFilterDto {

    @JsonProperty("tipo_carteira")
    private TipoCarteira tipoCarteira;
    private Boolean saldo;
    private Boolean pagamento;

    public NotaTotalFilterDto() {
    }

    public NotaTotalFilterDto(TipoCarteira tipoCarteira, Boolean saldo, Boolean pagamento) {
        this.tipoCarteira = tipoCarteira;
        this.saldo = saldo;
        this.pagamento = pagamento;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public void setTipoCarteira(TipoCarteira tipoCarteira) {
        this.tipoCarteira = tipoCarteira;
    }

    public Boolean getSaldo() {
        return saldo;
    }

    public void setSaldo(Boolean saldo) {
        this.saldo = saldo;
    }

    public Boolean getPagamento() {
        return pagamento;
    }

    public void setPagamento(Boolean pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        return "NotaTotalFilterDto{" +
                "tipoCarteira=" + tipoCarteira +
                ", saldo=" + saldo +
                ", pagamento=" + pagamento +
                '}';
    }
}
