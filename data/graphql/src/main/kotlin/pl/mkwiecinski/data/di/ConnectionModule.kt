package pl.mkwiecinski.data.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.CompiledField
import com.apollographql.apollo3.api.Executable
import com.apollographql.apollo3.cache.normalized.api.CacheKey
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo3.cache.normalized.api.CacheKeyResolver
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.TypePolicyCacheKeyGenerator
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
        config: GithubConfig,
    ): ApolloClient =
        ApolloClient.Builder().apply {
            serverUrl(config.url)
            okHttpClient(client)
            dispatcher(dispatcher)
            normalizedCache(
                normalizedCacheFactory = MemoryCacheFactory(maxSizeBytes = CACHE_SIZE),
                cacheResolver = IdBasedCacheKeyResolver,
                cacheKeyGenerator = IdBasedCacheKeyResolver,
            )
            addCustomScalarAdapter(URI.type, UriToStringAdapter)
        }.build()

    companion object {
        private const val CACHE_SIZE = 1024 * 1024
    }
}

internal object IdBasedCacheKeyResolver : CacheKeyResolver(), CacheKeyGenerator {

    override fun cacheKeyForObject(obj: Map<String, Any?>, context: CacheKeyGeneratorContext) =
        obj["id"]?.toString()?.let(::CacheKey) ?: TypePolicyCacheKeyGenerator.cacheKeyForObject(obj, context)

    override fun cacheKeyForField(field: CompiledField, variables: Executable.Variables): CacheKey? =
        (field.resolveArgument("id", variables) as? String)?.let(::CacheKey)
}
