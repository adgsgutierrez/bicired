package co.com.konrad.bicired.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import co.com.konrad.bicired.R;

public class Utils {



    public static void mostrarAlerta (Context context , String mensaje ){
        Dialog customDialog = null;
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(context,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialogo_mensajes);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Ups!!!");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText(mensaje);

        final Dialog customFinal = customDialog;

        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customFinal.dismiss();
            }
        });

        customDialog.show();
    }


    public static boolean validacionCorreoPassword (String input , Boolean email){
        //Validacion de campos
        //Es vacio
        Log.e(Constants.TAG_ERROR , "Validando -"+input + "- Booleano "+email);
        if(!input.equals("")){
            //Es correo
            if(email){
                Log.e(Constants.TAG_ERROR , "Validando por email");
                return (input.matches(Constants.EMAIL_REGEX));
            }
            //es input normal
            else{
                Log.e(Constants.TAG_ERROR , "Validando por texto");
                return (input.matches(Constants.TEXT_REGEX));
            }
        }
        return false;
    }
}
