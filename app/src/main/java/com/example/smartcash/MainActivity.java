package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final MediaType mediaType = MediaType.parse("application/json");
    Button btnRegister, btnSair, btnLogin;
    TextView editTxtLogin, editTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister = findViewById(R.id.btnRegister);
        btnSair = findViewById(R.id.btnSair);
        btnLogin = findViewById(R.id.btnLogin);
        editTxtLogin = findViewById(R.id.editTxtLogin);
        editTxtPassword = findViewById(R.id.editTxtPassword);
    }

    public void btnAbrirCadastrar(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void AbrirHome(){
        Intent intent = new Intent(this, HomePadraoActivity.class);
        startActivity(intent);
    }

    public void btnVoltarClick(View view){ finish();}

    public void btnLogin(View view){
        loginUsuario();
    }

    public void loginUsuario(){
        String email = editTxtLogin.getText().toString();
        String senha = editTxtPassword.getText().toString();

        if(!email.isEmpty()
            && !senha.isEmpty()
            && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("senha", senha);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

            Request request = new Request.Builder()
                    .url("https://smartcash-engine.herokuapp.com/engine/v1/login")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb2FvQGdtYWlsLmNvbSIsImV4cCI6MTY1MjU3MTAwNH0.qblz3ttQIh3qiJSSE61fqupUwD9elejcdgfMWWlBfyvIbzIu0-Gxts4B4dCFuKLPG6xCUIfahRDaSp79ooobzQ")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this,
                            "Tente Novamente. Servidor fora do ar.", Toast.LENGTH_LONG).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this,
                                "Seja bem vindo!", Toast.LENGTH_LONG).show());
                        AbrirHome();
                    } else if (response.code() == 403) {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this,
                                "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                    }
                }
            });
        }
//        else if(!senha.equals(confirmarSenha)){
//            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
//                    "Senhas Incompativeis.", Toast.LENGTH_LONG).show());
//        }

    } //Fim do metodo
}
