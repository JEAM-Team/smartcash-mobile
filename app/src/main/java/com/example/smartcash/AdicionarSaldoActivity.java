package com.example.smartcash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import com.example.smartcash.models.enums.AppConstants;
import com.example.smartcash.models.enums.TipoCarteira;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AdicionarSaldoActivity extends AppCompatActivity {
    TextView editTxtTitulo, editTxtValor, editTxtData, editTxtVezes;
    Switch editTxtRepeticao;
    Spinner spinnerContaSaldo, spinnerTagSaldo;
    Button buttonPostSaldo;

    SharedPreferences prefs;

    ArrayAdapter<String> adapterContas;
    ArrayAdapter<String> adapterTags;

    Long contaId;
    Long tagId;
    Long carteiraId;

    TipoCarteira tipoCarteira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_saldo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tipoCarteira = (TipoCarteira) extras.get("tipoCarteira");
        }

        editTxtTitulo = findViewById(R.id.txtTituloSaldo);
        editTxtValor = findViewById(R.id.txtValorSaldo);
        editTxtRepeticao = findViewById(R.id.switchRepetirSaldo);
        editTxtData = findViewById(R.id.txtDataVencimentoSaldo);
        editTxtVezes = findViewById(R.id.txtQtdRepeticoesSaldo);
        spinnerContaSaldo = findViewById(R.id.spinnerContaSaldo);
        spinnerTagSaldo = findViewById(R.id.spinnerTagSaldo);
        buttonPostSaldo = findViewById(R.id.btnAdicionarSaldo);

        getCarteira();
        buttonPostSaldo.setOnClickListener(view -> postSaldo());


    }

    private void getCarteira() {
        OkHttpClient client = new OkHttpClient();

        prefs = AdicionarSaldoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        Request request = new Request.Builder()
                .url(AppConstants.BASE_URL.getName().concat("/carteira/busca?tipo=" + tipoCarteira.name()))
                .get()
                .addHeader("email", prefs.getString("email", ""))
                .addHeader("Authorization", "Bearer " + prefs.getString("token", ""))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AdicionarSaldoActivity.this.runOnUiThread(() -> Toast.makeText(AdicionarSaldoActivity.this,
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
                    adapterContas = new ArrayAdapter<String>(AdicionarSaldoActivity.this, android.R.layout.simple_spinner_item, contaNomes);
                    adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerContaSaldo.setAdapter(adapterContas);
                    for (Conta conta : contas) {
                        if (conta.getNome().equals(spinnerContaSaldo.getSelectedItem().toString())) {
                            contaId = conta.getId();
                        }
                    }

                    adapterTags = new ArrayAdapter<String>(AdicionarSaldoActivity.this, android.R.layout.simple_spinner_item, tagsNomes);
                    adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerTagSaldo.setAdapter(adapterTags);
                    for (Tag tag : tags) {
                        if (tag.getTitulo().equals(spinnerTagSaldo.getSelectedItem().toString())) {
                            tagId = tag.getId();
                        }
                    }
                });
            }
        });
    }

    private void postSaldo() {
        prefs = AdicionarSaldoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);

        String titulo = editTxtTitulo.getText().toString();
        Double valor = Double.parseDouble(editTxtValor.getText().toString().replace(",", "."));
        Boolean repeticao = Boolean.parseBoolean(editTxtRepeticao.getText().toString());
        String data = editTxtData.getText().toString();
        Integer qtd_vezes = !repeticao || editTxtVezes.getText().toString().isEmpty() ? 0 : Integer.parseInt(editTxtVezes.getText().toString());
        String tipo = "RECEBIMENTO";
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
                AdicionarSaldoActivity.this.runOnUiThread(() -> Toast.makeText(AdicionarSaldoActivity.this,
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

}