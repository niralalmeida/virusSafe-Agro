package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;

import java.util.List;

public class GridVirusInfoAdapter extends BaseAdapter {

    private FragmentActivity fragmentActivity;
    private LayoutInflater layoutInflater;

    private List<VirusModel> virusModelInfoList;

    private ImageView virusImageView;
    private TextView virusFullNameTextView;

    public GridVirusInfoAdapter(FragmentActivity fragmentActivity, List<VirusModel> virusModelInfoList) {
        this.fragmentActivity = fragmentActivity;
        this.layoutInflater = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return virusModelInfoList.size();
    }

    @Override
    public VirusModel getItem(int position) {
        return virusModelInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item_card_virus_info, parent, false);

            // initialize views
            this.virusImageView = convertView.findViewById(R.id.img_option_grid_item);
            this.virusFullNameTextView = convertView.findViewById(R.id.tv_full_name_virus_info_list);

            // get virus model
            VirusModel virusModel = virusModelInfoList.get(position);

            // set virus image
            int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(virusModel.getVirusId());
            Bitmap virusPictureBitmap = BitmapFactory.decodeResource(fragmentActivity.getResources(), virusPictureDrawableId);
            this.virusImageView.setImageBitmap(virusPictureBitmap);

            // set virus full name
            this.virusFullNameTextView.setText(virusModel.getVirusFullName());

        }
        return convertView;
    }
}

