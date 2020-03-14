package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerDomainDependenciesBuilder
import pl.mkwiecinski.browser.di.DaggerMainComponent
import pl.mkwiecinski.domain.di.DaggerDomainComponent
import pl.mkwiecinski.domain.di.DomainComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationInComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationOutComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()
        val presentationOut = presentationOut()

        val domainDependencies = DaggerDomainDependenciesBuilder.factory().create(
            networking = networking,
            presentation = presentationOut
        )
        val domain = DaggerDomainComponent.factory().create(
            dependencies = domainDependencies
        )

        return DaggerMainComponent.factory().create(
            application = this,
            presentation = presentationIn(domain)
        )
    }

    private fun presentationOut() =
        DaggerPresentationOutComponent.create()

    private fun presentationIn(domain: DomainComponent) =
        DaggerPresentationInComponent.factory()
            .create(domain)
}
