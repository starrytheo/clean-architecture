package net.starry.cleanarch.presentation.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.starry.cleanarch.databinding.FragmentProfileBinding
import net.starry.cleanarch.presentation.base.BindingFragment

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

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
    }
}

