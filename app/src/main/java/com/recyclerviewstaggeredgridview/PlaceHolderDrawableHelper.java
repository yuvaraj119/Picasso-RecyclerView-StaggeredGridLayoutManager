package com.recyclerviewstaggeredgridview;

import android.graphics.*;
import android.graphics.drawable.*;

import java.util.*;

/**
 * Created by yuvaraj on 3/4/16.
 */
public class PlaceHolderDrawableHelper {
	static final String placeholderValues[] = {
			"#76A599", "#7D9A96", "#6F8888",
			"#82A1AB", "#8094A1", "#74838C",
			"#B6A688", "#9E9481", "#8D8F9F",
			"#A68C93", "#968388", "#7E808B"};
	static List<Drawable> drawableBackgroundList;

	public static Drawable getBackgroundDrawable(int position) {
		if(drawableBackgroundList==null || drawableBackgroundList.size()==0) {
			drawableBackgroundList = new ArrayList<Drawable>(placeholderValues.length);
			for (int i=0; i<placeholderValues.length; i++) {
				int color = Color.parseColor(placeholderValues[i]);
				drawableBackgroundList.add(new ColorDrawable(color));
			}
		}

		return drawableBackgroundList.get(position % placeholderValues.length);
	}
}
