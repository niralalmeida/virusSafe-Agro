package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.model.VirusModel;

import java.util.List;

public class VirusInfoListAdapter extends RecyclerView.Adapter<VirusInfoListAdapter.ViewHolder> {
    private List<VirusModel> virusList;
    private FragmentActivity fragmentActivity;

    public VirusInfoListAdapter(List<VirusModel> virusList, FragmentActivity fragmentActivity) {
        this.virusList = virusList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv = itemView.findViewById(R.id.tv_text_try);
        }
    }

    @NonNull
    @Override
    public VirusInfoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View moviesView = inflater.inflate(R.layout.recycler_view_virus_info_list, parent, false);
        VirusInfoListAdapter.ViewHolder viewHolder = new VirusInfoListAdapter.ViewHolder(moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VirusModel virus = this.virusList.get(position);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
