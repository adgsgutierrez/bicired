package co.com.konrad.bicired;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import co.com.konrad.bicired.logic.ResponseDao;
import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.Services.ServicesLoginTask;
import co.com.konrad.bicired.utils.Utils;
import co.com.konrad.bicired.view.News;
import co.com.konrad.bicired.view.RegisterActivity;

public class StartActivity extends AppCompatActivity {

    private EditText correo;
    private EditText clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.correo = ((EditText) findViewById(R.id.usuario));
        this.correo.setHintTextColor(getResources().getColor(R.color.colorAccent));
        this.clave = ((EditText) findViewById(R.id.clave));
        this.clave.setHintTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void onLogin(View v){
        String correo = this.correo.getText().toString();
        String clave = this.clave.getText().toString();
        if(!correo.equals("") && !clave.equals("")) {
            ServicesLoginTask servicesTask = new ServicesLoginTask(v.getContext());
            String[] datos = new String[3];
            datos[0] = Constants.URL_LOGIN;
            datos[1] = correo;
            datos[2] = clave;
            try {
                String responseString = servicesTask.execute(datos).get();
                Gson gson = new GsonBuilder().create();
                ResponseDao respuesta = gson.fromJson(responseString , ResponseDao.class);

                if(respuesta.getCodigo() == Constants.SERVICES_OK){
                    Intent intent = new Intent(this, News.class);
                    startActivity(intent);
                }else{
                    Utils.mostrarAlerta(this ,respuesta.getMensaje());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }else{
            Utils.mostrarAlerta(this , getString(R.string.mensaje_error_not_inputs));
        }
    }
    public void onRegister(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}
