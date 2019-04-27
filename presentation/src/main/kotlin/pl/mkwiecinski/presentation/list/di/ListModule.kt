package pl.mkwiecinski.presentation.list.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mkwiecinski.presentation.base.ViewModelKey
import pl.mkwiecinski.presentation.list.vm.ListViewModel

@Module
internal abstract class ListModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun vm(viewModel: ListViewModel): ViewModel
}
