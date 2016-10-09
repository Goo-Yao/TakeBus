package com.rdc.takebus.view.CustomView;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


public class CreamListView extends ListView {
	public CreamListView(Context context) {
		// TODO Auto-generated method stub
		super(context);
	}

	public CreamListView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		super(context, attrs);
	}

	public CreamListView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated method stub
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
