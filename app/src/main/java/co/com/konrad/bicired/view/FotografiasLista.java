package co.com.konrad.bicired.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import co.com.konrad.bicired.R;
import co.com.konrad.bicired.logic.Fotografia;
import co.com.konrad.bicired.logic.ResponseDaoFotografias;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FotografiasLista extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ProgressBar spiner;
    private TextView labelNoData;
    private ImageView imgNoData;
    private ArrayList<Fotografia> data;
    private final String RUTA_FOTOS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    private File fotografiaFile;
    private static final MediaType MEDIA_TYPE_JPG = MediaType.get("image/jpeg");
    private String correo;
    private String evento;

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
        correo = myIntent.getStringExtra(Constants.PREFERENCE_USER);
        evento = myIntent.getStringExtra(Constants.PARAMETRO_EVENTO);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabFotografia);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotografia();
            }
        });
        //Consulta de fotografias
        consultarFotografias();
    }

    public void mostrarError(){
        Utils.mostrarAlerta(this , getString(R.string.MENSAJE_ERROR_GENERAL));
    }

    @TargetApi(value=23)
    public void tomarFotografia() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        }else if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

        }else if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);

        }else{
            capturarFotografia();
        }
    }

    @SuppressLint("SimpleDateFormat")
     private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        return photoCode;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capturarFotografia();
            }else {
                Toast.makeText(this, "No podemos tomar, la fotografía, debes permitirnos acceder a la camara", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capturarFotografia();
            }else {
                Toast.makeText(this, "No podemos guardar, la fotografía, debes permitirnos acceder a la camara", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capturarFotografia();
            }else {
                Toast.makeText(this, "No podemos guardar, la fotografía, debes permitirnos acceder a la camara", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void capturarFotografia(){
        try{
            String file =  RUTA_FOTOS + "/" + getCode() + ".jpg";
            Log.e(Constants.TAG_LOG , file);
            fotografiaFile = new File(file);
            if(fotografiaFile.createNewFile()) {
                Uri uri = Uri.fromFile(fotografiaFile);
                //Abre la camara para tomar la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Guarda imagen
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //Retorna a la actividad
                startActivityForResult(cameraIntent, 1);
            }else{
                Toast.makeText(this, getString(R.string.ERROR_SAVE_FILE), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e(Constants.TAG_LOG , e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(Constants.TAG_LOG , "requestCode "+requestCode);
        Log.e(Constants.TAG_LOG , "resultCode "+resultCode);


            try {
                spiner.setVisibility(View.VISIBLE);
                labelNoData.setVisibility(View.GONE);
                imgNoData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                if(fotografiaFile.isFile()){
                    OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                            .readTimeout(7, TimeUnit.MINUTES)
                            .writeTimeout(7, TimeUnit.MINUTES)
                            .build();

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("foto", fotografiaFile.getName(), RequestBody.create(MEDIA_TYPE_JPG, fotografiaFile))
                            //.addFormDataPart("foto", fotografiaFile.getName(), RequestBody.create(null , fotografiaFile))
                            .addFormDataPart("email", correo)
                            .addFormDataPart("evento", evento)
                            .build();

                    Request request = new Request.Builder()
                            .url(Constants.URL_FOTOS)
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e(Constants.TAG_LOG, e.getMessage());
                            FotografiasLista.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spiner.setVisibility(View.GONE);
                                    labelNoData.setVisibility(View.VISIBLE);
                                    imgNoData.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), getString(R.string.ERROR_UPDATE_FILE), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e(Constants.TAG_ERROR , "Mensaje desde el server "+ response.body().string());
                            if (response.isSuccessful()) {

                                FotografiasLista.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        spiner.setVisibility(View.GONE);
                                        labelNoData.setVisibility(View.GONE);
                                        imgNoData.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        consultarFotografias();
                                    }
                                });
                            } else {
                                FotografiasLista.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        spiner.setVisibility(View.GONE);
                                        labelNoData.setVisibility(View.VISIBLE);
                                        imgNoData.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), getString(R.string.ERROR_UPDATE_FILE), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, getString(R.string.ERROR_SAVE_FILE), Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Log.e(Constants.TAG_LOG, "onActivityResult: "+ e.toString());
                Toast.makeText(this, getString(R.string.ERROR_UPDATE_FILE), Toast.LENGTH_SHORT).show();
            }
    }

    private void consultarFotografias(){
        spiner.setVisibility(View.VISIBLE);
        labelNoData.setVisibility(View.GONE);
        imgNoData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

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
}
