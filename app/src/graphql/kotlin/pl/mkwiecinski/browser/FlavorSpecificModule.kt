package pl.mkwiecinski.browser

import dagger.Module
import dagger.Provides
import pl.mkwiecinski.data.di.DataModule
import pl.mkwiecinski.data.di.GithubConfig

@Module(includes = [DataModule::class])
internal class FlavorSpecificModule {

    @Provides
    fun githubConfig() = GithubConfig(
        token = BuildConfig.GITHUB_TOKEN,
        url = BuildConfig.GITHUB_API
    )
}
