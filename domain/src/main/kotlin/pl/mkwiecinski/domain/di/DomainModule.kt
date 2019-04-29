package pl.mkwiecinski.domain.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner
import pl.mkwiecinski.domain.listing.paging.PagingSourceFactory
import pl.mkwiecinski.domain.listing.paging.SingleActivePagingSourceFactory
import pl.mkwiecinski.domain.listing.persistences.InMemoryPagingEventsPersistence
import pl.mkwiecinski.domain.listing.persistences.PagingEventsPersistence

@Module
abstract class DomainModule {

    @Module
    companion object Hardcoded {

        @JvmStatic
        @Provides
        fun hardcodedUser() = RepositoryOwner(name = "toptal")
    }

    @Binds
    internal abstract fun paging(implementation: SingleActivePagingSourceFactory): PagingSourceFactory

    @Binds
    internal abstract fun events(implementation: InMemoryPagingEventsPersistence): PagingEventsPersistence
}
