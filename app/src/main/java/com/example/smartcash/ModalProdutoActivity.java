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

public class ModalProdutoActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json");
    TextView txtNome3, editTxtValor3, editTxtCodigo, editTxtDecricao;
    Button btnAdicionarConta3;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_produto);
        txtNome3 = findViewById(R.id.txtNome3);
        editTxtValor3 = findViewById(R.id.editTxtValor3);
        editTxtCodigo = findViewById(R.id.editTxtCodigo);
        editTxtDecricao = findViewById(R.id.editTxtDescricao);
        btnAdicionarConta3 = findViewById(R.id.btnAdicionarConta3);

    }

    public void btnSair(View view) {
        finish();
    }

    public void btnEnvioProduto(View view) {
        enviarProduto();
    }

    public void enviarProduto() {
        prefs = ModalProdutoActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        String email = prefs.getString("email", "");
        Long carteiraId = prefs.getLong("idCarteiraProfissional", 0L);


        String nomeProduto = txtNome3.getText().toString();
        String codigoProduto = editTxtCodigo.getText().toString();
        String descricaoProduto = editTxtDecricao.getText().toString();
        String valorProduto = editTxtValor3.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome", nomeProduto);
            jsonObject.put("codigo", codigoProduto);
            jsonObject.put("descricao", descricaoProduto);
            jsonObject.put("valor", valorProduto);
            jsonObject.put("carteira_id", carteiraId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

        Request request = new Request.Builder()
                .url("https://smartcash-engine.herokuapp.com/engine/v1/produto")
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
                    ModalProdutoActivity.this.runOnUiThread(() -> Toast.makeText(ModalProdutoActivity.this,
                            "Produto enviada com Sucesso!", Toast.LENGTH_LONG).show());
                    finish();
                } else if (response.code() == 403) {
                    ModalProdutoActivity.this.runOnUiThread(() -> Toast.makeText(ModalProdutoActivity.this,
                            "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}