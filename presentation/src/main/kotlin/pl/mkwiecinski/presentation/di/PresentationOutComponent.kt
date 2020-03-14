package pl.mkwiecinski.presentation.di

import dagger.Binds
import dagger.Component
import dagger.Module
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder
import pl.mkwiecinski.presentation.base.paging.AndroidPagedListBuilder

@Component(modules = [PagingModule::class])
interface PresentationOutComponent {

    fun listBuilder(): PagedListBuilder
}

@Module
internal abstract class PagingModule {

    @Binds
    abstract fun pagingListProvider(implementation: AndroidPagedListBuilder): PagedListBuilder
}
