package co.com.konrad.bicired;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class profileActivity extends AppCompatActivity {

    Button volverInicio;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate (Bundle BackStart){

        super.onCreate(BackStart);
        setContentView(R.layout.activity_home);

        volverInicio = findViewById(R.id.btn1);

        volverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent volverInicio = new Intent(profileActivity.this, StartActivity.class);
                startActivity(volverInicio);
            }
        });


    }


}



