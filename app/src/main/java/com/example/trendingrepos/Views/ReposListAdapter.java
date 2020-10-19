package com.example.trendingrepos.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trendingrepos.Models.RepoModel;
import com.example.trendingrepos.R;

import java.util.ArrayList;
import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.RepoViewHolder> implements Filterable {


    class RepoViewHolder extends RecyclerView.ViewHolder {
        private final TextView author,name,description,language,starCount;
        private  final ImageView avatar,color;

        private RepoViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            language = itemView.findViewById(R.id.language);
            starCount = itemView.findViewById(R.id.starCount);
            avatar = itemView.findViewById(R.id.avatar);
            color = itemView.findViewById(R.id.color);
        }
    }

    private final LayoutInflater mInflater;
    private List<RepoModel> repos;
    private List<RepoModel> filteredrepos;


    public ReposListAdapter(Context context) {
        mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.adapter_repo_item, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        if (repos != null) {
            RepoModel  current = repos.get(position);
            holder.author.setText(""+current.getAuthor());
            holder.name.setText(current.getName());
            holder.description.setText(current.getDescription());
            holder.starCount.setText(current.getStars());

            int strokeWidth = 5;
            int strokeColor = Color.parseColor(current.getLanguageColor());
            int fillColor = Color.parseColor(current.getLanguageColor());
            GradientDrawable gD = new GradientDrawable();
            gD.setColor(fillColor);
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(strokeWidth, strokeColor);
            holder.color.setBackground(gD);

            String imageUrl = current.getAvatar();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.avatar);
        } else {
            // Covers the case of data not being ready yet.
           // holder.userNameView.setText("No Word");
        }
    }
    public void setRepos(List<RepoModel> repos){
        this.repos = repos;
        notifyDataSetChanged();
    }

    public void setFilteredrepos(List<RepoModel> repos){
        this.filteredrepos = repos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (repos != null) {
            return repos.size();
        }else{
            return  0;
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = repos.size();
                    filterResults.values = repos;

                }else{
                    List<RepoModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(RepoModel itemsModel:repos){
                        if(itemsModel.getName().contains(searchStr) || itemsModel.getAuthor().contains(searchStr) || itemsModel.getDescription().contains(searchStr) || itemsModel.getLanguage().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredrepos = (List<RepoModel>) results.values;
                setRepos(filteredrepos);
                notifyDataSetChanged();

            }
        };
        return filter;
    }

}
