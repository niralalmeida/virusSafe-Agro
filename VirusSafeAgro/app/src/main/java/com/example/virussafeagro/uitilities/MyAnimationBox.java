package com.example.virussafeagro.uitilities;

import android.view.animation.AlphaAnimation;

public class MyAnimationBox {

    public static AlphaAnimation getAlphaAnimationForFadeIn(int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(duration);
        return alphaAnimation;
    }
}
