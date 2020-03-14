package pl.mkwiecinski.browser

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.mkwiecinski.browser.di.DaggerDomainDependenciesBuilder
import pl.mkwiecinski.browser.di.DaggerMainComponent
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.di.DaggerDomainComponent
import pl.mkwiecinski.domain.di.DomainComponent
import pl.mkwiecinski.domain.di.DomainDependencies
import pl.mkwiecinski.presentation.di.DaggerPresentationInComponent
import pl.mkwiecinski.presentation.di.DaggerPresentationOutComponent
import pl.mkwiecinski.presentation.di.PresentationOutComponent

internal class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<MainApplication> {
        val networking = createNetworking()
        val presentationOut = presentationOut()

        val domain = DaggerDomainComponent.factory().create(
            dependencies = domainDependencies(
                networking = networking,
                presentationOut = presentationOut
            )
        )

        return DaggerMainComponent.factory().create(
            application = this,
            presentation = presentationIn(domain)
        )
    }

    private fun domainDependencies(networking: NetworkingComponent, presentationOut: PresentationOutComponent): DomainDependencies =
        DaggerDomainDependenciesBuilder.factory().create(
            networking = networking,
            presentation = presentationOut
        )

    private fun presentationOut(): PresentationOutComponent =
        DaggerPresentationOutComponent.create()

    private fun presentationIn(domain: DomainComponent) =
        DaggerPresentationInComponent.factory()
            .create(domain)
}
