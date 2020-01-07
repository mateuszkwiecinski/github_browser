package pl.mkwiecinski.browser.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import pl.mkwiecinski.browser.FlavorSpecificModule
import pl.mkwiecinski.browser.MainApplication
import pl.mkwiecinski.domain.di.DomainModule
import pl.mkwiecinski.presentation.di.PresentationModule

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class,
        DomainModule::class,
        FlavorSpecificModule::class
    ]
)
internal interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<MainApplication>
}
