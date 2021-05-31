package com.handmark.pulltorefresh.library.internal;

import android.os.Environment;
import android.util.Log;

public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

	/**
	 * 获取外部存储的路径
	 *
	 * @return null 没有外部存储
	 */
	public static String getExtStorePath() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return null;
	}
}
