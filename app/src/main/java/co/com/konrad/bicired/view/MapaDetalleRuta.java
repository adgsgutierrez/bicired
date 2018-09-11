package co.com.konrad.bicired.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import co.com.konrad.bicired.R;

public class MapaDetalleRuta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_detalle_ruta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_fotografias = (FloatingActionButton) findViewById(R.id.fotografias_detalle);
        fab_fotografias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FotografiasLista.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_volver = (FloatingActionButton) findViewById(R.id.volver_mapa);
        fab_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), News.class);
                startActivity(intent);
            }
        });
    }

}
