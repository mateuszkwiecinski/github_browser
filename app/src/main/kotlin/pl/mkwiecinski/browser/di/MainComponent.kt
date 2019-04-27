package pl.mkwiecinski.browser.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.browser.MainApplication
import pl.mkwiecinski.presentation.di.PresentationModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class
    ]
)
internal interface MainComponent: AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<MainApplication>
}
