package com.example.smartcash.models.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;
import com.example.smartcash.dataset.PagamentoComercialDataset;
import com.example.smartcash.dataset.PagamentoPessoalDataset;
import com.example.smartcash.dataset.SaldoComercialDataset;
import com.example.smartcash.dataset.SaldoPessoalDataset;
import com.example.smartcash.models.domain.Nota;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.TipoCarteira;
import com.example.smartcash.models.holders.CardPagamentoHolder;

import java.util.ArrayList;
import java.util.List;

public class PagamentoAdapter extends RecyclerView.Adapter<CardPagamentoHolder> {
    private List<NotaDto> notas;

    public PagamentoAdapter(List<NotaDto> notas) {this.notas = notas;}

    @NonNull
    @Override
    public CardPagamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardPagamentoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_pagamento,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardPagamentoHolder holder, int position) {
        holder.txtDadoPagamento.setText(notas.get(position).getTitulo());
        holder.txtValorPagamento.setText(notas.get(position).getValor().toString());
        holder.txtDataPagamento.setText(notas.get(position).getData().toString());
    }

    public void updated(TipoCarteira tipo, List<NotaDto> n) {
        int initial = 0;
        switch (tipo){
            case PESSOAL:
                initial = PagamentoPessoalDataset.getPagamentos().size();
                PagamentoPessoalDataset.updateAll(n);
                break;
            case COMERCIAL:
                initial = PagamentoComercialDataset.getPagamentos().size();
                PagamentoComercialDataset.updateAll(n);
                break;
        }
        notifyItemRangeInserted(initial, notas.size());
    }

    public void addItem(NotaDto notaDto){
        notas.add(notaDto);
        notifyItemInserted(getItemCount());
    }

    @Override
    public int getItemCount() {
        return notas != null ? notas.size() : 0;
    }
}
