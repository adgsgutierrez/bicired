package co.com.konrad.bicired.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import co.com.konrad.bicired.R;

public class Utils {

    public static void mostrarAlerta (Context context , String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //Adicionando Mensaje
        builder.setMessage(mensaje);
        // Add the button
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
