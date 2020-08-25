package com.example.virussafeagro.uitilities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentOperator {
    // replace the fragment view
    public static void replaceFragmentNoBackStack(FragmentActivity fragmentActivity, Fragment nextFragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_wrapper, nextFragment)
                .commit();
    }
}
