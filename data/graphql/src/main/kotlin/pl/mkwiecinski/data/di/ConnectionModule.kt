package pl.mkwiecinski.data.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.mkwiecinski.data.AuthInterceptor
import pl.mkwiecinski.data.adapters.UriToStringAdapter
import pl.mkwiecinski.graphql.type.URI

@Module
internal class ConnectionModule {

    @IntoSet
    @Provides
    fun interceptor(authInterceptor: AuthInterceptor): Interceptor =
        authInterceptor

    @Provides
    fun okHttp(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors.forEach(::addInterceptor)
        }.build()

    @Provides
    @Reusable
    fun apollo(
        client: OkHttpClient,
        dispatcher: CoroutineDispatcher,
        config: GithubConfig
    ): ApolloClient =
        ApolloClient.builder().apply {
            serverUrl(config.url)
            okHttpClient(client)
            requestedDispatcher(dispatcher)
            normalizedCache(MemoryCacheFactory(maxSizeBytes = CACHE_SIZE))
            addCustomScalarAdapter(URI.type, UriToStringAdapter)
        }.build()

    companion object {
        private const val CACHE_SIZE = 1024 * 1024
    }
}
