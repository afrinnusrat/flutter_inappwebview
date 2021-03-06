//
//  URLResponse.swift
//  flutter_inappwebview
//
//  Created by Lorenzo Pichilli on 19/02/21.
//

import Foundation

extension URLResponse {
    public func toMap () -> [String:Any?] {
        return [
            "expectedContentLength": expectedContentLength,
            "mimeType": mimeType,
            "suggestedFilename": suggestedFilename,
            "textEncodingName": textEncodingName,
            "url": url?.absoluteString
        ]
    }
}
