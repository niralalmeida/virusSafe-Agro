package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class NewsFragment extends Fragment {
    private View view;

    private LinearLayout allViewLinearLayout;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_news, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("News");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show News Views
        this.showNewsViews();

        // test
        WebView webView = view.findViewById(R.id.wv_news);
        webView.loadUrl("https://www.stockandland.com.au/story/6929499/targeted-funding-to-attract-seasonal-workers/");
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news);
    }

    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewLinearLayout, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);
    }
}
