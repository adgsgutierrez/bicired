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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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
private Spinner spinner;
private LinearLayout linearLayout;
private Button btn_editar,btn_guardar,btn_cancelar;
private ProgressBar load;
private TextView textnombre,textcorreo,textgenero;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle BackStart) {

        super.onCreate(BackStart);
        setContentView(R.layout.activity_profile);
        textnombre = (TextView) findViewById(R.id.textnombre);
        textcorreo = (TextView) findViewById(R.id.textcorreo);
        textgenero = (TextView) findViewById(R.id.textgenero);
        load = (ProgressBar) findViewById(R.id.cargandoSpiner);
        linearLayout = (LinearLayout) findViewById(R.id.caja_botones);
        btn_editar = (Button) findViewById(R.id.editar_perfil);
       spinner = (Spinner) findViewById(R.id.spinner_per);
        nombre_pe = (EditText) findViewById(R.id.nombre_profile);
        correo_pe = (EditText) findViewById(R.id.correo_profile);
        genero_pe = (EditText) findViewById(R.id.genero_pe);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genero_pe,android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
                spinner.setEnabled(false);
        serviciomostrar(0);
    }

    public void Editar_pe(View v){
   linearLayout.setVisibility(View.VISIBLE);
   btn_editar.setVisibility(View.GONE);
   spinner.setEnabled(true);
   nombre_pe.setEnabled(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                if (item.toString().equals("Hombre")) {
                    genero_pe.setText("M");
                } else if (item.toString().equals("Mujer")) {
                    genero_pe.setText("F");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Cancel_pe(View v){
        linearLayout.setVisibility(View.GONE);
        btn_editar.setVisibility(View.VISIBLE);
        spinner.setEnabled(false);
        nombre_pe.setEnabled(false);
        correo_pe.setEnabled(false);
    }
    public void Guardar_pe(View v) {
        String correo = correo_pe.getText().toString();
        String nombre = nombre_pe.getText().toString();
        String genero = genero_pe.getText().toString();
        if (!nombre.equals("") && !correo.equals("")) {
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add("correo", correo)
                    .add("nombre", nombre)
                    .add("genero", genero)
                    .add("edad", "19")
                    .add("funcion", "acperfil")
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.URL_LOGIN) // The URL to send the data to
                    .put(formBody)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(Constants.TAG_LOG, "buenas");
                    profileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mostrarError();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    if (response.isSuccessful()) {
                        final String data = response.body().string();
                        Log.d(Constants.TAG_LOG, data);
                        profileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                RespuestaDaoLogin respuesta1 = gson.fromJson(data, RespuestaDaoLogin.class);
                                if (respuesta1.getCodigo() == Constants.SERVICES_OK) {
                                    serviciomostrar(1);


                                } else {
                                    mostrarError(respuesta1.getMensaje());
                                }

                            }
                        });
                    } else {

                        profileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mostrarError();
                            }
                        });
                    }
                }
            });
            linearLayout.setVisibility(View.GONE);
            btn_editar.setVisibility(View.VISIBLE);
            spinner.setEnabled(false);
            nombre_pe.setEnabled(false);
            correo_pe.setEnabled(false);
        }
        else{
            Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs3));
        }
    }


    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
    public void mostrarError(String mensaje){
        Utils.mostrarAlerta(this , mensaje);
    }

    public void serviciomostrar(int value){
        if(value!=1) {
            load.setVisibility(View.VISIBLE);
            textnombre.setVisibility(View.GONE);
            textgenero.setVisibility(View.GONE);
            textcorreo.setVisibility(View.GONE);
            nombre_pe.setVisibility(View.GONE);
            correo_pe.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            btn_editar.setVisibility(View.GONE);
        }
        Intent myIntent = getIntent();
        String datos = myIntent.getStringExtra(Constants.PREFERENCE_USER_DATA);
        Gson gson = new Gson();
        UsuarioDao user = gson.fromJson(datos , UsuarioDao.class);
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
                        load.setVisibility(View.GONE);
                        textnombre.setVisibility(View.VISIBLE);
                        textgenero.setVisibility(View.VISIBLE);
                        textcorreo.setVisibility(View.VISIBLE);
                        nombre_pe.setVisibility(View.VISIBLE);
                        correo_pe.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);
                        btn_editar.setVisibility(View.VISIBLE);
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
                                load.setVisibility(View.GONE);
                                textnombre.setVisibility(View.VISIBLE);
                                textgenero.setVisibility(View.VISIBLE);
                                textcorreo.setVisibility(View.VISIBLE);
                                nombre_pe.setVisibility(View.VISIBLE);
                                correo_pe.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.VISIBLE);
                                btn_editar.setVisibility(View.VISIBLE);
                                Gson gson2 = new Gson();
                                UsuarioDao user = gson2.fromJson(respuesta.getDatos().toString() , UsuarioDao.class);
                                nombre_pe.setText(user.getNombre());
                                correo_pe.setText(user.getCorreo());
                                if(user.getGenero().equals("M")){
                                    genero_pe.setText("M");
                                }else if(user.getGenero().equals("F")){
                                    genero_pe.setText("F");
                                }
                                if(user.getGenero().equals("M")){
                                    spinner.setSelection(0);
                                }
                                else if(user.getGenero().equals("F")){
                                    spinner.setSelection(1);
                                }

                            }else{
                                mostrarError(respuesta.getMensaje());
                            }

                        }
                    });
                }else{
                    load.setVisibility(View.GONE);
                    textnombre.setVisibility(View.VISIBLE);
                    textgenero.setVisibility(View.VISIBLE);
                    textcorreo.setVisibility(View.VISIBLE);
                    nombre_pe.setVisibility(View.VISIBLE);
                    correo_pe.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    btn_editar.setVisibility(View.VISIBLE);
                    mostrarError();
                }
            }
        });



    }

}





