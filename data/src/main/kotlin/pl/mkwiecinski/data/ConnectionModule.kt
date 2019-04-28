package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.mkwiecinski.data.adapters.UriToStringAdapter
import pl.mkwiecinski.graphql.type.CustomType
import java.io.File

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
    fun apollo(client: OkHttpClient): ApolloClient =
        ApolloClient.builder().apply {
            serverUrl("https://api.github.com/graphql")
            okHttpClient(client)
            normalizedCache(LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(1024*1024).build()))
            addCustomTypeAdapter(CustomType.URI, UriToStringAdapter)
        }.build()
}
