package net.starry.cleanarch.presentation.home

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.starry.cleanarch.databinding.FragmentHomeBinding
import net.starry.cleanarch.presentation.base.BindingFragment


@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUI()
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            btnSearch.setOnClickListener {
                etQuery.editableText.toString().let {
                    if(it.isNotEmpty()) {
                        viewModel.getGitUserRepo(it)
                    }
                }
            }
            etQuery.requestFocus()
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            refreshListData.observe(viewLifecycleOwner) {
                binding.rvRepo.adapter = HomeListAdapter(
                    it,
                    viewModel::onClickUser,
                    viewModel::onClickRepo
                )
            }

            showToast.observe(viewLifecycleOwner) {
                showToast(it)
            }

            showAlertDialog.observe(viewLifecycleOwner) {
                showAlertDialog(it)
            }

            showRetryDialog.observe(viewLifecycleOwner) {
                showRetryDialog(it)
            }

            showProgress.observe(viewLifecycleOwner) {
                binding.layoutProgress.isVisible = it
            }
        }
    }
}

