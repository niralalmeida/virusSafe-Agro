package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;

public class ContactUsFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // data
    public final static String[] OUR_EMAIL = new String[]{"virusSafeAgro@gmail.com"};

    // views
    private TextView emailTextView;


    public ContactUsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_contact_us);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set more button
        this.mainActivity.setMoreButton(true);
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_MORE);

        // set Email TextView On Click Listener
        this.setEmailTextViewOnClickListener();

        return view;
    }

    private void setEmailTextViewOnClickListener() {
        this.emailTextView = view.findViewById(R.id.tv_our_email_contact_us);
        this.emailTextView.setOnClickListener(etv -> {
            sendEmail();
        });
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, OUR_EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, "About virusSafe-Agro");
        if (intent.resolveActivity(mainActivity.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // set more button
        mainActivity.setMoreButton(false);
    }

}

