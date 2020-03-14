package pl.mkwiecinski.browser.di

import dagger.Component
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.di.DomainDependencies
import pl.mkwiecinski.presentation.di.PresentationOutComponent

@Component(dependencies = [NetworkingComponent::class, PresentationOutComponent::class])
internal interface DomainDependenciesBuilder : DomainDependencies {

    @Component.Factory
    interface Factory {

        fun create(
            networking: NetworkingComponent,
            presentation: PresentationOutComponent
        ): DomainDependenciesBuilder
    }
}
