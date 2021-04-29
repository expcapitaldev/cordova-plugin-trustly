package com.expcapital.trustly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

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
		final List<String> endUrls = intent.getStringArrayListExtra(TRUSTLY_END_URLS_MESSAGE);
		Log.d(LOGTAG, String.format("onCreate going to url %s", urlString));

		trustlyView = new TrustlyWebView(this, urlString);
		trustlyView.setWebViewClient(new TrustlyWebViewClient(endUrls));
		setContentView(trustlyView);

	}

	private class TrustlyWebViewClient extends WebViewClient {
		List<String> endUrls;

		private static final String LOGTAG = "TrustlyWebViewClient";

		public TrustlyWebViewClient(List<String> endUrls) {
			this.endUrls = endUrls;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

			String url = request.getUrl().toString();

			Log.d(LOGTAG, String.format("shouldOverrideUrlLoading for url: %s", url));

			for (String endUrl : endUrls) {
				if (url.endsWith(endUrl)) {
					Intent result = new Intent();
					result.putExtra(TrustlyActivity.TRUSTLY_EXTRA_FINAL_URL, url);
					setResult(Activity.RESULT_OK, result);
					finish();
					return true;
				}
			}
			return false;
		}

	}

}
