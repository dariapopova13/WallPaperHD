package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daria.example.wallpaper.wallpaperhd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daria Popova on 21.09.17.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {

    private List<String> mockTags;
    private Context mContext;

    public TagsAdapter(Context mContext) {
        this.mContext = mContext;
        mockTags = new ArrayList<>();
        mockTags.add("game of thrones");
        mockTags.add("vikings");
        mockTags.add("new");
        mockTags.add("fashion");
        mockTags.add("birds");
        mockTags.add("girls");
        mockTags.add("cars");
        Collections.sort(mockTags);
    }

    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_layout, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        if (position > mockTags.size()) return;
        holder.tag.setText(mockTags.get(position));
    }

    @Override
    public int getItemCount() {
        return mockTags.size();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        public TextView tag;

        public TagsViewHolder(View view) {
            super(view);
            tag = (TextView) view.findViewById(R.id.image_tag);
        }
    }
}
