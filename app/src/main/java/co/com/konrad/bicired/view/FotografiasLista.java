package co.com.konrad.bicired.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.logic.Fotografia;
import co.com.konrad.bicired.logic.ResponseDaoFoto;
import co.com.konrad.bicired.logic.ResponseDaoFotografias;
import co.com.konrad.bicired.logic.ResponseDaoNews;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FotografiasLista extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ProgressBar spiner;
    private TextView labelNoData;
    private ImageView imgNoData;
    private ArrayList<Fotografia> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotografias_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        spiner = (ProgressBar) findViewById(R.id.cargandoSpinerPhotos);
        labelNoData = (TextView) findViewById(R.id.informacionNoDataPhotos);
        imgNoData = (ImageView) findViewById(R.id.imagenNoDataPhotos);

        recyclerView.setHasFixedSize(true);
        Intent myIntent = getIntent();
        String correo = myIntent.getStringExtra(Constants.PREFERENCE_USER);
        String evento = myIntent.getStringExtra(Constants.PARAMETRO_EVENTO);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar fotografia", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        spiner.setVisibility(View.VISIBLE);
        labelNoData.setVisibility(View.GONE);
        imgNoData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        //Consulta de fotografias
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                .build();
        RequestBody formBody = new FormBody.Builder()
                .add("publicacion", evento)
                .build();
        Request request = new Request.Builder()
                .url(Constants.URL_PUBLICACION ) // The URL to send the data to
                .put(formBody)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FotografiasLista.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spiner.setVisibility(View.GONE);
                        labelNoData.setVisibility(View.VISIBLE);
                        imgNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        mostrarError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String data = response.body().string();
                    FotografiasLista.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(Constants.TAG_LOG, data);
                            Gson gson = new Gson();
                            ResponseDaoFotografias fotos = gson.fromJson(data, ResponseDaoFotografias.class);
                            if (fotos.getCodigo() == Constants.SERVICES_OK ) {
                                spiner.setVisibility(View.GONE);
                                if(fotos.getDatos().size() > 0) {
                                    labelNoData.setVisibility(View.GONE);
                                    imgNoData.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    CustomAdapter adapter = new CustomAdapter(fotos.getDatos() , getApplicationContext());
                                    recyclerView.setAdapter(adapter);
                                }else{
                                    labelNoData.setVisibility(View.VISIBLE);
                                    imgNoData.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }else{
                                spiner.setVisibility(View.GONE);
                                labelNoData.setVisibility(View.VISIBLE);
                                imgNoData.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    FotografiasLista.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spiner.setVisibility(View.GONE);
                            labelNoData.setVisibility(View.VISIBLE);
                            imgNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            mostrarError();
                        }
                    });
                }
            }
        });
    }

    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }
}
