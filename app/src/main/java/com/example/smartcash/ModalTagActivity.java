package com.example.smartcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcash.models.domain.Carteira;
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

public class ModalTagActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    TextView txtNome2;
    Button btnAdicionarConta2;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_tag);
        txtNome2 = findViewById(R.id.editTxtName3);
        btnAdicionarConta2 = findViewById(R.id.btnAdicionarConta2);
    }

    public void btnSair(View view){
        finish();
    }

    public void btnEnviarTag(View view) {
        enviarTag();
    }

    public void enviarTag() {
        prefs = ModalTagActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        String email = prefs.getString("email","");

        Request requestById = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/carteira/busca?tipo=PESSOAL")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("email", email)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(requestById).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Long carteira = new ObjectMapper().readValue(response.body().string(), Carteira[].class)[0].getId();

                String nomeTag = txtNome2.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("titulo", nomeTag);
                    jsonObject.put("carteira_id", carteira);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

                Request request = new Request.Builder()
                        .url("https://smartcash-engine.herokuapp.com/engine/v1/tag")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer "+token)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            ModalTagActivity.this.runOnUiThread(() -> Toast.makeText(ModalTagActivity.this,
                                    "Tag enviada com Sucesso!", Toast.LENGTH_LONG).show());
                            finish();
                        } else if (response.code() == 403) {
                            ModalTagActivity.this.runOnUiThread(() -> Toast.makeText(ModalTagActivity.this,
                                    "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                        }
                    }
                });
            }
        });
    }
}