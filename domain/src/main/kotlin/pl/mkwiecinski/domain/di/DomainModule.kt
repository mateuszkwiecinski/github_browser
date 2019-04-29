package pl.mkwiecinski.domain.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import pl.mkwiecinski.domain.listing.persistences.PagingEventsPersistence
import pl.mkwiecinski.domain.listing.paging.PagingSourceFactory
import pl.mkwiecinski.domain.listing.paging.SingleActivePagingSourceFactory

@Module
abstract class DomainModule {

    @Module
    companion object HardCoded {

        @JvmStatic
        @Provides
        fun hardcodedUser() = RepositoryOwner("toptal")
    }

    @Binds
    internal abstract fun paging(implementation: SingleActivePagingSourceFactory): PagingSourceFactory

    @Binds
    internal abstract fun events(implementation: InMemoryPagingEventsPersistence): PagingEventsPersistence
}
