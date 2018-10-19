package co.com.konrad.bicired;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import co.com.konrad.bicired.logic.RespuestaDaoLogin;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import co.com.konrad.bicired.view.News;
import co.com.konrad.bicired.view.RegisterActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

    private EditText correo;
    private EditText clave;
    private Button buttonLogin;
    private Button buttonRegistro;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.correo = ((EditText) findViewById(R.id.usuario));
        this.correo.setHintTextColor(getResources().getColor(R.color.colorAccent));
        this.clave = ((EditText) findViewById(R.id.clave));
        this.clave.setHintTextColor(getResources().getColor(R.color.colorAccent));
        this.buttonLogin = ((Button) findViewById(R.id.buttonLogin));
        this.buttonRegistro = ((Button) findViewById(R.id.buttonRegistro));
        this.spinner = ((ProgressBar) findViewById(R.id.cargandoSpiner));

    }

    public void onLogin(View v){
        String correo = this.correo.getText().toString();
        String clave = this.clave.getText().toString();
        if(!correo.equals("") && !clave.equals("")) {

            this.buttonRegistro.setVisibility(View.GONE);
            this.buttonLogin.setVisibility(View.GONE);
            this.spinner.setVisibility(View.VISIBLE);

            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                    .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                    .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add("correo", correo)
                    .add("clave", clave)
                    .add("origen", Constants.PLATAFORMA)
                    .add("usuario", "")
                    .add("foto", "")
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.URL_LOGIN) // The URL to send the data to
                    .post(formBody)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    StartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonRegistro.setVisibility(View.VISIBLE);
                            buttonLogin.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.GONE);
                            mostrarError();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    if(response.isSuccessful()) {
                        final String data = response.body().string();
                        StartActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                buttonRegistro.setVisibility(View.VISIBLE);
                                buttonLogin.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                Gson gson = new Gson();
                                RespuestaDaoLogin respuesta = gson.fromJson(data , RespuestaDaoLogin.class);
                                if(respuesta.getCodigo() == Constants.SERVICES_OK){
                                    Intent intent = new Intent(getApplicationContext(), News.class);
                                    try {
                                        intent.putExtra(Constants.PREFERENCE_USER , respuesta.getDatos().toString());
                                        startActivity(intent);
                                    }catch (Exception ex){
                                        Log.e(Constants.TAG_LOG , ex.getMessage());
                                        mostrarError();
                                    }
                                }else{
                                    mostrarError(respuesta.getMensaje());
                                }

                            }
                        });
                    }else{
                        StartActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttonRegistro.setVisibility(View.VISIBLE);
                                buttonLogin.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                mostrarError();
                            }
                        });
                    }
                }
            });

        }else{
            Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs));
        }
    }
    public void onRegister(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
    public void mostrarError(String mensaje){
        Utils.mostrarAlerta(this , mensaje);
    }

}
