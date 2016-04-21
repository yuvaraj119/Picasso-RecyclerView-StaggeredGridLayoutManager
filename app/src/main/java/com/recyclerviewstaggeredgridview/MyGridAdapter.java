package com.recyclerviewstaggeredgridview;

import com.squareup.picasso.*;
import android.content.*;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView.*;
import android.view.*;
import android.widget.*;

import java.util.*;

/**
 * Created by yuvaraj on 3/4/16.
 */
public class MyGridAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ImageModel> imageModels = new ArrayList<>();
    //Picasso p;

    public MyGridAdapter(Context context) {
        mContext = context;
       /* Picasso p = new Builder(mContext)
                .memoryCache(new LruCache(24000))
                .build();
        p.setIndicatorsEnabled(true);
        p.setLoggingEnabled(true);
        Picasso.setSingletonInstance(p);*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.resizable_grid_item, null);
        MyViewHolder holder = new MyViewHolder(itemView);
        holder.imageView = (DynamicHeightImageView) itemView.findViewById(R.id.dynamic_height_image_view);
        holder.positionTextView = (TextView) itemView.findViewById(R.id.item_position_view);
        itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        MyViewHolder vh = (MyViewHolder) viewHolder;
        ImageModel item = imageModels.get(position);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) vh.imageView.getLayoutParams();
        float ratio = item.getHeight() / item.getWidth();
        rlp.height = (int) (rlp.width * ratio);
        vh.imageView.setLayoutParams(rlp);
        vh.positionTextView.setText("pos: " + position);
        vh.imageView.setRatio(item.getRatio());
        Picasso.with(mContext).load(item.getUrl()).placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position)).into(vh.imageView);
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }


    public void addDrawable(ImageModel imageModel) {
        float ratio = (float) imageModel.getHeight() / (float) imageModel.getWidth();
        imageModel.setRatio(ratio);
        this.imageModels.add(imageModel);
    }
}
