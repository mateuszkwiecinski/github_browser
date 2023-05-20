package pl.mkwiecinski.browser.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.browser.MainApplication
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.di.DomainModule
import pl.mkwiecinski.presentation.di.PresentationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PresentationModule::class,
        DomainModule::class,
    ],
    dependencies = [
        NetworkingComponent::class,
    ],
)
internal interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: MainApplication, networking: NetworkingComponent): MainComponent
    }
}
