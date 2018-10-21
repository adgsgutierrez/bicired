package co.com.konrad.bicired.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback {

    GoogleMap map;
    Button btn_fecha;
    TextView textfecha;
    EditText lt1,lt2,ln1,ln2;
    int day, month, year, hour, minutes;
    int dayFinal, monthFinal, yearFinal, hourFinal, minutesFinal;
    private  Button btn_guardar;
    private ProgressBar spinner;
    ArrayList<LatLng> ListPoints;
    private int distancia;
    private String nombre_mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        ListPoints = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        btn_guardar = (Button) findViewById(R.id.button6);
        btn_fecha = (Button) findViewById(R.id.btnfecha);
        textfecha = (TextView) findViewById(R.id.textfecha);
        spinner = (ProgressBar) findViewById(R.id.cargandoSpiner);
        lt1 = (EditText) findViewById(R.id.lt1);
        lt2 = (EditText) findViewById(R.id.lt2);
        ln1 = (EditText) findViewById(R.id.ln1);
        ln2 = (EditText) findViewById(R.id.ln2);

        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, CreateEventActivity.this, year, month, day);
                datePickerDialog.show();

            }
        });

    }

    public void Guardar (View v){
        String fecha = textfecha.getText().toString();
        String lt1 = this.lt1.getText().toString();
        String lt2 = this.lt2.getText().toString();
        String ln1 = this.ln1.getText().toString();
        String ln2 = this.ln2.getText().toString();
        Intent myIntent = getIntent();
        String datos = myIntent.getStringExtra("dato_correo");
        Gson gson = new Gson();
        final UsuarioDao user = gson.fromJson(datos , UsuarioDao.class);

        if(!fecha.equals("") && !lt1.equals("") && !lt2.equals("") && !ln1.equals("") && !ln2.equals("") && !user.getCorreo().equals("")) {
         btn_guardar.setVisibility(View.GONE);
         spinner.setVisibility(View.VISIBLE);

            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(5000 , TimeUnit.MILLISECONDS)
                    .readTimeout(5000 ,TimeUnit.MILLISECONDS)
                    .writeTimeout(5000 ,TimeUnit.MILLISECONDS)
                    .build();
            RequestBody formBody = new FormBody.Builder()
                    .add("fecha", fecha)
                    .add("lt1", lt1)
                    .add("ln1",ln1)
                    .add("lt2", lt2)
                    .add("ln2", ln2)
                    .add("descripcion", "hara un recorrido de "+distancia+" mts")
                    .add("usuario", user.getCorreo())
                    .build();
            Log.d(Constants.TAG_LOG, String.valueOf(formBody));
            Request request = new Request.Builder()
                    .url(Constants.URL_PUBLICACION) // The URL to send the data to
                    .post(formBody)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    CreateEventActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_guardar.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.GONE);
                            mostrarError();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    if(response.isSuccessful()) {
                        final String data = response.body().string();
                        CreateEventActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();

                                RespuestaDaoLogin respuesta = gson.fromJson(data , RespuestaDaoLogin.class);

                                if(respuesta.getCodigo() == Constants.SERVICES_OK){
                                    btn_guardar.setVisibility(View.VISIBLE);
                                    spinner.setVisibility(View.GONE);
                                    Intent myIntent = getIntent();
                                    String datos = myIntent.getStringExtra("dato_correo");
                                    Log.d(Constants.TAG_LOG,datos);
                                    Log.d(Constants.TAG_LOG,""+distancia+"");
                                    Intent intent = new Intent(getApplicationContext(), News.class);
                                    try {
                                        intent.putExtra(Constants.PREFERENCE_USER_DATA, datos);
                                        startActivity(intent);
                                    }catch (Exception ex){
                                        Log.e(Constants.TAG_LOG , ex.getMessage());
                                        mostrarError();
                                    }
                                }else{
                                    btn_guardar.setVisibility(View.VISIBLE);
                                    spinner.setVisibility(View.GONE);
                                    mostrarError(respuesta.getMensaje());
                                }

                            }
                        });
                    }else{
                        btn_guardar.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                    }
                }
            });

        }else{
            Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs2));
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, CreateEventActivity.this, hour, minutes, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minutesFinal = i1;

        textfecha.setText(yearFinal + "-" + monthFinal + "-" + dayFinal + " " +hourFinal + ":" + minutesFinal+":00");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoomLevel = 14.0f;
        LatLng bogota = new LatLng(4.710988599999999, -74.072092);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, zoomLevel));

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(ListPoints.size() == 2){
                    ListPoints.clear();
                    map.clear();
                }
                ListPoints.add(latLng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if(ListPoints.size() == 1){
                  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else{
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                map.addMarker(markerOptions);

                if(ListPoints.size() == 2){

                   lt1.setText(""+ListPoints.get(0).latitude+"");
                    lt2.setText(""+ListPoints.get(1).latitude+"");
                    ln1.setText(""+ListPoints.get(0).longitude+"");
                    ln2.setText(""+ListPoints.get(1).longitude+"");
                    distancia = (int) Math.round(getdistance(ListPoints.get(0).latitude,ListPoints.get(0).longitude,ListPoints.get(1).latitude,ListPoints.get(1).longitude));

                    Log.d(Constants.TAG_LOG,""+Math.round(getdistance(ListPoints.get(0).latitude,ListPoints.get(0).longitude,ListPoints.get(1).latitude,ListPoints.get(1).longitude))+"");
Log.d(Constants.TAG_LOG,""+distancia+"");
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

    public double getdistance(double lat1,double ln1,double lat2,double ln2){
        int R =  6378137;
        double dlat = rand(lat2-lat1);
        double dln = rand(ln2-ln1);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(rand(lat1)) * Math.cos(rand(lat2)) * Math.sin(dln / 2) * Math.sin(dln / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    public double rand(double x){
        return x * Math.PI / 180;
    }

}
