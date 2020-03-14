package pl.mkwiecinski.browser.di

import dagger.Component
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.di.DomainDependencies
import pl.mkwiecinski.presentation.di.PresentationComponent

@Component(dependencies = [NetworkingComponent::class, PresentationComponent::class])
internal interface DomainDependenciesBuilder : DomainDependencies {

    @Component.Factory
    interface Factory {

        fun create(
            networking: NetworkingComponent,
            presentation: PresentationComponent
        ): DomainDependenciesBuilder
    }
}
