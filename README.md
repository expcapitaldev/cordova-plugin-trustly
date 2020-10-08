# Cordova Trustly Plugin

---
Start trustly flow in webview.
---

### Supported Platforms

- Android
- iOS

## Types

```

interface Cordova {
	plugins: CordovaPlugins;
}

interface CordovaPlugins {
	trustly: ITrustlyPlugin;
}

interface ITrustlyPluginSuccess {
	finalUrl: string;
}

interface ITrustlyPluginError {
	code: string;
	message: string;
}

interface ITrustlyPlugin {
	startTrustlyFlow(
		url: string,
		endUrls: string[],
		successfulCallback: (result: ITrustlyPluginSuccess) => void,
		errorCallback: (error: ITrustlyPluginError) => void,
	): void;
}

```

## Example

```javascript

cordova.plugins.Trustly.startTrustlyFlow(
    'https://test.trustly.com/demo/deposit?env=live', 
    ["https://my.com/success", "https://my.com/failure"],
    (result: ITrustlyPluginSuccess) => {
        console.log(result.finalUrl);
    }, (error: ITrustlyPluginError) => {
        console.error(error);
    });

```
