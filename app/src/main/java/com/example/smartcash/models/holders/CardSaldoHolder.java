package com.example.smartcash.models.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;

public class CardSaldoHolder extends RecyclerView.ViewHolder{
    public TextView txtDadoSaldo, txtValorSaldo, txtDataSaldo;

    public CardSaldoHolder(View itemView){
        super(itemView);
        txtDadoSaldo = (TextView) itemView.findViewById(R.id.txtDadoSaldo);
        txtValorSaldo = (TextView) itemView.findViewById(R.id.txtValorSaldo);
        txtDataSaldo = (TextView) itemView.findViewById(R.id.txtDataSaldo);
    }
}
