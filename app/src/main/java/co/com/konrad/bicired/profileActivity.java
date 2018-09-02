package co.com.konrad.bicired;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class profileActivity extends AppCompatActivity {

    Button volverInicio;
    Button BuscarUbicacion;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate (Bundle BackStart){

        super.onCreate(BackStart);
        setContentView(R.layout.activity_home);


        volverInicio = findViewById(R.id.btn1);
        BuscarUbicacion = findViewById(R.id.btn2);


        volverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent volverInicio = new Intent(profileActivity.this, StartActivity.class);
                startActivity(volverInicio);
            }
        }
        );
        BuscarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent BuscarUbicacion = new Intent(Intent.ACTION_VIEW);
                BuscarUbicacion.setData(Uri.parse("geo:0,0?z=4&q;=parques"));
                startActivity(BuscarUbicacion);
            }
        });
    }

}



