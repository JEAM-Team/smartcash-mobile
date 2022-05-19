package com.example.smartcash.models.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;

public class CardPagamentoHolder extends RecyclerView.ViewHolder{ //esse grande homem aq define os campos la da linda card_pagamento
    public TextView txtDadoPagamento, txtValorPagamento, txtDataPagamento;

    public CardPagamentoHolder(View itemView){
        super(itemView);
        txtDadoPagamento = (TextView) itemView.findViewById(R.id.txtDadoPagamento);
        txtValorPagamento = (TextView) itemView.findViewById(R.id.txtValorPagamento);
        txtDataPagamento = (TextView) itemView.findViewById(R.id.txtDataPagamento);
    }
}
