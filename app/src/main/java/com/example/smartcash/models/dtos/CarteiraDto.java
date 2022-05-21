package com.example.smartcash.models.dtos;

public class CarteiraDto {

    public CarteiraDto() {
    }

    public CarteiraDto(Long id) {
        this.id = id;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "id=" + id +
                '}';
    }
}
