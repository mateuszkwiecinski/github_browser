package pl.mkwiecinski.presentation.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.domain.details.GetRepositoryDetailsUseCase
import pl.mkwiecinski.domain.listing.GetPagedRepositoriesUseCase
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class
    ]
)
interface PresentationComponent {

    fun androidInjector(): Map<Class<*>, javax.inject.Provider<AndroidInjector.Factory<*>>>

    fun androidInjector2(): Map<String, javax.inject.Provider<AndroidInjector.Factory<*>>>

    fun listBuilder(): PagedListBuilder


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance getPagedRepositories: GetPagedRepositoriesUseCase,
            @BindsInstance getDetails: GetRepositoryDetailsUseCase
        ): PresentationComponent
    }
}
