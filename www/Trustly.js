'use strict';

var exec = require('cordova/exec');

var TrustlyPlugin = (function () {

	function TrustlyPlugin () {}

	TrustlyPlugin.prototype = {

		startTrustlyFlow: function (arg, endUrls) {
			// waiting for checkout events from ios , see https://github.com/trustly/TrustlyIosSdk/pull/1/files
			return new Promise(function (resolve, reject) {
				exec(resolve, reject, 'Trustly', 'startTrustlyFlow', [arg, endUrls]);
			});
		}

	};
	return TrustlyPlugin;
})();

var Trustly = new TrustlyPlugin();

module.exports = Trustly;
