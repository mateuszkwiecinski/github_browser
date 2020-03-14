package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerMainComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()
        val presentation = DaggerPresentationComponent.factory()
            .create(

            )

        return DaggerMainComponent.factory().create(
            application = this,
            networking = networking,
            presentation = presentation
        )
    }
}
