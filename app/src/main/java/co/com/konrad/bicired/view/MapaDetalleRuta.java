package co.com.konrad.bicired.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.com.konrad.bicired.R;
import co.com.konrad.bicired.logic.ArrayPoint;
import co.com.konrad.bicired.logic.Point;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.utils.DirectionsJSONParser;

public class MapaDetalleRuta extends AppCompatActivity {

    private GoogleMap googleMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_detalle_ruta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent myIntent = getIntent();
        Gson gson = new Gson();
        final String correo = myIntent.getStringExtra(Constants.PREFERENCE_USER);
        final String evento = myIntent.getStringExtra(Constants.PARAMETRO_EVENTO);

        String ruta = myIntent.getStringExtra(Constants.PARAMETRO_RUTA);
        Log.e(Constants.TAG_LOG , ruta);

        final ArrayList<Point> puntosRuta = gson.fromJson(ruta , new TypeToken<ArrayList<Point>>() {}.getType());

        PolylineOptions polyLineOptions = null;
        polyLineOptions = new PolylineOptions();

        final PolylineOptions polilinea = polyLineOptions;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMapView = googleMap;
                // Creating a marker inicio
                MarkerOptions markerOptions = new MarkerOptions();
                MarkerOptions markerOptions1 = new MarkerOptions();
                int height = 60;
                int width = 60;
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.start);
                BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.end);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap b1 = bitmapdraw1.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, width, height, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(smallMarker1));
                Point pointStart = puntosRuta.get(0);
                double lat = Double.parseDouble(pointStart.getLatitud());
                double lng = Double.parseDouble(pointStart.getLongitud());
                LatLng position = new LatLng(lat, lng);
                Point pointEnd = puntosRuta.get(puntosRuta.size() -1);
                markerOptions.position(position);
                markerOptions.title("Inicia tu ruta aquí");

                double lat1 = Double.parseDouble(pointEnd.getLatitud());
                double lng1 = Double.parseDouble(pointEnd.getLongitud());
                LatLng position1 = new LatLng(lat1, lng1);
                markerOptions1.position(position1);
                markerOptions1.title("Finaliza tu ruta aquí");

                // Getting URL to the Google Directions API
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                googleMap.addMarker(markerOptions);
                googleMap.addMarker(markerOptions1);
               // String url = getDirectionsUrl(position, position1);
               // DownloadTask downloadTask = new DownloadTask();
               // downloadTask.execute(url);
                ArrayList points = null;
                PolylineOptions lineOptions = new PolylineOptions();;
                for (int i = 0; i < puntosRuta.size(); i++) {
                    points = new ArrayList();
                    Point point = puntosRuta.get(i);
                    double lat2 = Double.parseDouble(point.getLatitud());
                    double lng2 = Double.parseDouble(point.getLongitud());
                    LatLng positionRuta = new LatLng(lat2, lng2);
                    points.add(positionRuta);
                    lineOptions.addAll(points);
                    lineOptions.width(8);
                    lineOptions.color(Color.BLUE);
                    lineOptions.geodesic(true);
                }
                if(lineOptions != null) {
                    googleMapView.addPolyline(lineOptions);
                }else{
                    Toast.makeText(getApplicationContext() , getString(R.string.MENSAJE_ERROR_PAINT_ROUTE) , Toast.LENGTH_SHORT).show();
                }



            }
        });

        FloatingActionButton fab_fotografias = (FloatingActionButton) findViewById(R.id.fotografias_detalle);
        fab_fotografias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FotografiasLista.class);
                intent.putExtra(Constants.PARAMETRO_EVENTO, evento);
                intent.putExtra(Constants.PREFERENCE_USER, correo);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_volver = (FloatingActionButton) findViewById(R.id.volver_mapa);
        fab_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
/*
    //Descarga de URL
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    //Consulta de URL
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);
            }
            if(lineOptions != null) {
                googleMapView.addPolyline(lineOptions);
            }else{
                Toast.makeText(getApplicationContext() , getString(R.string.MENSAJE_ERROR_PAINT_ROUTE) , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=walking";
        String key = "key=AIzaSyAkDlGqJE3SPMkrF_ZbbRX4V-e_YoBTmuU";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
*/
