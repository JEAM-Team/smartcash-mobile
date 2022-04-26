package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeSecundariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_secundaria);
        Button btn = (Button) findViewById(R.id.btnCarteiraProf);
    }

    public void btnCarteiraProfissionalClick(View view){
        Intent intent = new Intent(this, HomePadraoActivity.class);
        startActivity(intent);
        Log.e("teste","teste");
    }

}