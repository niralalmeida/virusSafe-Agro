package com.example.virussafeagro.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.fragments.QuizQuestionFragment;
import com.example.virussafeagro.fragments.QuizResultFragment;

public class QuestionSlideAdapter extends FragmentStateAdapter {
    public QuestionSlideAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        if (position != QuizStartActivity.NUM_PAGES) { // other fragments
            return new QuizQuestionFragment(position + 1);
        } else {
            return new QuizResultFragment(); // quiz result fragment
        }
    }

    @Override
    public int getItemCount() {
        return QuizStartActivity.NUM_PAGES + 1;
    }
}
