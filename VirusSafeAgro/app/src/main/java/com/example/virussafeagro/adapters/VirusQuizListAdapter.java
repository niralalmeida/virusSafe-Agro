package com.example.virussafeagro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.R;
import com.example.virussafeagro.entities.Virus;

import java.util.List;

public class VirusQuizListAdapter extends RecyclerView.Adapter<VirusQuizListAdapter.ViewHolder> {
    private List<Virus> virusList;
    private FragmentActivity fragmentActivity;
    private VirusQuizListAdapter.VirusQuizButtonClickListener virusQuizButtonClickListener;
    private VirusQuizListAdapter.VirusViewContentButtonClickListener virusViewContentButtonClickListener;

    public VirusQuizListAdapter(List<Virus> virusList, FragmentActivity fragmentActivity) {
        this.virusList = virusList;
        this.fragmentActivity = fragmentActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView virusFullName;
        public Button takeQuizButton;
        public Button viewContentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.virusFullName = itemView.findViewById(R.id.tv_virus_full_name);
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
        final Virus currentVirus = this.virusList.get(position);

        viewHolder.virusFullName.setText(currentVirus.getVirusFullName());

        viewHolder.takeQuizButton.setOnClickListener(v ->{
            virusQuizButtonClickListener.onVirusQuizButtonClick(position);
        });

        viewHolder.viewContentButton.setOnClickListener(v ->{
            virusViewContentButtonClickListener.onVirusQuizButtonClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.virusList.size();
    }
}
