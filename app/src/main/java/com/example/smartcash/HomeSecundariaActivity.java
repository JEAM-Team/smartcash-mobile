package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcash.models.dtos.CalculaResultadoDto;

import com.example.smartcash.models.enums.TipoCarteira;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeSecundariaActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");

    TextView txtSaldoCarteiraPessoal, txtPagamentoCarteiraPessoal, txtUsuario;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_secundaria);
        Button btn = (Button) findViewById(R.id.btnCarteiraProf);
        Button btnSaldoPessoal = (Button) findViewById(R.id.btnSaldoPessoal);
        Button btnPagamentosPessoal = (Button) findViewById(R.id.btnPagamentosPessoal);
        Button btnHistoricoPessoal = (Button) findViewById(R.id.btnHistoricoPessoal);
        txtSaldoCarteiraPessoal = findViewById(R.id.txtSaldoCarteiraPessoal);
        txtPagamentoCarteiraPessoal = findViewById(R.id.txtPagamentoCarteiraPessoal);
        txtUsuario = findViewById(R.id.txtUsuario);
        chamada();
    }

    public void chamada() {
        prefs = HomeSecundariaActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        String email = prefs.getString("email","");

        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/nota/total?saldo_pessoal=true&saldo_comercial=false&pagamento_pessoal=true&pagamento_comercial=false")
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
            public void onResponse(Call call, Response response) {
                try {
                    CalculaResultadoDto total = new Gson().fromJson(response.body().string(), CalculaResultadoDto.class);
                    txtSaldoCarteiraPessoal.setText("R$"+total.getTotalPessoal().getTotalSaldo());
                    txtPagamentoCarteiraPessoal.setText("R$"+total.getTotalPessoal().getTotalPagamento());
                    txtUsuario.setText(email);
                }catch (RuntimeException | IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void criarCarteiraComercial(){
        prefs = HomeSecundariaActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        String email = prefs.getString("email","");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/carteira/comercial")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+ token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    HomeSecundariaActivity.this.runOnUiThread(() -> {
                        Intent intent = new Intent(HomeSecundariaActivity.this, HomePadraoActivity.class);
                        startActivity(intent);
                    });
                }catch (RuntimeException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void btnCarteiraProfissionalClick(View view) {
        criarCarteiraComercial();
    }

    public void btnAbrirHistoricoClick(View view){
        Intent intent = new Intent(this, HistoricoActivity.class);
        intent.putExtra("tipoCarteira", TipoCarteira.PESSOAL);
        startActivity(intent);
    }

    public void btnAbrirSaldoClick(View view){
        Intent intent = new Intent(this, SaldoActivity.class);
        intent.putExtra("tipoCarteira", TipoCarteira.PESSOAL);
        startActivity(intent);
    }

    public void btnAbrirPagamentoClick(View view){
        Intent intent = new Intent(this, PagamentoActivity.class);
        intent.putExtra("tipoCarteira", TipoCarteira.PESSOAL);
        startActivity(intent);
    }




    public void Sair() {
        finish();
    }

}