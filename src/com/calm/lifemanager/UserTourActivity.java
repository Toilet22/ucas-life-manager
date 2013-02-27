package com.calm.lifemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UserTourActivity extends Activity implements OnClickListener,
		OnPageChangeListener, OnTouchListener {

	private ViewPager viewPager;

	private ViewPagerAdapter viewPagerAdapter;

	private List<View> views;

	private int currentIndex;

	private int lastX = 0;

	private static final int[] pics = { R.drawable.video_demo_today_plan, R.drawable.video_demo_history_record, R.drawable.video_demo_today_summary,
		R.drawable.video_demo_tomorrow_plan, R.drawable.video_demo_good_night };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_tour);
		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			views.add(iv);
		}
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPagerAdapter = new ViewPagerAdapter(views);
		viewPager.setOnTouchListener(this);
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(this);

	}

	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	public void onPageScrollStateChanged(int state) {
	}

	public void onPageScrolled(int index, float arg1, int dis) {
	}

	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if ((lastX - event.getX()) > 100
					&& (currentIndex == views.size() - 1)) {
				//Toast.makeText(this, "在此处添加界面跳转代码哦！", Toast.LENGTH_LONG).show();
				Intent nextIntent = new Intent();
				nextIntent.setClass(UserTourActivity.this, MainActivity.class);
				this.startActivity(nextIntent);
				this.finish();
			}
			break;
		default:
			break;
		}
		return false;
	}

	public void onPageSelected(int index) {
		// TODO Auto-generated method stub
		setCurDot(index);
	}
	
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}
		currentIndex = positon;
	}
}
