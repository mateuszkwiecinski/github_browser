package pl.mkwiecinski.data

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
    fun apollo(client: OkHttpClient): ApolloClient =
        ApolloClient.builder().apply {
            serverUrl("https://api.github.com/graphql")
            okHttpClient(client)
            addCustomTypeAdapter(CustomType.URI, UriToStringAdapter)
        }.build()
}
