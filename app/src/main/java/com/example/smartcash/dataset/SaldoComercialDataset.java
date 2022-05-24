package com.example.smartcash.dataset;

import com.example.smartcash.models.dtos.NotaDto;

import java.util.ArrayList;
import java.util.List;

public class SaldoComercialDataset implements NotaDataset{
    private static List<NotaDto> saldos = new ArrayList<>();

    public static List<NotaDto> getSaldos() {
        return saldos;
    }

    public static void updateAll(List<NotaDto> updated){
        saldos.clear();
        saldos.addAll(updated);
    }

    public static void put(NotaDto nota) {
        saldos.add(nota);
    }
}
