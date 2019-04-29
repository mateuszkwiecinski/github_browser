package pl.mkwiecinski.presentation.list

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.mkwiecinski.domain.di.DomainModule
import pl.mkwiecinski.mocked.di.MockedModule
import pl.mkwiecinski.presentation.di.MainActivityInjectors
import pl.mkwiecinski.presentation.di.PresentationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        DomainModule::class,
        MainActivityInjectors::class,
        PresentationModule::class,
        MockedModule::class
    ]
)
interface PresentationComponent : AndroidInjector<TestApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<TestApplication>
}
