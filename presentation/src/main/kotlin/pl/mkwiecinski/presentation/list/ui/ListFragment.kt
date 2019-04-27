package pl.mkwiecinski.presentation.list.ui

import android.os.Bundle
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.base.BaseFragment
import pl.mkwiecinski.presentation.databinding.FragmentListBinding
import pl.mkwiecinski.presentation.list.vm.ListViewModel

internal class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    override val layoutId = R.layout.fragment_list
    override val viewModelClass = ListViewModel::class

    override fun init(savedInstanceState: Bundle?) = Unit
}
