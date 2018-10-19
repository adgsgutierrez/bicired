package co.com.konrad.bicired.view;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.StartActivity;
import co.com.konrad.bicired.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class profileActivity extends AppCompatActivity {

    Button volverInicio;
    Button guardarPerfil;
    private EditText nameComplete;
    private EditText email;
    private EditText cellphone;
    private Button Buttonsave;
    private Button Buttoncancel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle BackStart) {

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

    public void onProfile(View v) {
        String usuario = this.nameComplete.getText().toString();
        String email = this.email.getText().toString();
        String cellphone = this.cellphone.getText().toString();


        if (!usuario.equals("") && !usuario.equals("")) {

            this.Buttonsave.setVisibility(View.VISIBLE);
            this.Buttoncancel.setVisibility(View.VISIBLE);


            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add("namecomplete", usuario)
                    .add("email", email)
                    .add("cellphone", cellphone)

                    .build();
            Request request = new Request.Builder()
                    .url(Constants.URL_LOGIN) // The URL to send the data to
                    .post(formBody)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    profileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nameComplete.setVisibility(View.VISIBLE);
                        }

                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });

        }
    }
}





