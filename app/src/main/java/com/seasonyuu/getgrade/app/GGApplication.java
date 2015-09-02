package com.seasonyuu.getgrade.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.seasonyuu.getgrade.R;

/**
 * Created by seasonyuu on 15/8/28.
 */
public class GGApplication extends Application {
	public static String cookie;
	public static String userName;
	public static String viewState;
	private SharedPreferences sp;

	private static GGApplication mApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		sp = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
	}

	public static GGApplication getInstance(){
		return mApplication;
	}

	public boolean isUseDx() {
		return sp.getBoolean(getString(R.string.use_dx_key), false);
	}
}
