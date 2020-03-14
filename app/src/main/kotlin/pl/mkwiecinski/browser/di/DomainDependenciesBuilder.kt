package pl.mkwiecinski.browser.di

import dagger.Component
import pl.mkwiecinski.data.di.NetworkingComponent
import pl.mkwiecinski.domain.di.DomainDependencies

@Component(dependencies = [NetworkingComponent::class])
internal interface DomainDependenciesBuilder : DomainDependencies {

    @Component.Factory
    interface Factory {

        fun create(networking: NetworkingComponent): DomainDependenciesBuilder
    }
}
