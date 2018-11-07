package com.tcl.easybill.ui.activity;


import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;



public class SplashActivity extends AwesomeSplash {

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LockViewUtil.setIsfirst(this, false);
    }

    /**
     * Show the Splash
     * @param configSplash
     */
    @Override
    public void initSplash(ConfigSplash configSplash) {
        /*If it is the first time to use app, start the WelcomeActivity*/
        if(LockViewUtil.getIsfirst(this)){
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }



        /* you don't have to override every property */

        //设置主题颜色
        configSplash.setBackgroundColor(R.color.startColor);

        //configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(3000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.start); //or any other drawable
        configSplash.setAnimLogoSplashDuration(500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInUp); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("好好记账");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
//        configSplash.setTitleFont("fonts/volatire.ttf"); //provide string to your font located in assets/fonts/

    }

    /**
     * 监听动画完成事件
     */
    @Override
    public void animationsFinished() {

        Intent intent  = new Intent(this,UesrLoginActivity.class);
        startActivityForResult(intent,0);
        finish();
    }
}
