package pl.mkwiecinski.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.mkwiecinski.data.AuthInterceptor
import pl.mkwiecinski.data.adapters.UriToStringAdapter
import pl.mkwiecinski.graphql.type.CustomType

@Module
internal class ConnectionModule {

    @IntoSet
    @Provides
    fun interceptor(authInterceptor: AuthInterceptor): Interceptor =
        authInterceptor

    @Provides
    fun okHttp(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors.forEach { addInterceptor(it) }
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
            dispatcher(dispatcher.asExecutor())
            normalizedCache(
                LruNormalizedCacheFactory(
                    EvictionPolicy.builder().maxSizeBytes(CACHE_SIZE).build()
                )
            )
            addCustomTypeAdapter(CustomType.URI, UriToStringAdapter)
        }.build()

    companion object {
        private const val CACHE_SIZE = 1024 * 1024L
    }
}
