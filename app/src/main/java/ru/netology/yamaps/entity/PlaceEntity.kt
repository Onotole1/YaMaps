package ru.netology.yamaps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.yamaps.dto.Place

@Entity
data class PlaceEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val name: String,
) {
    companion object {
        fun fromDto(dto: Place): PlaceEntity = with(dto) {
            PlaceEntity(id = id, latitude = lat, longitude = long, name = name)
        }
    }

    fun toDto(): Place = Place(id = id, lat = latitude, long = longitude, name = name)
}
