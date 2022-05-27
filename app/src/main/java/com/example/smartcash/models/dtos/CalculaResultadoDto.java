package com.example.smartcash.models.dtos;

public class CalculaResultadoDto {

    private NotaTotalDto totalPessoal;
    private NotaTotalDto totalComercial;

    public CalculaResultadoDto() {
    }

    public CalculaResultadoDto(NotaTotalDto totalPessoal, NotaTotalDto totalComercial) {
        this.totalPessoal = totalPessoal;
        this.totalComercial = totalComercial;
    }

    public NotaTotalDto getTotalPessoal() {
        return totalPessoal;
    }

    public void setTotalPessoal(NotaTotalDto totalPessoal) {
        this.totalPessoal = totalPessoal;
    }

    public NotaTotalDto getTotalComercial() {
        return totalComercial;
    }

    public void setTotalComercial(NotaTotalDto totalComercial) {
        this.totalComercial = totalComercial;
    }

    @Override
    public String toString() {
        return "CalculaResultadoDto{" +
                "totalPessoal=" + totalPessoal +
                ", totalComercial=" + totalComercial +
                '}';
    }
}
