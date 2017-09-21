package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daria.example.wallpaper.wallpaperhd.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Daria Popova on 21.09.17.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> implements View.OnClickListener{

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
        Collections.sort(mockTags, new Comparator<String>() {
            @Override
            public int compare(String s, String s1) {
                if (s == null || s.length() < 1 || s1 == null || s1.length() < 1)
                    return 0;
                else return Character.compare(s.charAt(0), s1.charAt(0));
            }
        });
    }

    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_layout, parent, false);
        view.setOnClickListener(this);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        holder.tag.setText(mockTags.get(position));
    }

    @Override
    public int getItemCount() {
        return mockTags.size();
    }

    @Override
    public void onClick(View view) {
        TextView tag = view.findViewById(R.id.image_tag);
        if (tag == null || TextUtils.isEmpty(tag.getText().toString()))
            return;
        Toast.makeText(mContext, tag.getText().toString() +
                "'s intent will be shown", Toast.LENGTH_SHORT).show();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        public TextView tag;

        public TagsViewHolder(View view) {
            super(view);
            tag = (TextView) view.findViewById(R.id.image_tag);
        }
    }
}
