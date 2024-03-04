package com.app.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.database.CityDBModel
import com.app.models.CityModel
import com.app.ui.adapters.CityAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CityBottomSheet : BottomSheetDialogFragment() {


    var cityList = ArrayList<CityModel>()
    lateinit var adapter: CityAdapter
    lateinit var recyclerView: RecyclerView
    private var citySelectedListener: CitySelectedListener? = null
    private lateinit var searchEditText: EditText





    interface CitySelectedListener {
        fun onCitySelected(city: CityModel)
    }

    fun setCitySelectedListener(listener: CitySelectedListener) {
        this.citySelectedListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_bottom_sheet_layout, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        adapter = CityAdapter(cityList) { city ->
            citySelectedListener?.onCitySelected(city)
            dismiss()
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

       addCities()

    }


    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }


    private fun onItemClick(cityName: CityDBModel) {
        dismiss()

    }

    private fun addCities() {
        cityList.clear()
        cityList.add(CityModel("Lahore", 29.405327, 71.655458))
        cityList.add(CityModel("Karachi", 24.854684, 67.020706))
        cityList.add(CityModel("Islamabad", 33.693812, 73.065151))
        cityList.add(CityModel("Istanbul", 41.006381, 28.975872))
        cityList.add(CityModel("Shanghai", 31.232344, 121.469102))
        cityList.add(CityModel("Dhaka", 23.764403, 90.389015))
        cityList.add(CityModel("Mumbai", 19.081577, 72.886628))
        cityList.add(CityModel("Dubai", 25.074282, 55.188539))
        cityList.add(CityModel("Delhi", 28.627393, 77.171695))
        cityList.add(CityModel("Jakarta", -6.175247, 106.827049))

    }

}
