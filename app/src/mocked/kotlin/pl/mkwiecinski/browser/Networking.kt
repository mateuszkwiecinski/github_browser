package pl.mkwiecinski.browser

import pl.mkwiecinski.data.di.DaggerNetworkingComponent
import pl.mkwiecinski.data.di.NetworkingComponent

internal fun createNetworking(): NetworkingComponent =
    DaggerNetworkingComponent.create()
