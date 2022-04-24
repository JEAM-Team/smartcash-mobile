package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeSecundariaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_secundaria);
    }


    public void btnCarteiraProfissional(View view){
        Intent intent = new Intent(this, HomePadraoActivity.class);
        startActivity(intent);
    }
}