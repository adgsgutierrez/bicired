package co.com.konrad.bicired.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.com.konrad.bicired.logic.ResponseDao;
import co.com.konrad.bicired.utils.Constants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ServicesLoginTask extends AsyncTask<String, Void, String> {

    ProgressDialog progDailog;
    Context context;

    public ServicesLoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("correo", strings[1])
                    .add("clave", strings[2])
                    .add("origen", Constants.PLATAFORMA)
                    .add("usuario", "")
                    .add("foto", "")
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0]) // The URL to send the data to
                    .post(formBody)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            return responseString;
        }catch (Exception e){
            Log.e(Constants.TAG_ERROR, e.getMessage());
        }
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("On Post Execute" , s);
        progDailog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
