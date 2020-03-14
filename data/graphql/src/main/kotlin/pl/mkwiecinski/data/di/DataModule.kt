package pl.mkwiecinski.data.di

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.data.GraphqlGateway
import pl.mkwiecinski.domain.details.gateways.DetailsGateway
import pl.mkwiecinski.domain.listing.gateways.ListingGateway

@Module(includes = [ConnectionModule::class])
internal abstract class DataModule {

    @Binds
    abstract fun listing(implementation: GraphqlGateway): ListingGateway

    @Binds
    abstract fun details(implementation: GraphqlGateway): DetailsGateway
}
