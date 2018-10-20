package co.com.konrad.bicired;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import co.com.konrad.bicired.utils.Constants;
import co.com.konrad.bicired.view.News;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences preferences;

    @Override
   protected void onCreate(Bundle sa){
       super.onCreate(sa);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                endSplash();
           }
       }, SPLASH_TIME_OUT);
   }

   private void endSplash(){
       SharedPreferences sharedPref = getSharedPreferences(Constants.PREFERENCE_USER, Context.MODE_PRIVATE);
       boolean isLogin = sharedPref.getBoolean(Constants.PREFERENCE_USER_IS_LOGIN, false);
        if(!isLogin) {
            startActivity(new Intent(this, StartActivity.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            finish();
        }else{
            String datos = sharedPref.getString(Constants.PREFERENCE_USER_DATA , "");
            Intent intent = new Intent(getApplicationContext(), News.class);
            intent.putExtra(Constants.PREFERENCE_USER_DATA , datos);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            finish();
        }
   }
}
