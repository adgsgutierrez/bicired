package co.com.konrad.bicired;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

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
        startActivity(new Intent(this , StartActivity.class));
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        finish();
   }
}
