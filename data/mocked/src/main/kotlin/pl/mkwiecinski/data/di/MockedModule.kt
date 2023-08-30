package pl.mkwiecinski.data.di

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.data.mocked.MockedDetailsGateway
import pl.mkwiecinski.data.mocked.MockedListingGateway
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway

@Module
internal abstract class MockedModule {

    @Binds
    abstract fun listing(impl: MockedListingGateway): ListingGateway

    @Binds
    abstract fun details(impl: MockedDetailsGateway): DetailsGateway
}
