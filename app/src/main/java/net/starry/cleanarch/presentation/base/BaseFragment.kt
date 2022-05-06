package net.starry.cleanarch.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import net.starry.cleanarch.presentation.model.PopupMessage

abstract class BaseFragment : Fragment() {

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    protected fun showAlertDialog(popup: PopupMessage) {
        AlertDialog.Builder(requireContext())
            .setTitle(popup.title)
            .setMessage(popup.message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    protected fun showRetryDialog(retry: Retry) {
        AlertDialog.Builder(requireContext())
            .setTitle(retry.message.title)
            .setMessage(retry.message.message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, _ ->
                retry.action?.invoke()
                dialog.dismiss()
            }.show()
    }

}