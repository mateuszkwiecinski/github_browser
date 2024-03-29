package pl.mkwiecinski.presentation.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.mkwiecinski.presentation.MainActivity

@Module
abstract class PresentationModule {

    @ContributesAndroidInjector(modules = [MainActivityInjectors::class])
    abstract fun mainActivity(): MainActivity
}
