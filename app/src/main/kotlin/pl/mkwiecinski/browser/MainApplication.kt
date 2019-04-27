package pl.mkwiecinski.browser

import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerMainComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector() =
        DaggerMainComponent.factory().create(this)
}
