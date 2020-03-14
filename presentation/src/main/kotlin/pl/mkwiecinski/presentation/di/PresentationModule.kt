package pl.mkwiecinski.presentation.di

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder
import pl.mkwiecinski.presentation.MainActivity
import pl.mkwiecinski.presentation.base.paging.AndroidPagedListBuilder

@Module
internal abstract class PresentationModule {

    @ContributesAndroidInjector(modules = [MainActivityInjectors::class])
    abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun pagingListProvider(implementation: AndroidPagedListBuilder): PagedListBuilder
}
