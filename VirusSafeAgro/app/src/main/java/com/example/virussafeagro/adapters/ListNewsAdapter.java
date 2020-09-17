package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.uitilities.DataComparison;

import java.util.List;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder> {
    private List<NewsModel> newsModelList;

    private FragmentActivity fragmentActivity;
    private ListNewsAdapter.NewsTileClickListener newsTileClickListener;

    public ListNewsAdapter(List<NewsModel> newsModelList, FragmentActivity fragmentActivity) {
        this.newsModelList = newsModelList;
        this.fragmentActivity = fragmentActivity;
        }

    public interface NewsTileClickListener{
        void onNewsTileClick(int position);
    }
    public void setOnNewsTileClickListener(ListNewsAdapter.NewsTileClickListener newsTileClickListener){
        this.newsTileClickListener = newsTileClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout allItemViewsLinearLayout;
        public TextView newsTitleTextView;
        public TextView newsSnippetTextView;
        public TextView newsPressTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.allItemViewsLinearLayout = itemView.findViewById(R.id.ll_item_card_news_list);
            this.newsTitleTextView = itemView.findViewById(R.id.tv_title_news_list);
            this.newsSnippetTextView = itemView.findViewById(R.id.tv_snippet_news_list);
            this.newsPressTimeTextView = itemView.findViewById(R.id.tv_time_news_list);
        }
    }

    @NonNull
    @Override
    public ListNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.recycler_view_item_news_list, parent, false);
        ListNewsAdapter.ViewHolder viewHolder = new ListNewsAdapter.ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsAdapter.ViewHolder viewHolder, int position) {
        final NewsModel newsModel = this.newsModelList.get(position);

        // news title
        viewHolder.newsTitleTextView.setText(newsModel.getNewsTitle());

        // news snippet
        viewHolder.newsSnippetTextView.setText(newsModel.getNewsSnippet());

        // news press time
        viewHolder.newsPressTimeTextView.setText(newsModel.getNewsPressTime());

        // news tile on click listener
        viewHolder.newsPressTimeTextView.setOnClickListener(v -> newsTileClickListener.onNewsTileClick(position));
    }

    @Override
    public int getItemCount() {
        return this.newsModelList.size();
    }
}
