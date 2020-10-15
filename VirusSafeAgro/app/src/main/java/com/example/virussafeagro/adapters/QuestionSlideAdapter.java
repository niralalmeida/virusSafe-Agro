package com.example.virussafeagro.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.fragments.QuizQuestionFragment;

public class QuestionSlideAdapter extends FragmentStateAdapter {
    public QuestionSlideAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        return new QuizQuestionFragment();
    }

    @Override
    public int getItemCount() {
        return QuizStartActivity.NUM_PAGES;
    }
}
