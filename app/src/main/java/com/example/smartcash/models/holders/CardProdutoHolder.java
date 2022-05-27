package com.example.smartcash.models.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;

public class CardProdutoHolder extends RecyclerView.ViewHolder {
    public TextView txtNomeProduto, txtCodigoProduto, txtValorProduto;

    public CardProdutoHolder(View itemView){
        super(itemView);
        txtNomeProduto = (TextView) itemView.findViewById(R.id.txtNomeProduto);
        txtCodigoProduto = (TextView) itemView.findViewById(R.id.txtCodigoProduto);
        txtValorProduto = (TextView) itemView.findViewById(R.id.txtValorProduto);
    }
}
