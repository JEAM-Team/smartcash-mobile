package com.example.smartcash.dataset;

import com.example.smartcash.models.dtos.NotaDto;

import java.util.ArrayList;
import java.util.List;

public class PagamentoComercialDataset implements NotaDataset{
    private static List<NotaDto> pagamentos = new ArrayList<>();

    public static List<NotaDto> getPagamentos() {
        return pagamentos;
    }

    public static void updateAll(List<NotaDto> updated){
        pagamentos.clear();
        pagamentos.addAll(updated);
    }

    public static void put(NotaDto nota) {
        pagamentos.add(nota);
    }
}
