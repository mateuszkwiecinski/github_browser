package pl.mkwiecinski.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor @Inject constructor(
    @Named("github_token") private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
            .let(chain::proceed)
    }
}
