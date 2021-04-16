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

/**
 * Common error from ITrustlyPlugin
 */
interface ITrustlyPluginError {
	code: string;
	message: string;
}

interface ITrustlyPlugin {
	/**
	 *
	 * @param url
	 * @param endUrls
	 *
	 * Return final url from your endUrls, String
	 */
	startTrustlyFlow(url: string, endUrls: string[]): Promise<string>;
}

```

## Example

```typescript

cordova.plugins.Trustly.startTrustlyFlow(
    'https://test.trustly.com/demo/deposit?env=live', 
    ["https://my.com/success", "https://my.com/failure"])
        .then((url: string) => {
            console.log(url);
        })
        .catch((err: ITrustlyPluginError) => console.error(err));
```
