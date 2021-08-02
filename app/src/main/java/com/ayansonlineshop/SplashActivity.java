package com.ayansonlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.codeshuffle.typewriterview.TypeWriterView;
import soft.insafservice.apphelper.MyFunc;


public class SplashActivity extends AppCompatActivity {
    //private int SPLASH_TIMER = 1500;
    private int SPLASH_TIMER = 2000;
    public static boolean isPlayServiceAvailable = true;
    String TAG = "SplashActivity ";
    String permissionOkKey = "permissionOkKey";
    TypeWriterView typeWriterView;
    TextView sloganTv;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyFunc.initSP(SplashActivity.this);
        if (MyFunc.getSP(SpKey.isNigh, "0").contains("1")) {
            MyFunc.modeManage(SplashActivity.this, "1");
        }
        AppManage.checkAppHealth(SplashActivity.this); //TODO : Cheking update and security

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

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        text.startAnimation(anim);


//        typeWriterView.setDelay(1);
//
//        //Setting music effect On/Off
//        typeWriterView.setWithMusic(false);
//        //Animating Text
//        typeWriterView.animateText("Happy Shopping ☺");
        // ourIcon.setAnimation(fromLeft);
//        text.setAnimation(fromDown);
//        sloganTv.setAnimation(fromLeft);
//
//        //------todo : Checking some initial values
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                Intent streamPlayerHome = new Intent(SplashActivity.this,CustomTubeActivity.class);
////                startActivity(streamPlayerHome);
////                //overridePendingTransition(R.anim.mytramsition, R.anim.mytramsition2);
////                //overridePendingTransition(R.anim.slide_out_left, R.anim.push_up_in);
////                finish();
//
//
//                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
//
//
//            }
//        }, SPLASH_TIMER);
//    }


    }

    
    
}
