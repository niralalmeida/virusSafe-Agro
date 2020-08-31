package com.example.virussafeagro.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;

import java.util.List;

public class VirusQuizListAdapter extends RecyclerView.Adapter<VirusQuizListAdapter.ViewHolder> {
    private List<VirusModel> virusModelList;
    private FragmentActivity fragmentActivity;
    private VirusQuizListAdapter.VirusQuizButtonClickListener virusQuizButtonClickListener;
    private VirusQuizListAdapter.VirusViewContentButtonClickListener virusViewContentButtonClickListener;

    public VirusQuizListAdapter(List<VirusModel> virusModelList, FragmentActivity fragmentActivity) {
        this.virusModelList = virusModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView virusFullName;
        public ImageView virusPictureImageView;
        public Button takeQuizButton;
        public Button viewContentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.virusFullName = itemView.findViewById(R.id.tv_virus_full_name_virus_quiz_list);
            this.virusPictureImageView = itemView.findViewById(R.id.img_virus_picture_virus_quiz_list);
            this.takeQuizButton = itemView.findViewById(R.id.btn_take_quiz_virus_quiz_list);
            this.viewContentButton = itemView.findViewById(R.id.btn_view_content_virus_quiz_list);
        }
    }

    public interface VirusViewContentButtonClickListener{
        void onVirusQuizButtonClick(int position);
    }
    public void setOnVirusViewContentButtonClickListener(VirusQuizListAdapter.VirusViewContentButtonClickListener virusViewContentButtonClickListener){
        this.virusViewContentButtonClickListener = virusViewContentButtonClickListener;
    }

    public interface VirusQuizButtonClickListener{
        void onVirusQuizButtonClick(int position);
    }
    public void setOnVirusQuizButtonClickListener(VirusQuizListAdapter.VirusQuizButtonClickListener virusQuizButtonClickListener){
        this.virusQuizButtonClickListener = virusQuizButtonClickListener;
    }

    @NonNull
    @Override
    public VirusQuizListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View virusQuizView = inflater.inflate(R.layout.recycler_view_virus_quiz_list, parent, false);
        VirusQuizListAdapter.ViewHolder viewHolder = new VirusQuizListAdapter.ViewHolder(virusQuizView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VirusQuizListAdapter.ViewHolder viewHolder, int position) {
        final VirusModel currentVirusModel = this.virusModelList.get(position);

        viewHolder.virusFullName.setText(currentVirusModel.getVirusFullName());

        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModel.getVirusId());
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(fragmentActivity.getResources(), virusPictureDrawableId);
        viewHolder.virusPictureImageView.setImageBitmap(virusPictureBitmap);

        viewHolder.takeQuizButton.setOnClickListener(v ->{
            virusQuizButtonClickListener.onVirusQuizButtonClick(position);
        });

        viewHolder.viewContentButton.setOnClickListener(v ->{
            virusViewContentButtonClickListener.onVirusQuizButtonClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.virusModelList.size();
    }
}
