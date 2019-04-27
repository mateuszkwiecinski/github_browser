package pl.mkwiecinski.domain.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.mkwiecinski.domain.PagingUseCase
import pl.mkwiecinski.domain.paging.SingleActiveDataSourceFactory
import pl.mkwiecinski.domain.paging.models.RepositoryOwner

@Module
abstract class DomainModule {

    @Module
    companion object HardCoded {

        @JvmStatic
        @Provides
        fun hardcodedUser() = RepositoryOwner("toptal")
    }

    @Binds
    internal abstract fun paging(implementation: SingleActiveDataSourceFactory): PagingUseCase
}
