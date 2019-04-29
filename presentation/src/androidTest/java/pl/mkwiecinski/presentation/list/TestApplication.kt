package pl.mkwiecinski.presentation.list

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerPresentationComponent.factory().create(this)
}
