package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcash.models.domain.Carteira;
import com.example.smartcash.models.domain.Nota;
import com.example.smartcash.models.domain.Usuario;
import com.example.smartcash.models.enums.AppConstants;
import com.example.smartcash.models.enums.TipoCarteira;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
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

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

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

    public void btnAbrirCadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void AbrirHome() {
        Intent intent = new Intent(this, SaldoActivity.class);
        startActivity(intent);
    }

    public void btnVoltarClick(View view) {
        finish();
    }

    public void btnLogin(View view) {
        loginUsuario();
    }

    public void loginUsuario() {
        String email = editTxtLogin.getText().toString();
        String senha = editTxtPassword.getText().toString();

        if (!email.isEmpty()
                && !senha.isEmpty()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("senha", senha);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

            Request request = new Request.Builder()
                    .url(AppConstants.BASE_URL.getName().concat("/login"))
                    .post(body)
                    .addHeader("Content-Type", "application/json")
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

                        prefs = MainActivity.this.getSharedPreferences("sm-pref", Context.MODE_PRIVATE);
                        edit = prefs.edit();
                        String token = response.body().string();
                        edit.putString("token", token);
                        edit.putString("email", email);
                        edit.commit();

                        Request request = new Request.Builder()
                                .url(AppConstants.BASE_URL.getName().concat("/usuario?email="+email))
                                .get()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("email", email)
                                .addHeader("Authorization", "Bearer " + token)
                                .build();

                        Response responseUser = client.newCall(request).execute();

                        Usuario usuario = new ObjectMapper().readValue(responseUser.body().string(), Usuario.class);

                        for(Carteira carteira : usuario.getCarteiras()){
                            switch(carteira.getTipo()){
                                case PESSOAL:
                                    edit.putLong("idCarteiraPessoal", carteira.getId());
                                    edit.commit();
                                    break;
                                case COMERCIAL:
                                    edit.putLong("idCarteiraProfissional", carteira.getId());
                                    edit.commit();
                                    break;
                            }
                        }

                        AbrirHome();
                    } else if (response.code() == 403) {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this,
                                "Usuario não encontrado, por favor cadastre-se.", Toast.LENGTH_LONG).show());
                    } else if (response.code() == 401) {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this,
                                "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                    }
                }
            });
        }
    }
}
