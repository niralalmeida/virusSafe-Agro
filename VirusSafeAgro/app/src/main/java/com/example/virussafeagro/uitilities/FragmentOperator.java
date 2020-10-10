package com.example.virussafeagro.uitilities;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.virussafeagro.R;
import com.example.virussafeagro.fragments.LearnFragment;
import com.example.virussafeagro.fragments.ToolkitFragment;

import java.util.List;

public class FragmentOperator {
    // replace the fragment view with back stack
    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragments, nextFragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    // replace the fragment view with fade in animation
    public static void replaceFragmentWithFadeInAnimationNoStack(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fl_fragments, nextFragment, fragmentTag).commit();
    }

    // replace the fragment view with slide in animation
    public static void replaceFragmentWithSlideFromTopAnimation(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, 0, 0, R.anim.exit_to_top);
        fragmentTransaction.replace(R.id.fl_fragments, nextFragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    // replace the fragment view with slide in animation
    public static void replaceFragmentWithSlideFromRightAnimation(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fl_fragments, nextFragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    // replace the fragment view with slide in animation
    public static void replaceFragmentWithSlideFromBottomAnimation(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, 0,0, R.anim.exit_to_bottom);
        fragmentTransaction.replace(R.id.fl_fragments, nextFragment, fragmentTag).addToBackStack(null).commit();
    }

    public static void closeFragmentWithSlideToBottomAnimation(FragmentActivity fragmentActivity, Fragment fragmentToRemove){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(0, R.anim.exit_to_bottom)
                .remove(fragmentToRemove)
                .commit();
    }

    // replace the fragment view
    public static void replaceFragmentNoBackStack(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragments, nextFragment, fragmentTag)
                .commit();
    }

    // go back
    public static void backToLastFragment(FragmentActivity fragmentActivity){
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStackImmediate();
        }
    }

    // pop All Fragments In Stack
    public static void popAllFragmentsInStack(FragmentManager fragmentManager){
        int count = fragmentManager.getBackStackEntryCount();
        while(count > 0){
            fragmentManager.popBackStackImmediate();
            count--;
        }
    }

}
