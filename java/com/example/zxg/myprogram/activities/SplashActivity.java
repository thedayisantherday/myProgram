package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.zxg.myprogram.R;

public class SplashActivity extends Activity {

    private ImageView iv_splash;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mContext = this;
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        startAnimation();
    }

    private void startAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv_splash.setAnimation(alphaAnimation);
    }
}
