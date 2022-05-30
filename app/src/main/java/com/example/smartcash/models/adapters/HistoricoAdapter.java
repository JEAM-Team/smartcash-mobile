package com.example.smartcash.models.adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;
import com.example.smartcash.dataset.HistoricoComercialDataset;
import com.example.smartcash.dataset.HistoricoDataset;
import com.example.smartcash.dataset.SaldoComercialDataset;
import com.example.smartcash.dataset.SaldoPessoalDataset;
import com.example.smartcash.helpers.DateTimeHelper;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.TipoCarteira;
import com.example.smartcash.models.enums.TipoNota;
import com.example.smartcash.models.holders.CardHistoricoHolder;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<CardHistoricoHolder> {
    private List<NotaDto> notas;

    public HistoricoAdapter(List<NotaDto> notas) {
        this.notas = notas;
    }

    @NonNull
    @Override
    public CardHistoricoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardHistoricoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_historico, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CardHistoricoHolder holder, int position) {
        NotaDto nota = notas.get(position);
        holder.txtDadoHistorico.setText(nota.getTitulo());
        int color = nota.getTipo().equals(TipoNota.RECEBIMENTO) ? Color.parseColor("#8BC34A") : Color.parseColor("#FF4646");
        holder.txtValorHistorico.setTextColor(color);
        holder.txtValorHistorico.setText(String.format("%s R$ %s",nota.getTipo().equals(TipoNota.RECEBIMENTO) ? "+" : "-", nota.getValor().toString()));
        holder.txtDataHistorico.setText(DateTimeHelper.toBrazilianFormat(nota.getData()));
    }

    public void updated(TipoCarteira tipo, List<NotaDto> n) {
        int initial = 0;
        switch (tipo) {
            case PESSOAL:
                initial = HistoricoDataset.getAtividades().size();
                HistoricoDataset.updateAll(n);
                break;
            case COMERCIAL:
                initial = HistoricoComercialDataset.getAtividades().size();
                HistoricoComercialDataset.updateAll(n);
                break;
        }
        notifyItemRangeInserted(initial, notas.size());
    }

    @Override
    public int getItemCount() {
        return notas != null ? notas.size() : 0;
    }
}
