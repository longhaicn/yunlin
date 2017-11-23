package hnu.fooma.yunlin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import hnu.fooma.yunlin.activity.LoginActivity;

public class WelcomeActivity extends Activity {
    Intent intent ;
    SharedPreferences.Editor editor;
    boolean isFirstRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        editor = sharedPreferences.edit();

        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);
    }
    class splashhandler implements Runnable{

        public void run() {

            if (isFirstRun){
                editor.putBoolean("isFirstRun", false);
                editor.commit();
                intent = new Intent(WelcomeActivity.this, GuideActivity.class);
            } else{
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            }
            WelcomeActivity.this.startActivity(intent);
            finish();
        }

    }
}

