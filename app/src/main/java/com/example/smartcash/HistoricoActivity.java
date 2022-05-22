package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.smartcash.models.adapters.HistoricoAdapter;
import com.example.smartcash.models.domain.Nota;
import com.example.smartcash.models.dtos.NotaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoricoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private RecyclerView listaHistorico;
    private HistoricoAdapter historicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        listaHistorico = (RecyclerView) findViewById(R.id.listaHistorico);

        try {
            setRecycler();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setRecycler() throws JsonProcessingException {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        BuscarHistorico();

        String json = prefs.getString("listaHistorico","");

        historicoAdapter = new HistoricoAdapter(converterParaNotaDto(json));
        listaHistorico.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaHistorico.setLayoutManager(layoutManager);
        listaHistorico.setAdapter(historicoAdapter);
    }

    public void BuscarHistorico(){
        prefs = HistoricoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        edit = prefs.edit();

        String token = prefs.getString("token","");
        String email = prefs.getString("email","");


        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/atividade/6?start=2022%2F05%2F01&end=2022%2F05%2F30") //aq vou ter q substituir
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
                edit.putString("listaHistorico", response.body().string());
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