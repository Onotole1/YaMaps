package ru.netology.yamaps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.yamaps.dto.Place
import ru.netology.yamaps.R
import ru.netology.yamaps.databinding.PlaceItemBinding

class PlacesAdapter(
    private val listener: Listener,
) : ListAdapter<Place, PlacesAdapter.PlacesViewHolder>(DiffCallback) {

    interface Listener {
        fun onClick(place: Place)
        fun onDelete(place: Place)
        fun onEdit(place: Place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = PlaceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = PlacesViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                val place = getItem(holder.adapterPosition)
                listener.onClick(place)
            }
            menu.setOnClickListener {
                PopupMenu(root.context, it).apply {
                    inflate(R.menu.place_menu)

                    setOnMenuItemClickListener { item ->
                        val place = getItem(holder.adapterPosition)
                        when (item.itemId) {
                            R.id.delete -> {
                                listener.onDelete(place)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(place)
                                true
                            }
                            else -> false
                        }
                    }

                    show()
                }
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlacesViewHolder(
        private val binding: PlaceItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            with(binding) {
                title.text = place.name
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean =
            oldItem == newItem
    }
}
