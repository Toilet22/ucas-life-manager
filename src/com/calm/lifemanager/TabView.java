package com.calm.lifemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TabView extends LinearLayout {
	ImageView imageView;
	public TabView(Context c, int drawable, int drawableselect){
		super(c);
		imageView = new ImageView(c);
		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(SELECTED_STATE_SET, this.getResources().getDrawable(drawableselect));
		listDrawable.addState(ENABLED_STATE_SET, this.getResources().getDrawable(drawable));
		imageView.setImageDrawable(listDrawable);
		imageView.setBackgroundColor(Color.TRANSPARENT);
		//setGravity(Gravity.CENTER);
		//setGravity(Gravity.LEFT);
		setGravity(Gravity.FILL);
		addView(imageView);
		}
	
	public TabView(Context c, int drawable) {
		super(c);
		imageView = new ImageView(c);
		imageView.setImageDrawable(this.getResources().getDrawable(drawable));
		imageView.setBackgroundColor(Color.TRANSPARENT);
		setGravity(Gravity.FILL);
		addView(imageView);
	}
	
	public TabView(Context c) {
		super(c);
		imageView = new ImageView(c);
		addView(imageView);
	}
}
