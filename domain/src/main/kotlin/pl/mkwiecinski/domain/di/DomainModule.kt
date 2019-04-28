package pl.mkwiecinski.domain.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.mkwiecinski.domain.listing.PagingUseCase
import pl.mkwiecinski.domain.listing.paging.SingleActiveDataSourceFactory
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

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
