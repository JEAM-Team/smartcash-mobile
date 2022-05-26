package com.example.smartcash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcash.models.domain.Carteira;
import com.example.smartcash.models.domain.Conta;
import com.example.smartcash.models.domain.Tag;
import com.example.smartcash.models.dtos.NotaDto;
import com.example.smartcash.models.enums.AppConstants;
import com.example.smartcash.models.enums.TipoCarteira;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdicionarPagamentoActivity extends AppCompatActivity {
    TextView editTxtTitulo, editTxtValor, editTxtData, editTxtVezes;
    Switch editTxtRepeticao;
    Spinner spinnerContaPagamento, spinnerTagPagamento;
    Button buttonPostPagamento;

    SharedPreferences prefs;

    ArrayAdapter<String> adapterContas;
    ArrayAdapter<String> adapterTags;

    Long contaId;
    Long tagId;
    Long carteiraId;

    TipoCarteira tipoCarteira;
    NotaDto notaDto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pagamento);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tipoCarteira = (TipoCarteira) extras.get("tipoCarteira");
        }

        editTxtTitulo = findViewById(R.id.txtTituloPagamento);
        editTxtValor = findViewById(R.id.txtValorPagamento);
        editTxtRepeticao = findViewById(R.id.switchRepetir);
        editTxtData = findViewById(R.id.txtDataVencimentoPagamento);
        editTxtVezes = findViewById(R.id.txtQtdRepeticoesPagamento);
        spinnerContaPagamento = findViewById(R.id.spinnerContaPagamento);
        spinnerTagPagamento = findViewById(R.id.spinnerTagPagamento);
        buttonPostPagamento = findViewById(R.id.btnAdicionarPagamento);

        getCarteira();
        buttonPostPagamento.setOnClickListener(view -> postPagamento());
    }

    private void getCarteira() {
        OkHttpClient client = new OkHttpClient();

        prefs = AdicionarPagamentoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        Request request = new Request.Builder()
                .url(AppConstants.BASE_URL.getName().concat("/carteira/busca?tipo=" + tipoCarteira.name()))
                .get()
                .addHeader("email", prefs.getString("email", ""))
                .addHeader("Authorization", "Bearer " + prefs.getString("token", ""))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AdicionarPagamentoActivity.this.runOnUiThread(() -> Toast.makeText(AdicionarPagamentoActivity.this,
                        "Tente Novamente. Servidor fora do ar.", Toast.LENGTH_LONG).show());
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Carteira carteira = new ObjectMapper().readValue(response.body().string(), Carteira[].class)[0];

                carteiraId = carteira.getId();

                List<Conta> contas = carteira.getContas();
                List<Tag> tags = carteira.getTags();

                List<String> contaNomes = contas.stream().map(conta -> conta.getNome()).collect(Collectors.toList());
                List<String> tagsNomes = tags.stream().map(tag -> tag.getTitulo()).collect(Collectors.toList());

                runOnUiThread(() -> {
                    adapterContas = new ArrayAdapter<String>(AdicionarPagamentoActivity.this, android.R.layout.simple_spinner_item, contaNomes);
                    adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerContaPagamento.setAdapter(adapterContas);
                    for (Conta conta : contas) {
                        if (conta.getNome().equals(spinnerContaPagamento.getSelectedItem().toString())) {
                            contaId = conta.getId();
                        }
                    }

                    adapterTags = new ArrayAdapter<String>(AdicionarPagamentoActivity.this, android.R.layout.simple_spinner_item, tagsNomes);
                    adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerTagPagamento.setAdapter(adapterTags);
                    for (Tag tag : tags) {
                        if (tag.getTitulo().equals(spinnerTagPagamento.getSelectedItem().toString())) {
                            tagId = tag.getId();
                        }
                    }
                });
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void postPagamento() {
        prefs = AdicionarPagamentoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        String titulo = editTxtTitulo.getText().toString();
        Double valor = Double.parseDouble(editTxtValor.getText().toString().replace(",", "."));
        Boolean repeticao = Boolean.parseBoolean(editTxtRepeticao.getText().toString());
        String data = editTxtData.getText().toString();
        Integer qtd_vezes = !repeticao || editTxtVezes.getText().toString().isEmpty() ? 0 : Integer.parseInt(editTxtVezes.getText().toString());
        String tipo = "PAGAMENTO";
        Long conta = contaId;
        Long tag = tagId;
        Long carteira = carteiraId;

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("titulo", titulo);
            jsonObject.put("valor", valor);
            jsonObject.put("repeticao", repeticao);
            jsonObject.put("data", data);
            jsonObject.put("qtd_vezes", qtd_vezes);
            jsonObject.put("tipo", tipo);
            jsonObject.put("valor", valor);
            jsonObject.put("conta_id", conta);
            jsonObject.put("tag_id", tag);
            jsonObject.put("carteira_id", carteira);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notaDto = new NotaDto();
        notaDto.setTitulo(titulo);
        notaDto.setValor(valor);
        String[] subdata = data.split("/");
        notaDto.setData(LocalDate.of(Integer.parseInt(subdata[2]), Integer.parseInt(subdata[1]), Integer.parseInt(subdata[0])));

        RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);
        Request request = new Request.Builder()
                .url(AppConstants.BASE_URL.getName().concat("/nota"))
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + prefs.getString("token", ""))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AdicionarPagamentoActivity.this.runOnUiThread(() -> Toast.makeText(AdicionarPagamentoActivity.this,
                        "Tente Novamente. Servidor fora do ar.", Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    finish();
                }
            }
        });
    }

    public void btnAbrirContaClick(View view){
        Intent intent = new Intent(this, ModalContaActivity.class);
        intent.putExtra("tipoCarteira", tipoCarteira);
        startActivity(intent);
    }

    public void btnAbrirTagClick(View view){
        Intent intent = new Intent(this, ModalTagActivity.class);
        intent.putExtra("tipoCarteira", tipoCarteira);
        startActivity(intent);
    }
}