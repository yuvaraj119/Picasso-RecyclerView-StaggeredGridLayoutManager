package com.recyclerviewstaggeredgridview;

import android.support.v7.widget.RecyclerView.*;
import android.view.*;
import android.widget.*;

/**
 * Created by yuvaraj on 3/4/16.
 */
public class MyViewHolder extends ViewHolder {
	public DynamicHeightImageView imageView;
	public TextView positionTextView;
	public MyViewHolder(View itemView) {
		super(itemView);
	}
}
