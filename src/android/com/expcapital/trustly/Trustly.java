package com.expcapital.trustly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Trustly extends CordovaPlugin {
	private static final String ACTION_START_TRUSTLY_FLOW = "startTrustlyFlow";
	private static final String LOGTAG = "Trustly";
	private static final int REQUEST_CODE = 0;
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {

		if (ACTION_START_TRUSTLY_FLOW.equals(action)) {
			cordova.getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					startTrustlyFlow(args, callbackContext);
				}
			});
			return true;
		}

		return false;
	}

	private void startTrustlyFlow(CordovaArgs args, CallbackContext callbackContext) {
		Log.d(LOGTAG, String.format("Starting trustly flow with args: %s", args.toString()));

		ArrayList<String> endUrls = new ArrayList<String>();
		String urlString;
		try {
			urlString = args.getString(0);
			new URL(urlString).toURI();

			JSONArray urlsArray = args.getJSONArray(1);

			if (urlsArray.length() == 0) {
				callbackContext.sendPluginResult(makeErrorResult("endUrls needs to be an array with at least one element of type string"));
				return;
			}

			for (int i = 0; i < urlsArray.length(); i++) {
				endUrls.add(urlsArray.getString(i));
			}

		} catch (JSONException e) {
			Log.d(LOGTAG, "Got JSON Exception " + e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
			return;
		} catch (MalformedURLException e) {
			Log.d(LOGTAG, "Malformed URL " + e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.MALFORMED_URL_EXCEPTION));
			return;
		} catch (URISyntaxException e) {
			Log.d(LOGTAG, "URISyntaxException URL " + e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.MALFORMED_URL_EXCEPTION));
			return;
		}

		this.callbackContext = callbackContext;

		Intent trustlyIntent = new Intent(cordova.getActivity(), TrustlyActivity.class);
		trustlyIntent.putExtra(
			TrustlyActivity.TRUSTLY_URL_MESSAGE,
			urlString);
		trustlyIntent.putStringArrayListExtra(
			TrustlyActivity.TRUSTLY_END_URLS_MESSAGE,
			endUrls);

		this.cordova.startActivityForResult(this, trustlyIntent, REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == REQUEST_CODE) {

			switch (resultCode) {

				case TrustlyActivity.RESULT_OK:
					String finalUrl = intent.getStringExtra(TrustlyActivity.TRUSTLY_EXTRA_FINAL_URL);

					PluginResult result = new PluginResult(PluginResult.Status.OK, finalUrl);
					this.callbackContext.sendPluginResult(result);
					break;

				default:
					this.callbackContext.sendPluginResult(makeErrorResult("Unknown error"));
			}
		}
	}

	@Override
	public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
		this.callbackContext = callbackContext;
	}

	private PluginResult makeErrorResult(String message) {
		return makeErrorResult(message, null);
	}

	private PluginResult makeErrorResult(String message, String code) {
		if (code == null) {
			code = "error";
		}

		JSONObject response = new JSONObject();
		try {
			response.put("code", code);
			response.put("message", message);
		} catch (JSONException e) {
			Log.wtf(LOGTAG, "Got JSONException when creating error result", e);
		}
		return new PluginResult(PluginResult.Status.ERROR, response);
	}
}
