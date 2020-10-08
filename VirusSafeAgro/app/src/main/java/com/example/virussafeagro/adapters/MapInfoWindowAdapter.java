package com.example.virussafeagro.adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.fragments.PesticideStoreMapFragment;
import com.example.virussafeagro.models.PesticideStoreModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View markerInfoView;
    private MainActivity mainActivity;
    private Map<Marker, PesticideStoreModel> pesticideStoreMarkerMap;


    public MapInfoWindowAdapter(MainActivity mainActivity, Map<Marker, PesticideStoreModel>  pesticideStoreMarkerMap) {
        this.mainActivity = mainActivity;
        // get view from the layout file map_marker_info_window
        this.markerInfoView = mainActivity.getLayoutInflater().inflate(R.layout.map_marker_info_window, null);
        this.markerInfoView.setLayoutParams(
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        );
        this.pesticideStoreMarkerMap = pesticideStoreMarkerMap;
    }

    // Use default InfoWindow frame
    @Override
    public View getInfoWindow(Marker marker) {
        return rendTheWindowViews(marker);
    }

    // Defines the contents of the InfoWindow
    @Override
    public View getInfoContents(Marker marker) {
        return rendTheWindowViews(marker);
    }

    private View rendTheWindowViews(Marker marker) {
        // get the pesticideStoreModel
        PesticideStoreModel pesticideStoreModel = this.pesticideStoreMarkerMap.get(marker);

        // store name / user name
        TextView storeNameTextView = this.markerInfoView.findViewById(R.id.tv_store_name_map_window);
        assert pesticideStoreModel != null;
        storeNameTextView.setText(pesticideStoreModel.getStoreName());

        LinearLayout ratingBarLinearLayout = this.markerInfoView.findViewById(R.id.ll_store_rating_map_window);
        LinearLayout isOpenLinearLayout = this.markerInfoView.findViewById(R.id.ll_is_open_now_map_window);
        LinearLayout storeTypeLinearLayout = this.markerInfoView.findViewById(R.id.ll_store_type_tags_map_window);

        if (pesticideStoreModel.getStoreName().equals(PesticideStoreMapFragment.USER_MARKER_NAME_FOR_HASH_MAP)) {

            ratingBarLinearLayout.setVisibility(View.GONE);
            isOpenLinearLayout.setVisibility(View.GONE);
            storeTypeLinearLayout.setVisibility(View.GONE);

        } else {
            ratingBarLinearLayout.setVisibility(View.VISIBLE);
            isOpenLinearLayout.setVisibility(View.VISIBLE);
            storeTypeLinearLayout.setVisibility(View.VISIBLE);

            // store rating
            double ratingDouble = pesticideStoreModel.getRating();
            // rating text view
            String ratingString = Double.toString(ratingDouble);
            TextView ratingTextView = this.markerInfoView.findViewById(R.id.tv_store_rating_map_window);
            ratingTextView.setText(ratingString);
            // rating bar
            RatingBar ratingBar = this.markerInfoView.findViewById(R.id.rb_store_rating_map_window);
            ratingBar.setRating((float) ratingDouble);
            // rating count
            int ratingCount = pesticideStoreModel.getUserRatingsCount();
            TextView ratingCountTextView = this.markerInfoView.findViewById(R.id.tv_store_rating_count_map_window);
            String ratingCountString = "(" + ratingCount + ")";
            ratingCountTextView.setText(ratingCountString);

            // is open now
            TextView isOpenNowTextView = this.markerInfoView.findViewById(R.id.tv_is_open_now_map_window);
            if (pesticideStoreModel.isHasOpeningHours()) {
                if (pesticideStoreModel.isOpenNow()) {
                    isOpenNowTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.is_open));
                    isOpenNowTextView.setText("Open");
                } else {
                    isOpenNowTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.is_closed));
                    isOpenNowTextView.setText("Closed");
                }
            } else { // don't know
                isOpenNowTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.is_unknown));
                isOpenNowTextView.setText("Unknown");
            }

            // business status
            TextView businessStatusTextView = this.markerInfoView.findViewById(R.id.tv_business_status_map_window);
            businessStatusTextView.setText(pesticideStoreModel.getBusinessStatus());

            // store types
            com.uncopt.android.widget.text.justify.JustifiedTextView storesTypeTextView = this.markerInfoView.findViewById(R.id.tv_store_type_tags_map_window);
            StringBuilder storeTypesStringBuilder = new StringBuilder();
            for (String type : pesticideStoreModel.getStoreTypeList()) {
                storeTypesStringBuilder.append("#").append(type).append(",  ");
            }
            storesTypeTextView.setText(storeTypesStringBuilder.toString());

        }

        // return the view containing InfoWindow contents
        return this.markerInfoView;
    }
}
