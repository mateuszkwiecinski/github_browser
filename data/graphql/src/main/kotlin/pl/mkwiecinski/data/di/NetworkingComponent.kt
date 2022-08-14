package pl.mkwiecinski.data.di

import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway

@Component(modules = [DataModule::class])
interface NetworkingComponent {

    fun listing(): ListingGateway

    fun details(): DetailsGateway

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance dispatcher: CoroutineDispatcher,
            @BindsInstance config: GithubConfig,
        ): NetworkingComponent
    }
}
