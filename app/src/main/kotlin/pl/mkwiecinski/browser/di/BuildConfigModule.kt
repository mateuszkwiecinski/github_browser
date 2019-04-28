package pl.mkwiecinski.browser.di

import dagger.Module
import dagger.Provides
import pl.mkwiecinski.browser.BuildConfig
import pl.mkwiecinski.data.di.GithubConfig

@Module
internal class BuildConfigModule {

    @Provides
    fun githubConfig() = GithubConfig(
        token = BuildConfig.GITHUB_TOKEN,
        url = BuildConfig.GITHUB_API
    )
}
