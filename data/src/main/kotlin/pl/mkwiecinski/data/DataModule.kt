package pl.mkwiecinski.data

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway

@Module(includes = [ConnectionModule::class])
abstract class DataModule {

    @Binds
    internal abstract fun listing(implementation: GraphqlGateway): ListingGateway

    @Binds
    internal abstract fun details(implementation: GraphqlGateway): DetailsGateway
}
