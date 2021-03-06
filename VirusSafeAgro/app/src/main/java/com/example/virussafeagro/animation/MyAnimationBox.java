package com.example.virussafeagro.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;

import java.util.logging.Handler;

public class MyAnimationBox {
    public static void configureTheAnimation(MotionLayout motionLayout, int start, int end, int duration) {
        // move beginner Button + show intermediate Button
        motionLayout.clearAnimation();
        motionLayout.setTransition(start, end);
        motionLayout.setTransitionDuration(duration);
        motionLayout.transitionToEnd();
    }

    public static void runFadeInAnimation(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

    public static void runFadeOutAnimation(View view, int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.GONE);
    }

    public static void runFadeOutAnimationToInvisible(View view, int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.INVISIBLE);
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

    public static void runSlideOutAnimationToBottom(View view, int duration) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                view.getHeight());
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

    public static void runSlideInAnimationFromRight1(View view, float from, float to, int duration) {
        TranslateAnimation animate = new TranslateAnimation(
                from,
                to,
                0,
                0);
        animate.setDuration(duration);
        animate.setFillAfter(false);
        view.startAnimation(animate);
    }

    // for fold the image in virus details
    public static void runSlideInAnimationFromRight(View view, float from, float to, int duration){
        //value animator
        ValueAnimator valueAnimator = new ValueAnimator();
        //hide view
        valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            //get the current x value
            float x =(float) valueAnimator1.getAnimatedValue();
            // update the height of the view dynamically
            view.setX(x);
        });
        valueAnimator.setDuration(duration);
        // start animation
        valueAnimator.start();
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

    // for fold the image in virus details
    public static void runFoldViewAnimationByHeight(View view, int startHeight, int targetHeight, int duration){
        //value animator
        ValueAnimator valueAnimator = new ValueAnimator();
        //hide view
        valueAnimator = ValueAnimator.ofInt(startHeight, targetHeight);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            //get the current height value
            int h =(Integer) valueAnimator1.getAnimatedValue();
            // update the height of the view dynamically
            view.getLayoutParams().height = h;
            view.requestLayout();
        });
        valueAnimator.setDuration(duration);
        // start animation
        valueAnimator.start();
    }

    // for fold the image in virus details
    public static void runFoldViewAnimationByWidth(View view, int startWidth, int targetWidth, int duration){
        //value animator
        ValueAnimator valueAnimator = new ValueAnimator();
        //hide view
        valueAnimator = ValueAnimator.ofInt(startWidth, targetWidth);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            //get the current height value
            int w =(Integer) valueAnimator1.getAnimatedValue();
            // update the height of the view dynamically
            view.getLayoutParams().width = w;
            view.requestLayout();
        });
        valueAnimator.setDuration(duration);
        // start animation
        valueAnimator.start();
    }

    // for changing the text size
    public static void runChangeViewSizeAnimation(TextView textView, float startSize, float targetSize, int duration){
        //value animator
        ObjectAnimator objectAnimator = new ObjectAnimator();
        //hide view
        objectAnimator = ObjectAnimator.ofFloat(textView, "textSize", startSize, targetSize);
        objectAnimator.addUpdateListener(valueAnimator1 -> {
            //get the current size value
            float size = (float)valueAnimator1.getAnimatedValue();
            // update the height of the view dynamically
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        });
        objectAnimator.setDuration(duration);
        // start animation
        objectAnimator.start();
    }

}
