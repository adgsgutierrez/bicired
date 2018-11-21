package co.com.konrad.bicired.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.StartActivity;
import co.com.konrad.bicired.logic.RespuestaDaoLogin;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

     Spinner Genero;
     EditText generodefinitivo,nombre,correo,clave,confirmacion_clave;
    private ProgressBar spinner;
    private Button boton,boton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        boton = (Button) findViewById(R.id.button);
        boton2 = (Button) findViewById(R.id.button2);
        spinner = (ProgressBar) findViewById(R.id.cargandoSpiner);
        Genero = (Spinner)findViewById(R.id.spinner);

        generodefinitivo = (EditText)findViewById(R.id.genero);
        nombre = (EditText)findViewById(R.id.nombre_registro);
        correo = (EditText)findViewById(R.id.correo_registro);
        clave = (EditText)findViewById(R.id.clave);
        confirmacion_clave = (EditText)findViewById(R.id.confirmar_clave);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.genero,android.R.layout.simple_spinner_item);

        Genero.setAdapter(adapter);

        Genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                if(item.toString().equals("Hombre")){
                    generodefinitivo.setText("M");
                }else if(item.toString().equals("Mujer")){
                    generodefinitivo.setText("F");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Register(View v){
        String genero = generodefinitivo.getText().toString().trim();
        String nombre_re = nombre.getText().toString().trim();
        String clave_re = clave.getText().toString().trim();
        String clave_re_co = confirmacion_clave.getText().toString().trim();
        String correo_re = correo.getText().toString().trim();

        Log.e(Constants.TAG_ERROR , "genero -"+genero+"- nombre_re -"+nombre_re+"- clave_re -"+clave_re+ "- clave_re_co -"+clave_re_co + "- correo_re -"+correo_re+"-");

        if(Utils.validacionCorreoPassword(genero , false) &&
                Utils.validacionCorreoPassword(nombre_re , false)  &&
                Utils.validacionCorreoPassword(clave_re , false)  &&
                Utils.validacionCorreoPassword(clave_re_co , false)) {

            if(!clave_re.equals(clave_re_co)){
                Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs4));
            }
            else if(Utils.validacionCorreoPassword(correo_re , true)){
                Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_EMAIL));

            }else{

                boton.setVisibility(View.GONE);
                boton2.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                OkHttpClient client = new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                        .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                        .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                        .build();
                RequestBody formBody = new FormBody.Builder()
                        .add("correo", correo_re)
                        .add("nombre", nombre_re)
                        .add("genero",genero)
                        .add("clave", clave_re_co)
                        .build();
                Log.d(Constants.TAG_LOG, String.valueOf(formBody));
                Request request = new Request.Builder()
                        .url(Constants.URL_LOGIN) // The URL to send the data to
                        .put(formBody)
                        .addHeader("content-type", "application/json; charset=utf-8")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boton.setVisibility(View.VISIBLE);
                                boton2.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.GONE);
                                mostrarError();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String data = response.body().string();
                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    boton.setVisibility(View.VISIBLE);
                                    boton2.setVisibility(View.VISIBLE);
                                    spinner.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    RespuestaDaoLogin respuesta = gson.fromJson(data , RespuestaDaoLogin.class);
                                    if(respuesta.getCodigo() == Constants.SERVICES_OK){
                                        OkHttpClient client = new OkHttpClient()
                                                .newBuilder()
                                                .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                                                .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                                                .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                                                .build();
                                        RequestBody formBody = new FormBody.Builder()
                                                .add("correo", correo.getText().toString())
                                                .add("clave", clave.getText().toString())
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
                                                RegisterActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        mostrarError();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onResponse(Call call, final Response response) throws IOException {

                                                if(response.isSuccessful()) {
                                                    final String datas = response.body().string();
                                                    RegisterActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            Gson gson = new Gson();
                                                            RespuestaDaoLogin respuesta2 = gson.fromJson(datas , RespuestaDaoLogin.class);
                                                            if(respuesta2.getCodigo() == Constants.SERVICES_OK){
                                                                Log.d(Constants.TAG_LOG,respuesta2.getDatos().toString());
                                                                Intent intent = new Intent(getApplicationContext(), News.class);
                                                                try {
                                                                    intent.putExtra(Constants.PREFERENCE_USER_DATA ,respuesta2.getDatos().toString());
                                                                    startActivity(intent);
                                                                }catch (Exception ex){
                                                                    Log.e(Constants.TAG_LOG , ex.getMessage());
                                                                    mostrarError();
                                                                }

                                                            }else{
                                                                mostrarError(respuesta2.getMensaje());
                                                            }

                                                        }
                                                    });
                                                }else{
                                                    RegisterActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            boton.setVisibility(View.VISIBLE);
                                                            boton2.setVisibility(View.VISIBLE);
                                                            spinner.setVisibility(View.GONE);
                                                            mostrarError();
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }else{
                                        boton.setVisibility(View.VISIBLE);
                                        boton2.setVisibility(View.VISIBLE);
                                        spinner.setVisibility(View.GONE);
                                        mostrarError(respuesta.getMensaje());
                                    }

                                }
                            });
                        }else{
                            RegisterActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    boton.setVisibility(View.VISIBLE);
                                    boton2.setVisibility(View.VISIBLE);
                                    spinner.setVisibility(View.GONE);
                                    mostrarError();
                                }
                            });
                        }
                    }
                });
            }
        }
        else{
            Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs3));
        }


    }

    public void Cancel (View v){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
    public void mostrarError(String mensaje){
        Utils.mostrarAlerta(this , mensaje);
    }
}

