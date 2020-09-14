package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.virussafeagro.R;

public class OnBoardingSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    private ImageView slideImageView;
    private TextView slideHeadingTextView;
    private TextView slideDescriptionTextView;

    // icon id Arrays
    public static int[] slide_images = {
            R.drawable.screen_1_easy_virus_detection,
            R.drawable.screen_2_virus_and_nutrient_deficiency,
            R.drawable.screen_3_news_tweets,
            R.drawable.screen_4_causes
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
            "Upload the image of your tomato plant to \ncheck if it is infected or not.",
            "Read about virus and nutrient deficiencies in tomato plants.",
            "Streaming of latest news updates in the field of agriculture.",
            "Environmental conditions and causes of viral diseases in tomato plants.\n\nChemical and non-chemical strategies to control the spread of virus"
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

        this.initiateViews();
        this.setContentOfViewsByPosition(position);
        container.addView(this.view);

        return this.view;
    }

    private void initiateViews() {
        this.slideImageView = view.findViewById(R.id.img_icon_slide_boarding);
        this.slideHeadingTextView = view.findViewById(R.id.tv_heading_slide_boarding);
        this.slideDescriptionTextView = view.findViewById(R.id.tv_description_slide_boarding);
    }

    private void setContentOfViewsByPosition(int position) {
        this.slideImageView.setImageResource(slide_images[position]);
        this.slideHeadingTextView.setText(slide_headings[position]);
        this.slideDescriptionTextView.setText(slide_descriptions[position]);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
