package ru.netology.yamaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import ru.netology.yamaps.R
import ru.netology.yamaps.viewmodel.MapViewModel
import ru.netology.yamaps.dto.Place
import ru.netology.yamaps.adapter.PlacesAdapter
import ru.netology.yamaps.databinding.PlacesFragmentBinding

class PlacesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PlacesFragmentBinding.inflate(inflater, container, false)

        val viewModel by viewModels<MapViewModel>()

        val adapter = PlacesAdapter(object : PlacesAdapter.Listener {

            override fun onClick(place: Place) {
                findNavController().navigate(
                    R.id.action_placesFragment_to_mapFragment, bundleOf(
                        MapFragment.LAT_KEY to place.lat,
                        MapFragment.LONG_KEY to place.long
                    )
                )
            }

            override fun onDelete(place: Place) {
                viewModel.deletePlaceById(place.id)
            }

            override fun onEdit(place: Place) {
                AddPlaceDialog.newInstance(lat = place.lat, long = place.long, id = place.id)
                    .show(childFragmentManager, null)
            }
        })

        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.places.collectLatest { places ->
                adapter.submitList(places)
                binding.empty.isVisible = places.isEmpty()
            }
        }

        return binding.root
    }
}
