package pl.mkwiecinski.browser

import pl.mkwiecinski.data.di.DaggerNetworkingComponent
import pl.mkwiecinski.data.di.GithubConfig
import pl.mkwiecinski.data.di.NetworkingComponent

internal fun createNetworking(): NetworkingComponent {
    val config = GithubConfig(
        token = BuildConfig.GITHUB_TOKEN,
        url = BuildConfig.GITHUB_API
    )

    return DaggerNetworkingComponent.factory().create(config)
}
