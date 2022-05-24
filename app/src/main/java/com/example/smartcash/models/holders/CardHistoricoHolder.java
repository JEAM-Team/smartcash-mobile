package com.example.smartcash.models.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;

public class CardHistoricoHolder extends RecyclerView.ViewHolder {
    public TextView txtDadoHistorico, txtValorHistorico, txtDataHistorico;

    public CardHistoricoHolder(View itemView){
        super(itemView);
        txtDadoHistorico = (TextView) itemView.findViewById(R.id.txtDadoHistorico);
        txtValorHistorico = (TextView) itemView.findViewById(R.id.txtValorHistorico);
        txtDataHistorico = (TextView) itemView.findViewById(R.id.txtDataHistorico);
    }
}
