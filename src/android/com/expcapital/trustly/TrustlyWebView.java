package com.expcapital.trustly;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TrustlyWebView extends WebView {

    public TrustlyWebView(Activity activity, String url) {
        super(activity);

        try {
            // Enable javascript and DOM Storage
            configWebSettings(this);

            this.setWebChromeClient(new TrustlyWebChromeClient());
            // Add TrustlyJavascriptInterface
            this.addJavascriptInterface(new TrustlyJavascriptInterface(activity), TrustlyJavascriptInterface.NAME);

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

}
