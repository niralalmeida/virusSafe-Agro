package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class AboutAppFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private LinearLayout allViewLinearLayout;

    public AboutAppFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_about_app, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_about);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set more button
        this.mainActivity.setMoreButton(true);

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // test
//        this.testImageURL();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_about_app);
    }

//
//    // test
//    private void testImageURL(){
//        String imageURL = "https://raw.githubusercontent.com/hoyyang/virusSafe-Agro/master/design%20files/TA24-system%20architecture.png?token=ANAMHQXWB5FBJUGV4MKT5O27N73MK";
//        A a = new A();
//        a.execute(imageURL);
//    }
//
//    // test
//    private class A extends AsyncTask<String, Void, Bitmap>{
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            NetworkConnectionToAWSTomatoS3 networkConnectionToAWSTomatoS3 = new NetworkConnectionToAWSTomatoS3();
//            return networkConnectionToAWSTomatoS3.getImageFromURL(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            ImageView mapImageView = view.findViewById(R.id.img_aus_map_about_app);
//            mapImageView.setImageBitmap(bitmap);
//        }
//    }


    @Override
    public void onPause() {
        super.onPause();

        // set more button
        mainActivity.setMoreButton(false);
    }
}
