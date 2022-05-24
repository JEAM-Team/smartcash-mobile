package com.example.smartcash.models.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.R;
import com.example.smartcash.dataset.ProdutoDataset;
import com.example.smartcash.dataset.SaldoComercialDataset;
import com.example.smartcash.dataset.SaldoPessoalDataset;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.dtos.ProdutoDto;
import com.example.smartcash.models.enums.TipoCarteira;
import com.example.smartcash.models.holders.CardProdutoHolder;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<CardProdutoHolder> {
    private List<ProdutoDto> produtos;

    public ProdutoAdapter(List<ProdutoDto> produtos){this.produtos = produtos;}

    @NonNull
    @Override
    public CardProdutoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardProdutoHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.card_produto,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardProdutoHolder holder, int position) {
        holder.txtNomeProduto.setText(produtos.get(position).getNome());
        holder.txtCodigoProduto.setText(produtos.get(position).getCodigo());
        holder.txtValorProduto.setText(produtos.get(position).getValor().toString());
    }

    public void updated(List<ProdutoDto> n) {
        int initial = ProdutoDataset.getProdutos().size();
        ProdutoDataset.updateAll(n);
        notifyItemRangeInserted(initial, produtos.size());
    }

    @Override
    public int getItemCount() {
        return produtos != null ? produtos.size() : 0;
    }
}
