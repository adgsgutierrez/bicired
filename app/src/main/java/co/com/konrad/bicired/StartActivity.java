package co.com.konrad.bicired;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.com.konrad.bicired.view.News;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onLogin(View v){
        Intent intent = new Intent(this, News.class);
        startActivity(intent);
    }
}
