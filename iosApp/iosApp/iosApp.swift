//
//  iosAppApp.swift
//  iosApp
//
//  Created by Mohammadreza Rahmani on 03/09/2026.
//

import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinInitializer.shared.init()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
