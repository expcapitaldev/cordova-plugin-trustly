interface Cordova {
	plugins: CordovaPlugins;
}

interface CordovaPlugins {
	Trustly: ITrustlyPlugin;
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
	 * Return String, final url from your endUrls
	 */
	startTrustlyFlow(url: string, endUrls: string[]): Promise<string>;
}
