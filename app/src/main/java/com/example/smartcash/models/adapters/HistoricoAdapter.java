package com.example.smartcash.models.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.HistoricoActivity;
import com.example.smartcash.R;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.holders.CardHistoricoHolder;
import com.example.smartcash.models.holders.CardPagamentoHolder;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<CardHistoricoHolder> {
    private List<NotaDto> notas;

    public HistoricoAdapter(List<NotaDto> notas) {this.notas = notas;}

    @NonNull
    @Override
    public CardHistoricoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardHistoricoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_historico,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardHistoricoHolder holder, int position) {
        holder.txtDadoHistorico.setText(notas.get(position).getTitulo());
        holder.txtValorHistorico.setText(notas.get(position).getValor().toString());
        holder.txtDataHistorico.setText(notas.get(position).getData().toString());
    }

    @Override
    public int getItemCount() {
        return notas != null ? notas.size() : 0;
    }
}
