package com.daria.example.wallpaper.wallpaperhd.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daria.example.wallpaper.wallpaperhd.R;
import com.daria.example.wallpaper.wallpaperhd.utilities.AppUtils;

import java.util.List;

/**
 * Created by Daria Popova on 21.09.17.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> implements View.OnClickListener {

    private List<String> tags;
    private Context mContext;

    public TagsAdapter(Context mContext, String tag) {
        this.mContext = mContext;
        tags = AppUtils.seperateTags(tag);
    }

    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_tag_instance, parent, false);
        view.setOnClickListener(this);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        holder.tag.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
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
