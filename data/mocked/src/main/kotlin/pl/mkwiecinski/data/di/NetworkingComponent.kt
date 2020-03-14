package pl.mkwiecinski.data.di

import dagger.Component
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway

@Component(modules = [MockedModule::class])
interface NetworkingComponent {

    fun listing(): ListingGateway

    fun details(): DetailsGateway
}
