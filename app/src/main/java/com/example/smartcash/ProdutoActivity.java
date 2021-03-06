package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.smartcash.dataset.ProdutoDataset;
import com.example.smartcash.models.adapters.ProdutoAdapter;
import com.example.smartcash.models.domain.Produto;
import com.example.smartcash.models.dtos.ProdutoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProdutoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    private FloatingActionButton floatingActionButton;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private RecyclerView listaProduto;
    private ProdutoAdapter produtoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        listaProduto = (RecyclerView) findViewById(R.id.listaProduto);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        try {
            setRecycler();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarProduto();
    }

    public void btnAbrirProduto(View view){
        Intent intent = new  Intent(getApplicationContext(), ModalProdutoActivity.class);
        startActivity(intent);
    }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

    public void setRecycler() throws JsonProcessingException {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        buscarProduto();

        produtoAdapter = new ProdutoAdapter(ProdutoDataset.getProdutos());
        listaProduto.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaProduto.setLayoutManager(layoutManager);
        listaProduto.setAdapter(produtoAdapter);
    }

    public void buscarProduto(){
        prefs = ProdutoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        String token = prefs.getString("token","");
        String email = prefs.getString("email","");
        Long idCarteira = prefs.getLong("idCarteiraProfissional",0L);


        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/produto/carteira/"+idCarteira)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("email", email)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<ProdutoDto> produtoDtos = converterParaProdutoDto(response.body().string());
                runOnUiThread(() -> produtoAdapter.updated(produtoDtos));
            }
        });
    }

    public List<ProdutoDto> converterParaProdutoDto(String json) throws JsonProcessingException {
        Produto[] produtos = new ObjectMapper().readValue(json, Produto[].class);

        List<ProdutoDto> produtosDto = new ArrayList<>();

        for (Produto produto : produtos) {
            ProdutoDto produtoDto = new ProdutoDto();

            produtoDto.setNome(produto.getNome());
            produtoDto.setCodigo(produto.getCodigo());
            produtoDto.setValor(produto.getValor());

            produtosDto.add(produtoDto);
        }

        return produtosDto;
    }
}