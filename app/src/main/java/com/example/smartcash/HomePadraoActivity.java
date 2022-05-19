package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartcash.models.dtos.CalculaResultadoDto;

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
    Button btnHistoricoPessoal, btnSaldoPessoal, btnPagamentosPessoal;
    TextView txtSaldoPessoal, txtPagamentoPessoal, txtSaldoProfissional, txtPagamentoProfissional, txtUsuario;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnHistoricoPessoal = findViewById(R.id.btnHistoricoPessoal);
        btnSaldoPessoal = findViewById(R.id.btnSaldoPessoal);
        btnPagamentosPessoal = findViewById(R.id.btnPagamentosPessoal);
        txtSaldoPessoal = findViewById(R.id.txtSaldoPessoal);
        txtPagamentoPessoal = findViewById(R.id.txtPagamentoPessoal);
        txtSaldoProfissional = findViewById(R.id.txtSaldoProfissional);
        txtPagamentoProfissional = findViewById(R.id.txtPagamentoProfissional);
        txtUsuario = findViewById(R.id.txtUsuario);
        chamada();

    }

    public void chamada() {
        prefs = HomePadraoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");

        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/nota/total?saldo_pessoal=true&saldo_comercial=true&pagamento_pessoal=true&pagamento_comercial=true")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("email", "")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    CalculaResultadoDto total = new Gson().fromJson(response.body().string(), CalculaResultadoDto.class);
                    txtSaldoPessoal.setText("R$" + total.getTotalPessoal().getTotalSaldo().toString());
                    txtPagamentoPessoal.setText("R$"+total.getTotalPessoal().getTotalPagamento().toString());
                    txtSaldoProfissional.setText("R$"+total.getTotalComercial().getTotalSaldo().toString());
                    txtPagamentoProfissional.setText("R$"+total.getTotalComercial().getTotalPagamento().toString());
                }catch (RuntimeException | IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void btnAbrirHistorico(View view) {
        Intent intent = new Intent(this, HistoricoActivity.class);
        startActivity(intent);
    }

    public void btnAbrirSaldo(View view) {
        Intent intent = new Intent(this, SaldoActivity.class);
        startActivity(intent);
    }

    public void btnAbrirPagamento(View view) {
        Intent intent = new Intent(this, PagamentoActivity.class);
        startActivity(intent);
    }

    public void Sair() {
        finish();
    }
}