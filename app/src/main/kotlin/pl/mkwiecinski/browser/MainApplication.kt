package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerDomainDependenciesBuilder
import pl.mkwiecinski.browser.di.DaggerMainComponent
import pl.mkwiecinski.domain.di.DaggerDomainComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()
        val presentation = presentation()

        val domainDependencies = DaggerDomainDependenciesBuilder.factory().create(
            networking = networking,
            presentation = presentation
        )
        val domain = DaggerDomainComponent.factory().create(
            dependencies = domainDependencies
        )

        return DaggerMainComponent.factory().create(
            application = this,
            presentation = presentation
        )
    }

    private fun presentation() =
        DaggerPresentationComponent.factory()
            .create(TODO(), TODO())
}
