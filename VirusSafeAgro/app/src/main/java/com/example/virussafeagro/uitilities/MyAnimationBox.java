package com.example.virussafeagro.uitilities;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MyAnimationBox {

    public static void runFadeInAnimation(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

    public static void runFadeOutAnimation(View view, int duration) {
        view.setVisibility(View.GONE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

    public static void runSlideInAnimationFromBottomToTop(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                view.getHeight(),
                0);
        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
}
