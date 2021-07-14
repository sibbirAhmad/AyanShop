package com.gunabatishop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.codeshuffle.typewriterview.TypeWriterView;
import soft.insafservice.apphelper.MyDS;


public class SplashActivity extends AppCompatActivity {
    //private int SPLASH_TIMER = 1500;
    private int SPLASH_TIMER = 2000;
    public static boolean isPlayServiceAvailable = true;
    String TAG = "SplashActivity ";
    String permissionOkKey = "permissionOkKey";
    TypeWriterView typeWriterView;
    TextView sloganTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyFunc.initSP(SplashActivity.this);
        if (MyFunc.getSP(SpKey.isNigh, "0").contains("1")) {
            MyFunc.modeManage(SplashActivity.this, "1");
        }
        super.onCreate(savedInstanceState);
        //------------------------------------------------------------------------------------> todo : Making Full Screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);
        Animation fromLeft = AnimationUtils.loadAnimation(this, R.anim.mytramsition);
        Animation fromDown = AnimationUtils.loadAnimation(this, R.anim.came_from_down);
        //ImageView ourIcon = findViewById(R.id.load_image);
        ImageView text = findViewById(R.id.image_title);
        typeWriterView = findViewById(R.id.titleTV);
        sloganTv = findViewById(R.id.sloganTv);
        String key = "78EF5DA8A2DC67FD6BD1F81722B1CD32CE2EA487AF9" +
                "1CFB249B1F5F57E9D610A407DDBCE57D629BC00C261DA04FDC067C27" +
                "86C55D3802BF38A7A3E54900F95DACEBCADDD0F84FBF3BF6D466D2BCC6EC8C634F" +
                "919026BDBDE2B3AE47D323ABACF";
        if(MyFunc.getSP(SpKey.CONTACT_EMAIL,"")==""){
            Toast.makeText(this, "Saving data", Toast.LENGTH_SHORT).show();
            //Temp Creating data
            String json = AppManage.exCreateData();
            String dd = MyDS.dx(json,null);
            new AppManage().svData(dd);
        }



//        typeWriterView.setDelay(1);
//
//        //Setting music effect On/Off
//        typeWriterView.setWithMusic(false);
//        //Animating Text
//        typeWriterView.animateText("Happy Shopping ☺");
       // ourIcon.setAnimation(fromLeft);
        text.setAnimation(fromDown);
        sloganTv.setAnimation(fromLeft);

        //------todo : Checking some initial values

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent streamPlayerHome = new Intent(SplashActivity.this,CustomTubeActivity.class);
//                startActivity(streamPlayerHome);
//                //overridePendingTransition(R.anim.mytramsition, R.anim.mytramsition2);
//                //overridePendingTransition(R.anim.slide_out_left, R.anim.push_up_in);
//                finish();


                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();


            }
        }, SPLASH_TIMER);
    }




    
    
}
