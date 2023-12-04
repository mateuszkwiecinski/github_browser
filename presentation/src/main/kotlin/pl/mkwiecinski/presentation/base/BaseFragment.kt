package pl.mkwiecinski.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<TBinding : ViewBinding, TViewModel : ViewModel> : DaggerFragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: KClass<TViewModel>

    @Inject
    lateinit var viewModelFactory: ViewModelsFactory

    protected val viewModel: TViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[viewModelClass.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return init(inflater, container, savedInstanceState).root
    }

    abstract fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): TBinding
}
