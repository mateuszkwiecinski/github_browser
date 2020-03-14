package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerMainComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()

        return DaggerMainComponent.factory().create(
            application = this,
            networking = networking
        )
    }
}
