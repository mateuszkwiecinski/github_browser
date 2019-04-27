package pl.mkwiecinski.presentation.details.ui

import android.os.Bundle
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentDetailsBinding
import pl.mkwiecinski.presentation.details.vm.DetailsViewModel

internal class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val layoutId = R.layout.fragment_details
    override val viewModelClass = DetailsViewModel::class

    override fun init(savedInstanceState: Bundle?) = Unit
}
