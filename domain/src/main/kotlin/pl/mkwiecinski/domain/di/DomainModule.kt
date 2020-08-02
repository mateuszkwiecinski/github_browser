package pl.mkwiecinski.domain.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
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

        @Provides
        fun dispatcher() = Dispatchers.Default
    }

    @Binds
    internal abstract fun SingleActivePagingSourceFactory.paging(): PagingSourceFactory

    @Binds
    internal abstract fun InMemoryPagingEventsPersistence.events(): PagingEventsPersistence
}
