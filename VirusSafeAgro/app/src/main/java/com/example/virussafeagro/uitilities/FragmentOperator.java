package com.example.virussafeagro.uitilities;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.virussafeagro.R;

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
    public static void replaceFragmentWithFadeAnimation(FragmentActivity fragmentActivity, Fragment nextFragment, String fragmentTag){
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
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

    // remove the fragment view from the stack
    public static void removeFragment(FragmentActivity fragmentActivity, Fragment fragmentToRemove){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .remove(fragmentToRemove)
                .commit();
    }
}
