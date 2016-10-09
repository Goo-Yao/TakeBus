package com.rdc.takebus.view.CustomView;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {
	private static Toast mToast = null;

	public static void showToast(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setDuration(duration);
			mToast.setText(text);
		}
		mToast.show();
	}
}
