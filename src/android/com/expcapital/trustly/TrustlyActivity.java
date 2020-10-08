package com.expcapital.trustly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TrustlyActivity extends Activity {
    public static final String TRUSTLY_URL_MESSAGE = "TrustlyActivity.URL_MESSAGE";
    public static final String TRUSTLY_END_URLS_MESSAGE = "TrustlyActivity.END_URLS_MESSAGE";
    public static final String TRUSTLY_EXTRA_FINAL_URL = "finalUrl";
    private static final String LOGTAG = "TrustlyActivity";
    TrustlyWebView trustlyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String urlString = intent.getStringExtra(TRUSTLY_URL_MESSAGE);
		final String[] endUrls = intent.getStringArrayExtra(TRUSTLY_END_URLS_MESSAGE);
		Log.d(LOGTAG, String.format("onCreate going to url %s", urlString));

		trustlyView = new TrustlyWebView(this, urlString, endUrls);

		setContentView(trustlyView);

    }

}
