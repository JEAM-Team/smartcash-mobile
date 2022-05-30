package com.example.smartcash.models.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;
import com.example.smartcash.dataset.NotaDataset;
import com.example.smartcash.dataset.SaldoComercialDataset;
import com.example.smartcash.dataset.SaldoPessoalDataset;
import com.example.smartcash.helpers.DateTimeHelper;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.TipoCarteira;
import com.example.smartcash.models.holders.CardSaldoHolder;

import java.util.ArrayList;
import java.util.List;

public class SaldoAdapter extends RecyclerView.Adapter<CardSaldoHolder> {
    private List<NotaDto> notas;

    public SaldoAdapter(List<NotaDto> notas) {
        this.notas = notas != null ? notas : new ArrayList<>();
    }

    @NonNull
    @Override
    public CardSaldoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardSaldoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_saldo, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CardSaldoHolder holder, int position) {
        NotaDto nota = notas.get(position);
        holder.txtDadoSaldo.setText(nota.getTitulo());
        holder.txtValorSaldo.setText(String.format("R$ %s", nota.getValor().toString()));
        holder.txtDataSaldo.setText(DateTimeHelper.toBrazilianFormat(nota.getData()));
    }

    @Override
    public int getItemCount() {
        return notas != null ? notas.size() : 0;
    }

    public void updated(TipoCarteira tipo, List<NotaDto> n) {
        int initial = 0;
        switch (tipo){
            case PESSOAL:
                initial = SaldoPessoalDataset.getSaldos().size();
                SaldoPessoalDataset.updateAll(n);
                break;
            case COMERCIAL:
                initial = SaldoComercialDataset.getSaldos().size();
                SaldoComercialDataset.updateAll(n);
                break;
        }
        notifyItemRangeInserted(initial, notas.size());
    }

    public List<NotaDto> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDto> notas) {
        this.notas = notas;
    }
}
