package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;

import com.example.smartcash.models.adapters.SaldoAdapter;
import com.example.smartcash.models.domain.Nota;
import com.example.smartcash.models.dtos.NotaDto;
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


public class SaldoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    private FloatingActionButton floatingActionButton;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private RecyclerView listaSaldo;
    private SaldoAdapter saldoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        listaSaldo = (RecyclerView) findViewById(R.id.listaSaldo);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        try {
            setRecycler();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void btnAbrirAdicionarSaldo(View view){
        Intent intent = new  Intent(getApplicationContext(), AdicionarSaldoActivity.class);
        startActivity(intent);
    }

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

    public void setRecycler() throws JsonProcessingException {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        BuscarSaldo();

        String json = prefs.getString("listaSaldo","");

        saldoAdapter = new SaldoAdapter(converterParaNotaDto(json));
        listaSaldo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaSaldo.setLayoutManager(layoutManager);
        listaSaldo.setAdapter(saldoAdapter);
    }

    public void BuscarSaldo(){
        prefs = SaldoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        edit = prefs.edit();

        String token = prefs.getString("token","");
        String email = prefs.getString("email","");


        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/nota/busca?tipo_carteira=PESSOAL&tipo_nota=RECEBIMENTO")
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
                edit.putString("listaSaldo", response.body().string());
                edit.commit();
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
}