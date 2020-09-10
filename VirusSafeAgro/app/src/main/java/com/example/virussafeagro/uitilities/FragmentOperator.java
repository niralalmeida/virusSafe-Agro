package com.example.virussafeagro.uitilities;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.virussafeagro.R;

public class FragmentOperator {
    // replace the fragment view with back stack
    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment nextFragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragments, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    // replace the fragment view
    public static void replaceFragmentNoBackStack(FragmentActivity fragmentActivity, Fragment nextFragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragments, nextFragment)
                .commit();
    }

    // go back
    public static void backToLastFragment(FragmentActivity fragmentActivity){
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStackImmediate();
        }
    }
}
