package co.com.konrad.bicired.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.StartActivity;
import co.com.konrad.bicired.logic.RespuestaDaoLogin;
import co.com.konrad.bicired.logic.UsuarioDao;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class profileActivity extends AppCompatActivity {
private EditText nombre_pe,correo_pe,genero_pe;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle BackStart) {

        super.onCreate(BackStart);
        setContentView(R.layout.activity_profile);

        nombre_pe = (EditText) findViewById(R.id.nombre_profile);
        correo_pe = (EditText) findViewById(R.id.correo_profile);
        genero_pe = (EditText) findViewById(R.id.genero_pe);
        Intent myIntent = getIntent();
        String datos = myIntent.getStringExtra(Constants.PREFERENCE_USER);
        Gson gson = new Gson();
        UsuarioDao user = gson.fromJson(datos , UsuarioDao.class);
        Log.d(Constants.TAG_LOG,user.getCorreo());
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            String parametros = "?funcion=datos_perfil&correo="+user.getCorreo();
            Request request = new Request.Builder()
                    .url(Constants.URL_LOGIN+parametros) // The URL to send the data to
                    .get()
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    profileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mostrarError();
                        }

                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()) {
                        final String data = response.body().string();
                        profileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();

                                RespuestaDaoLogin respuesta = gson.fromJson(data , RespuestaDaoLogin.class);

                                if(respuesta.getCodigo() == Constants.SERVICES_OK){
                                    Gson gson2 = new Gson();
                                    UsuarioDao user = gson2.fromJson(respuesta.getDatos().toString() , UsuarioDao.class);
                                    nombre_pe.setText(user.getNombre());
                                    if(user.getGenero().equals("M")){
                                        genero_pe.setText("Hombre");
                                    }else if(user.getGenero().equals("F")){
                                        genero_pe.setText("Mujer");
                                    }
                                    correo_pe.setText(user.getCorreo());
                                }else{
                                    mostrarError(respuesta.getMensaje());
                                }

                            }
                        });
                    }else{

                    }
                }
            });



    }
    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
    public void mostrarError(String mensaje){
        Utils.mostrarAlerta(this , mensaje);
    }

}





