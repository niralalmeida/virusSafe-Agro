package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.TweetModel;
import com.example.virussafeagro.uitilities.DataConverter;

import java.util.List;

public class ListTweetAdapter extends RecyclerView.Adapter<ListTweetAdapter.ViewHolder> {
    private List<TweetModel> tweetModelList;

    private FragmentActivity fragmentActivity;

    public ListTweetAdapter(List<TweetModel> tweetModelList, FragmentActivity fragmentActivity) {
        this.tweetModelList = tweetModelList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout allItemViewsLinearLayout;
        public TextView tweetPublisherTextView;
        public ImageView tweetPortraitImageView;
        public com.uncopt.android.widget.text.justify.JustifiedTextView tweetContentTextView;
        public TextView tweetPublishTimeAgoTextView;
        public ImageView tweetImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.allItemViewsLinearLayout = itemView.findViewById(R.id.ll_item_card_tweet_list);
            this.tweetPublisherTextView = itemView.findViewById(R.id.tv_publisher_tweet_list);
            this.tweetPortraitImageView = itemView.findViewById(R.id.img_portrait_tweet_list);
            this.tweetContentTextView = itemView.findViewById(R.id.tv_content_tweet_list);
            this.tweetPublishTimeAgoTextView = itemView.findViewById(R.id.tv_time_tweet_list);
            this.tweetImageView = itemView.findViewById(R.id.img_item_tweet_list);
        }
    }

    @NonNull
    @Override
    public ListTweetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.recycler_view_item_tweet_list, parent, false);
        ListTweetAdapter.ViewHolder viewHolder = new ListTweetAdapter.ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListTweetAdapter.ViewHolder viewHolder, int position) {
        final TweetModel tweetModel = this.tweetModelList.get(position);

        // tweet portrait
        viewHolder.tweetPortraitImageView.setImageBitmap(tweetModel.getTweetPortraitBitmap());

        // tweet publisher
        viewHolder.tweetPublisherTextView.setText(tweetModel.getTweetPublisher());

        // tweet content
        viewHolder.tweetContentTextView.setText(tweetModel.getTweetContent());

        // tweet press time
        String originalTimeString = tweetModel.getTweetPublishTime(); // "yyyy-MM-dd'T'HH:mm:ssXXX"
        String tweetPressTime = DataConverter.getShortTime(originalTimeString);
        viewHolder.tweetPublishTimeAgoTextView.setText(tweetPressTime);

        // tweet image
        if (tweetModel.getTweetImageBitmap() != null ) {
            viewHolder.tweetImageView.setImageBitmap(tweetModel.getTweetImageBitmap());
        } else {
            tweetModel.setTweetImageBitmap(DataConverter.drawableImageToBitmap(fragmentActivity, R.drawable.agriculture));
        }

    }

    @Override
    public int getItemCount() {
        return this.tweetModelList.size();
    }

    public void addTweetItem(List<TweetModel> moreTweetModelList) {
        for(int i = moreTweetModelList.size() - 1; i >= 0; i--){
            TweetModel moreTweetModel = moreTweetModelList.get(i);
            for (TweetModel tweetModel : this.tweetModelList){
                if (moreTweetModel.getTweetContent().equals(tweetModel.getTweetContent())){
                    moreTweetModelList.remove(moreTweetModel);
                }
            }
        }
        this.tweetModelList.addAll(moreTweetModelList);
        notifyDataSetChanged();
    }
}
