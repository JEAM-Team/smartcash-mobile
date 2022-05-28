package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcash.models.domain.Carteira;
import com.example.smartcash.models.enums.TipoCarteira;
import com.example.smartcash.models.enums.TipoConta;
import com.fasterxml.jackson.databind.ObjectMapper;

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

public class ModalContaActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    TextView txtNome4, editTxtValor3;
    Button btnAdionarConta, btnCancelar;
    Spinner dropTipoConta;

    TipoCarteira tipoCarteira;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_conta);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tipoCarteira = (TipoCarteira) extras.get("tipoCarteira");
        }

        btnAdionarConta = findViewById(R.id.btnAdicionarConta3);
        btnCancelar = findViewById(R.id.btnCancelar3);
        txtNome4 = findViewById(R.id.txtNome3);
        editTxtValor3 = findViewById(R.id.editTxtValor3);
        dropTipoConta = findViewById(R.id.dropTipoConta);

        ArrayAdapter conta = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TipoConta.values());

        dropTipoConta.setAdapter(conta);
    }

    public void btnSair(View view){
        finish();
    }

    public void btnEnvioconta(View view) {
        enviarConta();
    }

    public void enviarConta() {
        prefs = ModalContaActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        String email = prefs.getString("email", "");

        Request requestById = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/carteira/busca?tipo=" + tipoCarteira.name())
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("email", email)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(requestById).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Long carteira = new ObjectMapper().readValue(response.body().string(), Carteira[].class)[0].getId();

                String nomeConta = txtNome4.getText().toString();
                String valorTotalConta = editTxtValor3.getText().toString();
                TipoConta tipoConta = TipoConta.tipoContaComValor(String.valueOf(dropTipoConta.getSelectedItem()));

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nome", nomeConta);
                    jsonObject.put("valorTotal", valorTotalConta);
                    jsonObject.put("tipo_conta", tipoConta.name());
                    jsonObject.put("carteira_id", carteira);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

                Request request = new Request.Builder()
                        .url("https://smartcash-engine.herokuapp.com/engine/v1/conta")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            ModalContaActivity.this.runOnUiThread(() -> Toast.makeText(ModalContaActivity.this,
                                    "Conta enviada com Sucesso!", Toast.LENGTH_LONG).show());
                            finish();

                        } else if (response.code() == 403) {
                            ModalContaActivity.this.runOnUiThread(() -> Toast.makeText(ModalContaActivity.this,
                                    "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                        }
                    }
                });
            }
        });
    }
}