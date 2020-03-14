package pl.mkwiecinski.presentation.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.domain.di.DomainComponent
import javax.inject.Provider

@PresentationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class
    ],
    dependencies = [DomainComponent::class]
)
interface PresentationInComponent {

    fun androidInjector(): Map<Class<*>, Provider<AndroidInjector.Factory<*>>>

    fun androidInjector2(): Map<String, Provider<AndroidInjector.Factory<*>>>

    @Component.Factory
    interface Factory {

        fun create(domain: DomainComponent): PresentationInComponent
    }
}
