package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class QuizQuestionSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    private CardView quizQuestionCardView;
    private TextView quizQuestionContentTextView;
    private ImageView quizQuestionImageView;
    private GridView quizOptionGridView;
    private Button submitAnswerButton;
    private TextView quizQuestionResultTextView;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
