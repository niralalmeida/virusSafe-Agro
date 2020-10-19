package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.virussafeagro.R;

public class OnBoardingSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    private ImageView slideImageView;
    private TextView slideHeadingTextView;
    private TextView slideDescriptionTextView;
    private Button launchAppButton;

    // icon id Arrays
    public static int[] slide_images = {
            R.drawable.iteration3screen1,
            R.drawable.iteration3screen2,
            R.drawable.iteration3screen3,
            R.drawable.iteration3screen4
    };
    // String heading Arrays
    public static String[] slide_headings = {
        "Easy Virus Detection",
        "Viruses and Nutrient Deficiency",
        "News and Tweets",
        "Causes and Control Strategies"
    };
    // String description Arrays
    public static String[] slide_descriptions = {
        "Let’s detect viruses in tomato plants by uploading a snap of a tomato leaf.",
        "One-stop to know about viruses, nutrient deficiencies, and tips to grow tomato plants.",
        "Know about the causes, prevention, control strategies, pesticide stores, and much more.",
        "Check your knowledge, read about the news updates, and tweets in the farming industry."
    };

    public OnBoardingSlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        this.view = layoutInflater.inflate(R.layout.slide_on_boarding, container, false);

        // initialize views
        this.initiateViews();
        this.setContentOfViewsByPosition(position);
        container.addView(this.view);

        return this.view;
    }

    private void initiateViews() {
        this.slideImageView = view.findViewById(R.id.img_icon_slide_boarding);
        this.slideHeadingTextView = view.findViewById(R.id.tv_heading_slide_boarding);
        this.slideDescriptionTextView = view.findViewById(R.id.tv_description_slide_boarding);
        this.launchAppButton = view.findViewById(R.id.btn_launch_app_boarding);
    }

    private void setContentOfViewsByPosition(int position) {
        this.slideImageView.setImageResource(slide_images[position]);
        this.slideHeadingTextView.setText(slide_headings[position]);
        this.slideDescriptionTextView.setText(slide_descriptions[position]);
        if (position == slide_headings.length - 1){
            this.launchAppButton.setVisibility(View.VISIBLE);
        } else {
            this.launchAppButton.setVisibility(View.GONE);
        }
    }

    public Button getLaunchAppButton() {
        return launchAppButton;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
