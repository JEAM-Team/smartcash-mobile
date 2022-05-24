package com.example.smartcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcash.dataset.HistoricoDataset;
import com.example.smartcash.models.adapters.HistoricoAdapter;
import com.example.smartcash.models.domain.Atividade;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.TipoCarteira;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoricoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private RecyclerView listaHistorico;
    private HistoricoAdapter historicoAdapter;

    private TipoCarteira tipoCarteira;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        listaHistorico = (RecyclerView) findViewById(R.id.listaHistorico);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tipoCarteira = (TipoCarteira) extras.get("tipoCarteira");
        }

        try {
            setRecycler();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        buscarHistorico();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setRecycler() throws JsonProcessingException {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        buscarHistorico();

        historicoAdapter = new HistoricoAdapter(HistoricoDataset.getAtividades());
        listaHistorico.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaHistorico.setLayoutManager(layoutManager);
        listaHistorico.setAdapter(historicoAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buscarHistorico() {
        prefs = HistoricoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        String token = prefs.getString("token", "");
        String email = prefs.getString("email", "");
        Long carteiraId = prefs.getLong("idCarteiraPessoal", 0L);

        LocalDate now = LocalDate.now();
        LocalDate start = now.minusYears(2);
        LocalDate end = now;

        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/atividade/" + carteiraId + "?start=" + start.toString() + "&end=" + end.toString()) //aq vou ter q substituir
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
                List<NotaDto> atividades = converterParaNotaDto(response.body().string());
                runOnUiThread(() -> {
                    historicoAdapter.updated(tipoCarteira, atividades);
                });
            }
        });
    }

    public List<NotaDto> converterParaNotaDto(String json) throws JsonProcessingException {
        Atividade[] historico = new ObjectMapper().readValue(json, Atividade[].class);

        List<NotaDto> historicoDto = new ArrayList<>();

        for (Atividade atividade : historico) {
            NotaDto notaDto = new NotaDto();

            notaDto.setTitulo(atividade.getNota().getTitulo());
            notaDto.setValor(atividade.getNota().getValor());
            notaDto.setData(atividade.getNota().getData());

            historicoDto.add(notaDto);
        }

        return historicoDto;
    }
}