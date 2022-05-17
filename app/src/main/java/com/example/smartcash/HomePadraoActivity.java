package com.example.smartcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcash.models.views.TotalView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePadraoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    Button btnHistoricoPessoal,btnSaldoPessoal,btnPagamentosPessoal;
    TextView textView, textView3, textView9 , textView8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHistoricoPessoal = findViewById(R.id.btnHistoricoPessoal);
        btnSaldoPessoal = findViewById(R.id.btnSaldoPessoal);
        btnPagamentosPessoal = findViewById(R.id.btnPagamentosPessoal);
        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        textView9 = findViewById(R.id.textView9);
        textView8 = findViewById(R.id.textView8);
        chamada();

    }

    public void chamada(){
        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/nota/total")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure( Call call, IOException e) {
            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                  TotalView totalView = new Gson().fromJson(response.body().toString(), TotalView.class );

                  textView.setText(totalView.getTotalRecebimento().toString());
                  textView3.setText(totalView.getTotalPagamento().toString());
            }
        });
    }

    public void btnAbrirHistorico(View view){
        Intent intent = new Intent(this, HistoricoActivity.class);
        startActivity(intent);
    }

    public void btnAbrirSaldo(View view){
        Intent intent = new Intent(this, SaldoActivity.class);
        startActivity(intent);
    }

    public void btnAbrirPagamento(View view){
        Intent intent = new Intent(this, PagamentoActivity.class);
        startActivity(intent);
    }

    public void Sair(){ finish();}
}