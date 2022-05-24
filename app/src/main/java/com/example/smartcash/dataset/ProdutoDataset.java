package com.example.smartcash.dataset;

import com.example.smartcash.models.domain.Produto;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.dtos.ProdutoDto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataset implements NotaDataset{
    private static List<ProdutoDto> produtos = new ArrayList<>();

    public static List<ProdutoDto> getProdutos() {
        return produtos;
    }

    public static void updateAll(List<ProdutoDto> updated){
        produtos.clear();
        produtos.addAll(updated);
    }

    public static void put(ProdutoDto produto) {
        produtos.add(produto);
    }
}
