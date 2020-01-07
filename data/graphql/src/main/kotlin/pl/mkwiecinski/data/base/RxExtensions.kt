package pl.mkwiecinski.data.base

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException

internal fun <T> ApolloCall<T>.rxEnqueue(): Single<T> =
    Single.create<T> { emitter ->
        enqueue(object : ApolloCall.Callback<T>() {
            override fun onFailure(exception: ApolloException) {
                emitter.onError(exception)
            }

            override fun onResponse(response: Response<T>) {
                response.data()?.let(emitter::onSuccess)
                    ?: emitter.onError(response.errors().toException())
            }
        })
        emitter.setCancellable { cancel() }
    }

private fun MutableList<Error>.toException(): Throwable =
    CompositeException(map { ApolloException(it.message()) })
