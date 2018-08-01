package co.com.konrad.bicired;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import co.com.konrad.bicired.co.com.konrad.bicired.utils.Constants;

public class SplashActivity extends Activity {

    private VideoView videoSplash;

   @Override
   protected void onCreate(Bundle sa){
       super.onCreate(sa);
       try{
           setContentView(R.layout.activity_splash);
           videoSplash = (VideoView) findViewById(R.id.videoView);
           Uri path = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
           videoSplash.setVideoURI(path);
           videoSplash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
               public void onCompletion(MediaPlayer mp) {
                   endSplash();
               }
           });
           videoSplash.start();
       }catch(Exception e){
           Log.e(Constants.TAG_ERROR, "Error al iniciar el splash "+e.getMessage());
           endSplash();
       }
   }

   private void endSplash(){

       if(isFinishing()){
            return;
        }

        startActivity(new Intent(this , StartActivity.class));
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
   }
}
