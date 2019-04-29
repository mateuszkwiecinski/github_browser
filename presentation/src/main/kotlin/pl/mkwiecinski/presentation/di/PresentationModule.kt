package pl.mkwiecinski.presentation.di

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.mkwiecinski.domain.listing.paging.PagedListBuilder
import pl.mkwiecinski.presentation.MainActivity
import pl.mkwiecinski.presentation.base.paging.AndroidPagedListBuilder

@Module
abstract class PresentationModule {

    @ContributesAndroidInjector(modules = [MainActivityInjectors::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    internal abstract fun pagingListProvider(implementation: AndroidPagedListBuilder): PagedListBuilder
}
