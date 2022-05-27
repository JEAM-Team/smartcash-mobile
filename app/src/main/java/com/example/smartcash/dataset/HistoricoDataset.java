package com.example.smartcash.dataset;

import com.example.smartcash.models.dtos.NotaDto;

import java.util.ArrayList;
import java.util.List;

public class HistoricoDataset implements NotaDataset{
    private static List<NotaDto> atividades = new ArrayList<>();

    public static List<NotaDto> getAtividades() {
        return atividades;
    }

    public static void updateAll(List<NotaDto> updated){
        atividades.clear();
        atividades.addAll(updated);
    }

    public static void put(NotaDto nota) {
        atividades.add(nota);
    }
}
