package pl.mkwiecinski.data

import okhttp3.Interceptor
import okhttp3.Response
import pl.mkwiecinski.data.di.GithubConfig
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val config: GithubConfig
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${config.token}")
            .build()
            .let(chain::proceed)
    }
}
