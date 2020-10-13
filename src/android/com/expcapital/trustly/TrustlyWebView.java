package com.expcapital.trustly;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TrustlyWebView extends WebView {

    public TrustlyWebView(TrustlyActivity activity, String url, String[] endUrls) {
        super(activity);

        try {
            // Enable javascript and DOM Storage
            configWebSettings(this);

            this.setWebViewClient(new TrustlyWebViewClient(activity, endUrls));
            this.setWebChromeClient(new TrustlyWebChromeClient());
            // Add TrustlyJavascriptInterface
            this.addJavascriptInterface(
                    new TrustlyJavascriptInterface(activity), TrustlyJavascriptInterface.NAME);

            this.setLayoutParams(new LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

            this.loadUrl(url);
        } catch (WebSettingsException e) {
            Log.d("WebView", "configWebView: Could not config WebSettings");
        } catch (Exception e) {
            Log.d("WebView", "configWebView: Unknown Problem happened");
        }
    }

    private void configWebSettings(WebView mainView) throws WebSettingsException {
        try {
            WebSettings webSettings = mainView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
        } catch (Exception e) {
            throw new WebSettingsException(e.getMessage());
        }
    }

     private class TrustlyWebViewClient extends WebViewClient {
		TrustlyActivity activity;
		String[] endUrls;

        private static final String LOGTAG = "TrustlyWebViewClient";

		public TrustlyWebViewClient(TrustlyActivity activity, String[] endUrls){
			this.activity = activity;
			this.endUrls = endUrls;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
			Log.d(LOGTAG, String.format("shouldOverrideUrlLoading for url: %s", request.getUrl().toString()));

			for (String endUrl : endUrls) {
				if (request.getUrl().toString().endsWith(endUrl)) {
					Intent result = new Intent();
					result.putExtra(TrustlyActivity.TRUSTLY_EXTRA_FINAL_URL, request.getUrl().toString());
					activity.setResult(Activity.RESULT_OK, result);
					activity.finish();
					return true;
				}
			}
			return false;
		}

	}
}
