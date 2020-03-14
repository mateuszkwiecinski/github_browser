package pl.mkwiecinski.presentation.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.domain.di.DomainComponent
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder

@PresentationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class
    ],
    dependencies = [
        DomainComponent::class
    ]
)
interface PresentationComponent {

    fun androidInjector(): Map<Class<*>, javax.inject.Provider<AndroidInjector.Factory<*>>>

    fun androidInjector2(): Map<String, javax.inject.Provider<AndroidInjector.Factory<*>>>

    fun listBuilder(): PagedListBuilder

    @Component.Factory
    interface Factory {

        fun create(domain: DomainComponent): PresentationComponent
    }
}
