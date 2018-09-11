package co.com.konrad.bicired;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import co.com.konrad.bicired.view.News;
import co.com.konrad.bicired.view.RegisterActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ((EditText) findViewById(R.id.usuario)).setHintTextColor(getResources().getColor(R.color.colorAccent));
        ((EditText) findViewById(R.id.clave)).setHintTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void onLogin(View v){
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }
    public void onRegister(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
