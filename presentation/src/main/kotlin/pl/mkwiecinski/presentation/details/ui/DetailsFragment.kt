package pl.mkwiecinski.presentation.details.ui

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentDetailsBinding
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

internal class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layoutId = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class

    internal val args by navArgs<DetailsFragmentArgs>()

    override fun init(savedInstanceState: Bundle?) = Unit
}
