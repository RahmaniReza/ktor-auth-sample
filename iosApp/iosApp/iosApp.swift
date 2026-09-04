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
        MainViewControllerKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
