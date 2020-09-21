package com.example.virussafeagro.uitilities;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.logging.Handler;

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

    public static void runSlideOutAnimationToTop(View view, int duration) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                -view.getHeight());
        animate.setDuration(duration);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public static void runSlideInAnimationFromTop(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                -view.getHeight(),
                0);
        animate.setDuration(duration);
        animate.setFillAfter(false);
        view.startAnimation(animate);
    }

    public static void runFlickerAnimation(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        view.startAnimation(alphaAnimation);
//        view.setAnimation(alphaAnimation);
//        alphaAnimation.start();
    }

    // for swipe
    public static void runRepeatedAnimationBottomToTop(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                0,
                0,
                50,
                25);
        translateAnimation.setDuration(duration);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.RESTART);
        view.setAnimation(translateAnimation);
        view.startAnimation(translateAnimation);
    }
}
