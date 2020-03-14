package pl.mkwiecinski.browser.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import pl.mkwiecinski.browser.MainApplication
import pl.mkwiecinski.presentation.di.PresentationComponent

@ApplicationScope
@Component(dependencies = [PresentationComponent::class])
internal interface MainComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: MainApplication,
            presentation: PresentationComponent
        ): MainComponent
    }
}
