var argscheck = require('cordova/argscheck');
var exec = require('cordova/exec');

var Trustly = {};

Trustly.startTrustlyFlow = function(arg, endUrls, successCallback, failureCallback, opts) {
	opts = opts || {};
	cordova.exec(successCallback, failureCallback,
		'Trustly', 'startTrustlyFlow', [arg, endUrls, opts]);
};

module.exports = Trustly;
