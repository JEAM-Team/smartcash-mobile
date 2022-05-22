package com.example.smartcash.models.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.holders.CardSaldoHolder;

import java.util.List;

public class SaldoAdapter extends RecyclerView.Adapter<CardSaldoHolder> {
    private List<NotaDto> notas;

    public SaldoAdapter(List<NotaDto> notas) {this.notas = notas;}

    @NonNull
    @Override
    public CardSaldoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardSaldoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_saldo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardSaldoHolder holder, int position) {
        holder.txtDadoSaldo.setText(notas.get(position).getTitulo());
        holder.txtValorSaldo.setText(notas.get(position).getValor().toString());
        holder.txtDataSaldo.setText(notas.get(position).getData().toString());
    }

    @Override
    public int getItemCount() {
        return notas != null ? notas.size() : 0;
    }
}
