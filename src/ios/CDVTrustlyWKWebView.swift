//
//  TrustlyWKWebView.swift
//  trustly-framework-ios
//
//  Created by Team Bulle on 2019-09-04.
//  Copyright © 2019 Trustly. All rights reserved.
//

import UIKit
import WebKit
import SafariServices

public class TrustlyWKWebView: UIView, WKNavigationDelegate, WKUIDelegate, SFSafariViewControllerDelegate {
    var trustlyView: WKWebView?
    var endUrls: [String]
    var completionBlock: ((String) -> Void)

    public init?(checkoutUrl: String, endUrls: [String], frame: CGRect, completionBlock: @escaping ((String) -> Void)) {
        self.endUrls = endUrls
        self.completionBlock = completionBlock
        super.init(frame: frame)

        let userContentController: WKUserContentController = WKUserContentController()
        let configuration: WKWebViewConfiguration = WKWebViewConfiguration()
        configuration.userContentController = userContentController
        configuration.preferences.javaScriptCanOpenWindowsAutomatically = true

        trustlyView = WKWebView(frame: frame, configuration: configuration)
        guard let trustlyView = trustlyView else { return nil }

        trustlyView.navigationDelegate = self
        trustlyView.uiDelegate = self

        userContentController.add(
            TrustlyWKScriptOpenURLScheme(webView: trustlyView), name: TrustlyWKScriptOpenURLScheme.NAME)
        if let url = URL(string: checkoutUrl) {
            trustlyView.load(URLRequest(url: url))
            trustlyView.allowsBackForwardNavigationGestures = true
        }

        addSubview(trustlyView)
    }

    public required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    public func webView(_ webView: WKWebView, createWebViewWith configuration: WKWebViewConfiguration, for navigationAction: WKNavigationAction, windowFeatures: WKWindowFeatures) -> WKWebView? {

        if navigationAction.targetFrame == nil {
            if let parentViewController: UIViewController = UIApplication.shared.keyWindow?.rootViewController,
                let url = navigationAction.request.url {
                    let safariView = SFSafariViewController(url: url)
                    parentViewController.present(safariView, animated: true, completion: nil)
                    safariView.delegate = self
            }
        }

        return nil
    }

    public func webView(_ webView: WKWebView, didCommit navigation: WKNavigation!) {
        if let url = webView.url?.absoluteString, self.endUrls.contains(url) {
            webView.stopLoading()
            self.completionBlock(url)
        }
    }
}
