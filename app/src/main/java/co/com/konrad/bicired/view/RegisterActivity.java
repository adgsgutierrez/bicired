package co.com.konrad.bicired.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.StartActivity;

public class RegisterActivity extends AppCompatActivity {

     Spinner Genero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Genero = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genero,android.R.layout.simple_spinner_item);

        Genero.setAdapter(adapter);
    }

    public void Register(View v){
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }

    public void Cancel (View v){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
