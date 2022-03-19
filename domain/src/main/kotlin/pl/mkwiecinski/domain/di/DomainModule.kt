package pl.mkwiecinski.domain.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import pl.mkwiecinski.domain.listing.entities.RepositoryOwner

@Module
class DomainModule {

    @Provides
    fun hardcodedUser() = RepositoryOwner(name = "toptal")

    @Provides
    fun dispatcher() = Dispatchers.Default
}
