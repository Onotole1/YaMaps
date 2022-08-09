package ru.netology.yamaps.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.netology.yamaps.R
import ru.netology.yamaps.viewmodel.MapViewModel
import ru.netology.yamaps.dto.Place

class AddPlaceDialog : DialogFragment() {

    companion object {
        private const val ID_KEY = "ID_KEY"
        private const val LAT_KEY = "LAT_KEY"
        private const val LONG_KEY = "LONG_KEY"
        fun newInstance(lat: Double, long: Double, id: Long? = null) = AddPlaceDialog().apply {
            arguments = bundleOf(LAT_KEY to lat, LONG_KEY to long, ID_KEY to id)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val viewModel by viewModels<MapViewModel>()
        val view = AppCompatEditText(requireContext())
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.enter_name))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val text = view.text?.toString()?.takeIf { it.isNotBlank() } ?: run {
                    Toast.makeText(requireContext(), "Name is empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.insertPlace(
                    Place(
                        id = requireArguments().getSerializable(ID_KEY) as? Long ?: 0,
                        lat = requireArguments().getDouble(LAT_KEY),
                        long = requireArguments().getDouble(LONG_KEY),
                        name = text,
                    )
                )
            }
            .create()
    }
}