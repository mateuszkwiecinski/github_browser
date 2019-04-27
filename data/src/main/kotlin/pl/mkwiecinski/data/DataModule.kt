package pl.mkwiecinski.data

import dagger.Binds
import dagger.Module
import pl.mkwiecinski.domain.gateways.RepoGateway

@Module(includes = [ConnectionModule::class])
abstract class DataModule {

    @Binds
    internal abstract fun gateway(implementation: GraphqlGateway): RepoGateway
}
