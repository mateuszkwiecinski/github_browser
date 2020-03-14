package pl.mkwiecinski.domain.di

import dagger.Component
import pl.mkwiecinski.domain.details.GetRepositoryDetails
import pl.mkwiecinski.domain.listing.GetPagedRepositories
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class], dependencies = [DomainDependencies::class])
interface DomainComponent {

    fun getPagedRepositories(): GetPagedRepositories

    fun getDetails(): GetRepositoryDetails

    @Component.Factory
    interface Factory {

        fun create(dependencies: DomainDependencies): DomainComponent
    }
}
