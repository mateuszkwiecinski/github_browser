package pl.mkwiecinski.presentation.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.mkwiecinski.presentation.details.di.DetailsModule
import pl.mkwiecinski.presentation.details.ui.DetailsFragment
import pl.mkwiecinski.presentation.list.di.ListModule
import pl.mkwiecinski.presentation.list.ui.ListFragment

@Module
abstract class MainActivityInjectors {

    @ContributesAndroidInjector(modules = [ListModule::class])
    abstract fun list(): ListFragment

    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun details(): DetailsFragment
}
