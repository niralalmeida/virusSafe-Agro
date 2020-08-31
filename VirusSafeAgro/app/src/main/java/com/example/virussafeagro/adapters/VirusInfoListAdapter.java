package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;

import java.util.List;

public class VirusInfoListAdapter extends RecyclerView.Adapter<VirusInfoListAdapter.ViewHolder> {
    private List<VirusModel> virusModelList;
    private FragmentActivity fragmentActivity;
    private VirusInfoListAdapter.VirusCardViewClickListener virusCardViewClickListener;

    public VirusInfoListAdapter(List<VirusModel> virusModelList, FragmentActivity fragmentActivity) {
        this.virusModelList = virusModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView virusCardView;
        public TextView virusFullName;
        public ImageView virusPictureImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.virusCardView = itemView.findViewById(R.id.cv_each_virus_info_list);
            this.virusFullName = itemView.findViewById(R.id.tv_virus_full_name_virus_info_list);
            this.virusPictureImageView = itemView.findViewById(R.id.img_virus_picture_info_list);
        }
    }

    public interface VirusCardViewClickListener{
        void onVirusCardViewClick(int position);
    }
    public void setOnVirusCardViewClickListener(VirusInfoListAdapter.VirusCardViewClickListener virusCardViewClickListener){
        this.virusCardViewClickListener = virusCardViewClickListener;
    }

    @NonNull
    @Override
    public VirusInfoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View virusInfoView = inflater.inflate(R.layout.recycler_view_virus_info_list, parent, false);
        VirusInfoListAdapter.ViewHolder viewHolder = new VirusInfoListAdapter.ViewHolder(virusInfoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VirusInfoListAdapter.ViewHolder viewHolder, int position) {
        final VirusModel currentVirusModel = this.virusModelList.get(position);

        viewHolder.virusFullName.setText(currentVirusModel.getVirusFullName());

        Bitmap bitmap = BitmapFactory.decodeResource(fragmentActivity.getResources(), R.drawable.);
        viewHolder.virusPictureImageView.setImageBitmap();

        viewHolder.virusCardView.setOnClickListener(v ->{
            virusCardViewClickListener.onVirusCardViewClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.virusModelList.size();
    }

}
