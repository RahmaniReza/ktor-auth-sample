import KoinInitializer.init
import SwiftUI
import Shared

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