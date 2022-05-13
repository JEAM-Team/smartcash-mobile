package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


public class CadastroActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final MediaType mediaType = MediaType.parse("application/json");
    TextView editTxtName, editTxtLastName, editTxtCPF, editTxtEmail, editTxtPassword, editTxtConfirm_Password;
    Button bntCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editTxtName = findViewById(R.id.editTxtName);
        editTxtLastName = findViewById(R.id.editTxtLastName);
        editTxtCPF = findViewById(R.id.editTxtCPF);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        editTxtConfirm_Password = findViewById(R.id.editTxtConfirm_Password);
        bntCadastro = findViewById(R.id.bntCadastro);

    }

    public void btnCadastrar(View view) {
        cadastrarUsuario();
    }

    public void cadastrarUsuario() {
        String name = editTxtName.getText().toString();
        String lastName = editTxtLastName.getText().toString();
        String cpf = editTxtCPF.getText().toString();
        String email = editTxtEmail.getText().toString();
        String senha = editTxtPassword.getText().toString();
        String confirmarSenha = editTxtConfirm_Password.getText().toString();

        if(!name.isEmpty()
                && !lastName.isEmpty()
                && !email.isEmpty()
                && !cpf.isEmpty()
                && senha.equals(confirmarSenha)){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nome", name);
                jsonObject.put("sobrenome", lastName);
                jsonObject.put("cpf", cpf);
                jsonObject.put("email", email);
                jsonObject.put("senha", senha);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(String.valueOf(jsonObject), mediaType);

            Request request = new Request.Builder()
                    .url("https://smartcash-engine.herokuapp.com/engine/v1/usuario/cadastro")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                            "Tente Novamente. Servidor fora do ar.", Toast.LENGTH_LONG).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                                "Usuario cadastrado com Sucesso!", Toast.LENGTH_LONG).show());
                        Sair();
                    } else if (response.code() == 403) {
                        CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                                "Token expirado. Por favor tente novamente.", Toast.LENGTH_LONG).show());
                    }
                }
            });
        }else if(!senha.equals(confirmarSenha)){
            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                    "Senhas Incompativeis.", Toast.LENGTH_LONG).show());
        }else if(name.isEmpty()){
            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                    "Campo Sobre obrigatorio.", Toast.LENGTH_LONG).show());
        }else if(lastName.isEmpty()){
            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                    "Campo sobrenome obrigatorio.", Toast.LENGTH_LONG).show());
        }else if(email.isEmpty()){
            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                    "Campo email obrigatorio.", Toast.LENGTH_LONG).show());
        }else if(senha.isEmpty() || confirmarSenha.isEmpty()){
            CadastroActivity.this.runOnUiThread(() -> Toast.makeText(CadastroActivity.this,
                    "Campo senha/confirmar senha são obrigatorios.", Toast.LENGTH_LONG).show());
        }
    }

    public void Sair(){ finish();}
}