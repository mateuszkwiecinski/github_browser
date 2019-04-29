package pl.mkwiecinski.mocked.di

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway
import pl.mkwiecinski.mocked.MockedDetailsGateway
import pl.mkwiecinski.mocked.MockedListingGateway

@Module
abstract class MockedModule {

    @Binds
    internal abstract fun listing(implementation: MockedListingGateway): ListingGateway

    @Binds
    internal abstract fun details(implementation: MockedDetailsGateway): DetailsGateway
}
