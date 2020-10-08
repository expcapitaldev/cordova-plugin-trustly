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

