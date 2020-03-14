package pl.mkwiecinski.domain.di

import dagger.BindsInstance
import dagger.Component
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.listing.GetPagedRepositoriesUseCase
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class], dependencies = [DomainDependencies::class])
interface DomainComponent {

    fun getPagedRepositories(): GetPagedRepositoriesUseCase

    fun getDetails(): GetRepositoryDetailsUseCase

    @Component.Factory
    interface Factory {

        fun create(dependencies: DomainDependencies): DomainComponent
    }
}
