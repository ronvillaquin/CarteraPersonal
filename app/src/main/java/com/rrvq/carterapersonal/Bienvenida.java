package com.rrvq.carterapersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);


    }

    public void btnCancelar(View vista) {
        finish();
    }

    public void btnCrear(View vista) {
        Intent intent = new Intent(this, Main2Activity_Categorias_add.class);
        startActivity(intent);
        finish();
    }
}
