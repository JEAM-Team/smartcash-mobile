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

import com.example.smartcash.dataset.PagamentoComercialDataset;
import com.example.smartcash.dataset.PagamentoPessoalDataset;
import com.example.smartcash.dataset.SaldoComercialDataset;
import com.example.smartcash.dataset.SaldoPessoalDataset;
import com.example.smartcash.models.adapters.PagamentoAdapter;
import com.example.smartcash.models.adapters.SaldoAdapter;
import com.example.smartcash.models.domain.Nota;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.TipoCarteira;
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


public class PagamentoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    private FloatingActionButton floatingActionButton;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private RecyclerView listaPagamentos;
    private PagamentoAdapter pagamentoAdapter;

    private TipoCarteira tipoCarteira;
    private NotaDto addNota;


    @Override
    protected void onResume() {
        super.onResume();
        buscarPagamentos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tipoCarteira = (TipoCarteira) extras.get("tipoCarteira");
            addNota = (NotaDto) extras.get("pagamento");

        }

        listaPagamentos = (RecyclerView) findViewById(R.id.listaPagamentos);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        try {
            buscarPagamentos();
            setRecycler();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setRecycler() throws JsonProcessingException {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        buscarPagamentos();

        switch (tipoCarteira) {
            case PESSOAL:
                pagamentoAdapter = new PagamentoAdapter(PagamentoPessoalDataset.getPagamentos());
                break;
            case COMERCIAL:
                pagamentoAdapter = new PagamentoAdapter(PagamentoComercialDataset.getPagamentos());
                break;
        }


        if(addNota != null){
            pagamentoAdapter.addItem(addNota);
        }
        listaPagamentos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaPagamentos.setLayoutManager(layoutManager);
        listaPagamentos.setAdapter(pagamentoAdapter);
    }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }


    public void buscarPagamentos(){
        prefs = PagamentoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        String token = prefs.getString("token","");
        String email = prefs.getString("email","");


        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/nota/busca?tipo_carteira="+tipoCarteira.name()+"&tipo_nota=PAGAMENTO")
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
                List<NotaDto> notas = converterParaNotaDto(response.body().string());
                runOnUiThread(() -> pagamentoAdapter.updated(tipoCarteira, notas));
            }
        });
    }

    public List<NotaDto> converterParaNotaDto(String json) throws JsonProcessingException {
            Nota[] notas = new ObjectMapper().readValue(json, Nota[].class);

        List<NotaDto> notasDto = new ArrayList<>();

        for (Nota nota : notas) {
            NotaDto notaDto = new NotaDto();

            notaDto.setTitulo(nota.getTitulo());
            notaDto.setValor(nota.getValor());
            notaDto.setData(nota.getData());

            notasDto.add(notaDto);
        }

        return notasDto;
    }

    public void btnAdicionarPagamento(View view){
        Intent intent = new  Intent(getApplicationContext(), AdicionarPagamentoActivity.class);
        intent.putExtra("tipoCarteira", tipoCarteira);
        startActivity(intent);
    }
}