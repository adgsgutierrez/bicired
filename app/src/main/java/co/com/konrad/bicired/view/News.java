package co.com.konrad.bicired.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import co.com.konrad.bicired.R;
import co.com.konrad.bicired.logic.ResponseDaoNews;
import co.com.konrad.bicired.logic.UsuarioDao;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class News extends AppCompatActivity {

    private ListView items;
    private ProgressBar spiner;
    private TextView labelNoData;
    private ImageView imgNoData;
    private ResponseDaoNews news;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Capturando datos del Login
        Intent myIntent = getIntent();
        String datos = myIntent.getStringExtra(Constants.PREFERENCE_USER);
        //Guardado de preferencia
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.PREFERENCE_USER, datos);
        editor.commit();
        //Inicio de proceso
        Gson gson = new Gson();
        UsuarioDao user = gson.fromJson(datos , UsuarioDao.class);
        Log.d(Constants.TAG_LOG,user.getCorreo());
        //Creando Enlace con las vistas
        items = (ListView) findViewById(R.id.listNews);
        spiner = (ProgressBar) findViewById(R.id.cargandoSpinerNews);
        labelNoData = (TextView) findViewById(R.id.informacionNoData);
        imgNoData = (ImageView) findViewById(R.id.imagenNoData);

        labelNoData.setVisibility(View.GONE);
        imgNoData.setVisibility(View.GONE);
        items.setVisibility(View.GONE);
        spiner.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                .build();
        String parametros = "?listar=all&correo="+user.getCorreo();
        Request request = new Request.Builder()
                .url(Constants.URL_PUBLICACION + parametros) // The URL to send the data to
                .get()
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                News.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spiner.setVisibility(View.GONE);
                        labelNoData.setVisibility(View.VISIBLE);
                        imgNoData.setVisibility(View.VISIBLE);
                        mostrarError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response.isSuccessful()) {
                    final String data = response.body().string();
                    News.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(Constants.TAG_LOG , data);
                            Gson gson = new Gson();
                             news = gson.fromJson(data , ResponseDaoNews.class);
                            if(news.getCodigo() == Constants.SERVICES_OK) {
                                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                                fab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(News.this, CreateEventActivity.class);
                                        Intent myIntent = getIntent();
                                        String datos = myIntent.getStringExtra(Constants.PREFERENCE_USER);
                                        intent.putExtra("dato_correo",datos);
                                        startActivity(intent);
                                    }
                                });
                                if(news.getNewDao().size() > 0) {
                                    MapAdapter map = new MapAdapter(getApplicationContext(), R.layout.adapter_map, news.getNewDao());
                                    items.setAdapter(map);


                                    items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(News.this, MapaDetalleRuta.class);
                                            intent.putExtra(Constants.PARAMETRO_LATITUD_ORIGEN , news.getNewDao().get(position).getPbl_ltd_origen());
                                            intent.putExtra(Constants.PARAMETRO_LONGITUD_ORIGEN , news.getNewDao().get(position).getPbl_ltg_origen());
                                            intent.putExtra(Constants.PARAMETRO_LATITUD_DESTINO , news.getNewDao().get(position).getPbl_ltd_destino());
                                            intent.putExtra(Constants.PARAMETRO_LONGITUD_DESTINO , news.getNewDao().get(position).getPbl_ltg_destino());
                                            startActivity(intent);
                                        }
                                    });
                                    items.setVisibility(View.VISIBLE);
                                    spiner.setVisibility(View.GONE);
                                }else{
                                    spiner.setVisibility(View.GONE);
                                    labelNoData.setVisibility(View.VISIBLE);
                                    imgNoData.setVisibility(View.VISIBLE);
                                }
                            }else{
                                spiner.setVisibility(View.GONE);
                                labelNoData.setVisibility(View.VISIBLE);
                                imgNoData.setVisibility(View.VISIBLE);
                                mostrarError();
                            }
                        }
                    });
                }else{
                    News.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            items.setVisibility(View.VISIBLE);
                            spiner.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
/*

        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_bar:
                Log.d(Constants.TAG_LOG, "Ingresando al profile");
                Intent intent = new Intent(this, profileActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
}
