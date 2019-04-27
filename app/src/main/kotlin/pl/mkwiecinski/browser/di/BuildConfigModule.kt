package pl.mkwiecinski.browser.di

import dagger.Module
import dagger.Provides
import pl.mkwiecinski.browser.BuildConfig
import javax.inject.Named

@Module
internal class BuildConfigModule {

    @Provides
    @Named("github_token")
    fun githubToken() = BuildConfig.GITHUB_TOKEN
}
