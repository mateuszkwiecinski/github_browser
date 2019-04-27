package pl.mkwiecinski.presentation.details.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mkwiecinski.presentation.base.ViewModelKey
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

@Module
internal abstract class DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun vm(viewModel: DetailsViewModel): ViewModel
}
