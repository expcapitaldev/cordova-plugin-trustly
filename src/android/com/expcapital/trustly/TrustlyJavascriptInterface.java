/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.expcapital.trustly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import java.lang.ref.WeakReference;

public class TrustlyJavascriptInterface {

    public static final String NAME = "TrustlyAndroid";
    private WeakReference<Activity> activityRef;


    public TrustlyJavascriptInterface(Activity a) {
        this.activityRef = new WeakReference<>(a);
    }

    /**
     * Will open the URL, then return result
     * @param String packageName
     * @param String URIScheme
     * @return boolean isOpened
     */
    @JavascriptInterface
    public boolean openURLScheme(String packageName, String URIScheme) {

        Activity activity = this.activityRef.get();

        if (activity != null && isPackageInstalledAndEnabled(packageName, activity)) {
            Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URIScheme));
            activity.startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }

    /**
     * Helper function that will verify that URL can be opened, then return result
     * @param String packageName
     * @param Context context
     * @return boolean canBeOpened
     */
    private boolean isPackageInstalledAndEnabled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageName,0);
            return ai.enabled;
        } catch (PackageManager.NameNotFoundException e) {}
        return false;
    }
}
