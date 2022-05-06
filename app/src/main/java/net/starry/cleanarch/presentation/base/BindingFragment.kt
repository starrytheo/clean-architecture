package net.starry.cleanarch.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
) : BaseFragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding
            ?: throw NullPointerException("Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()")

    protected val hasBinding: Boolean get() = _binding != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}