package pl.mkwiecinski.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.reflect.KClass

internal abstract class BaseFragment<TBinding : ViewDataBinding, TViewModel : ViewModel> : DaggerFragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: KClass<TViewModel>

    protected lateinit var binding: TBinding
        private set

    @Inject
    lateinit var viewModelFactory: ViewModelsFactory

    protected val compositeDisposable = CompositeDisposable()

    protected val viewModel: TViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[viewModelClass.java]
    }

    protected val navController: NavController
        get() = Navigation.findNavController(binding.root)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        init(savedInstanceState)

        return binding.root
    }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
