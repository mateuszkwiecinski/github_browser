package pl.mkwiecinski.domain.base

import io.reactivex.Observable

interface UseCase<TParam, TResult> {
    operator fun invoke(param: TParam): Observable<TResult>
}

operator fun <T> UseCase<Unit, T>.invoke() =
    invoke(Unit)
