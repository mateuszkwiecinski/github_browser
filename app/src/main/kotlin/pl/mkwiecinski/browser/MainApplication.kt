package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerMainComponent
import pl.mkwiecinski.domain.di.DaggerDomainComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()

        val domainDependencies = DaggerDomainDependenceisBuilder.factory().create(
            networking = networking
        )
        val domain = DaggerDomainComponent.factory().create(
            dependencies = domainDependencies
        )
        val presentation = DaggerPresentationComponent.factory()
            .create(
                domain = domain
            )

        return DaggerMainComponent.factory().create(
            application = this,
            presentation = presentation
        )
    }
}
