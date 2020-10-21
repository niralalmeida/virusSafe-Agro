package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.VirusModel;

public class VirusDetailNewFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private VirusModel currentVirusModel;

    // views
    private Button quizButton;
    private ImageView virusPictureImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_detail_new, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_virus_detail_new);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize views
        this.initializeViews();
        // show quiz button in toolbar
        this.quizButton.setVisibility(View.VISIBLE);

        // get passed bundle and the VirusModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentVirusModel = bundle.getParcelable("currentVirusModel");

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set take quiz button on click listener
        this.setTakeQuizButtonOnClickListener();

        // show quiz button
        MyAnimationBox.runFadeInAnimation(this.quizButton, 200);
        new Handler().postDelayed(()->{
            this.mainActivity.moveTipAndMoreToRight(getTag(), 200);
        },200);
    }

    private void initializeViews() {
        this.quizButton = this.mainActivity.getQuizButton();
        this.virusPictureImageView = view.findViewById(R.id.img_picture_virus_detail_new);
    }

    private void setTakeQuizButtonOnClickListener() {
        this.quizButton.setOnClickListener(quizButtonView -> {
            Intent intent = new Intent(mainActivity, QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentVirusModel", currentVirusModel);
            intent.putExtras(bundle);
            // animation
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, virusPictureImageView, ViewCompat.getTransitionName(virusPictureImageView));
            mainActivity.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // hide quiz button
        this.quizButton.setVisibility(View.GONE);

        this.mainActivity.setLearnButton(false);
    }
}
