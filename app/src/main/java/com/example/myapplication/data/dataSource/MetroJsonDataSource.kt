package data.dataSource

import android.content.Context
import com.google.gson.Gson
import com.example.myapplication.R
import data.models.MetroDto

class MetroJsonDataSource(private val context: Context) : MetroDataSource {
    private val gson = Gson()
    private val dto by lazy {
        val jsonString = context.resources.openRawResource(R.raw.metro_data).bufferedReader().use { it.readText() }
        gson.fromJson(jsonString, MetroDto::class.java)
    }

    override fun loadStations() = dto.stations

    override fun getTravelTime() = dto.travel_time_between_stations_minutes
}
