package co.com.konrad.bicired.view;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.StartActivity;

public class profileActivity extends AppCompatActivity {

    Button volverInicio;
    Button guardarPerfil;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate (Bundle BackStart){

        super.onCreate(BackStart);
        setContentView(R.layout.activity_profile);


        volverInicio = findViewById(R.id.btn_perfil_volver);
        guardarPerfil = findViewById(R.id.btn_perfil_guardar);

        volverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverInicio = new Intent(profileActivity.this, News.class);
                startActivity(volverInicio);
            }
        });
        guardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverInicio = new Intent(profileActivity.this, News.class);
                startActivity(volverInicio);
            }
        });

    }

}



