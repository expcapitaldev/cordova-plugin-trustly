@objc(TrustlyPlugin) class TrustlyPlugin : CDVPlugin {
    weak var wkWebviewController: UIViewController?

	@objc(startTrustlyFlow:)
	func startTrustlyFlow(command: CDVInvokedUrlCommand) {
       if let url = (command.arguments[0] as? String), let endUrls = (command.arguments[1] as? [String]), !endUrls.isEmpty {
            let bounds = self.viewController.view.bounds;
        let mainView = TrustlyWKWebView(checkoutUrl: url, endUrls: endUrls, frame: bounds, completionBlock: { [weak self] url in
            let callbackId: String = command.callbackId
            let result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: url)
            self?.commandDelegate.send(result, callbackId: callbackId)
            self?.wkWebviewController?.dismiss(animated: true, completion: nil)
        });
           //  self.viewController.view = mainView;
            let controller = UIViewController()
            controller.view = mainView;
            controller.modalPresentationStyle = .fullScreen
            self.viewController.present(controller, animated: true, completion: nil)
            wkWebviewController = controller
        } else {
            // send error
            let callbackId: String = command.callbackId
            let result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: ["code": "error", "message": "input params not valid"])
            self.commandDelegate.send(result, callbackId: callbackId)
            self.viewController.dismiss(animated: true, completion: nil)
        }
	}
}
