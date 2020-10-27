package com.example.virussafeagro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.DataConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListImageGalleryAdapter extends RecyclerView.Adapter<ListImageGalleryAdapter.ViewHolder> {
    private List<String> virusPictureURLList;
    private Activity activity;
    private ListImageGalleryAdapter.ImageCardClickListener imageCardClickListener;

    public ListImageGalleryAdapter(List<String> virusPictureURLList, Activity activity) {
        this.virusPictureURLList = virusPictureURLList;
        this.activity = activity;
    }

    // image card item on click listener interface
    public interface ImageCardClickListener{
        void onImageCardClick(int position);
    }
    public void setOnImageCardClickListener(ListImageGalleryAdapter.ImageCardClickListener imageCardClickListener){
        this.imageCardClickListener = imageCardClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView virusImageCardView;
        public ImageView virusImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.virusImageCardView = itemView.findViewById(R.id.cv_item_image_gallery);
            this.virusImageView = itemView.findViewById(R.id.img_item_image_gallery);
        }
    }

    @NonNull
    @Override
    public ListImageGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View virusImageView = inflater.inflate(R.layout.recycler_view_item_image_gallery, parent, false);
        ListImageGalleryAdapter.ViewHolder viewHolder = new ListImageGalleryAdapter.ViewHolder(virusImageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListImageGalleryAdapter.ViewHolder viewHolder, int position) {
        final String virusPictureURLString = this.virusPictureURLList.get(position);

        // virus image view
        Picasso.get()
                .load(virusPictureURLString)
                .placeholder(R.color.bg_cart_image_gallery)
                .resize(
                        DataConverter.dip2px(activity, 400),
                        DataConverter.dip2px(activity, 400))
                .centerCrop()
                .into(viewHolder.virusImageView);

        // image card on click listener
        viewHolder.virusImageCardView.setOnClickListener(v -> imageCardClickListener.onImageCardClick(position));
    }

    @Override
    public int getItemCount() {
        return this.virusPictureURLList.size();
    }


}