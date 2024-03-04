package com.app.ui.sidenavigation.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.res.Resources
import android.graphics.Outline
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment
import com.app.bottomsheets.CityBottomSheet
import com.app.broadcast.NetworkCallbackImpl
import com.app.models.CityModel
import com.app.models.prayer.Data
import com.app.network.Resource
import com.app.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.Duration

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    CityBottomSheet.CitySelectedListener {


    var year: Int = 2024
    var month: Int = 2
    var dayOfMonth = 12
    var locationLat: Double = 29.3544
    var locationLng: Double = 71.6911
    private lateinit var connectivityManager: ConnectivityManager
    private var networkCallback: NetworkCallbackImpl? = null
    private var datePickerDialog: DatePickerDialog? = null

    var cityList = ArrayList<CityModel>()


    override val mViewModel: HomeViewModel by viewModels()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.aladhan.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = null

    override fun getToolbarTitle() = R.string.menu_home

    override fun isMenuButton() = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupUI(savedInstanceState: Bundle?) {
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            fetchData()
            callPrayerTiming()
            setUpNetworkCallback()
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setUpNetworkCallback() {
        connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = NetworkCallbackImpl { isConnected ->
            if (isConnected) {
                Toast.makeText(requireContext(), "Network connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Network disconnected", Toast.LENGTH_SHORT).show()
            }
        }

        registerNetworkCallback()

    }


    private fun registerNetworkCallback() {
        networkCallback?.let { connectivityManager.registerDefaultNetworkCallback(it) }
    }

    private fun unregisterNetworkCallback() {
        networkCallback?.let { connectivityManager.unregisterNetworkCallback(it) }
    }


    private fun fetchData() {
        val apiUrl =
            "https://api.aladhan.com/v1/calendar/$year/$month?latitude=$locationLat&longitude=$locationLng"
        mViewModel.callGetPrayerData(apiUrl)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callPrayerTiming() {
        mViewModel.prayerResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showProgressDialog()
                }

                is Resource.Success -> {
                    hideProgressDialog()
                    val response = resource.value

                    val timeList = response.data
                    //add null check here
                    if (!timeList.isNullOrEmpty()) {
                        //Current date day
                        val currentDate = LocalDate.now().dayOfMonth
                        val todayTimeDates = timeList.find {
                            it.date?.gregorian?.day?.toIntOrNull() == currentDate
                        }
                        todayTimeDates?.let { populateTodayTimes(it) }

                        //val todayTimeDates = timeList.get(current-1)
                        // populateTodayTimes(todayTimeDates)
                    }


                    // adapter.updateAdapter(response.data as ArrayList<Data>)
                }

                is Resource.Failure -> {
                    hideProgressDialog()
                    Toast.makeText(mainActivity, resource.errorString, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }

        }
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateTodayTimes(todayTimeDates: Data) {
        val prayerTimings = todayTimeDates.timings
        val date = todayTimeDates.date
        prayerTimings?.let { model ->
            with(model) {
                mViewBinding.fajrtiming.text = formatTime(fajr)
                mViewBinding.sunrisetiming.text = formatTime(sunrise)
                mViewBinding.dhuhrtiming.text = formatTime(dhuhr)
                mViewBinding.asrtiming.text = formatTime(asr)
                mViewBinding.maghribtiming.text =formatTime(maghrib)
                mViewBinding.ishaTiming.text = formatTime(isha)
                mViewBinding.sunsetTiming.text = formatTime(sunset)
                mViewBinding.midnightTiming.text =formatTime(midnight)


                val currentTime = LocalTime.now()

                // Set colors based on prayer times
                setPrayerLayoutColor(mViewBinding.Fajr, fajr, currentTime)
                setPrayerLayoutColor(mViewBinding.dhuhr, dhuhr, currentTime)
                setPrayerLayoutColor(mViewBinding.Asr, asr, currentTime)
                setPrayerLayoutColor(mViewBinding.Maghrib, maghrib, currentTime)
                setPrayerLayoutColor(mViewBinding.Isha, isha, currentTime)

                //displayRemainingTimeForNextPrayer(model, currentTime)

            }


        }

        date?.let { model ->
            with(model) {
                mViewBinding.weekDay.text = gregorian?.weekday?.en
                mViewBinding.dateText.text = gregorian?.day
                mViewBinding.monthText.text = gregorian?.month?.en
                mViewBinding.yearText.text = gregorian?.year
                mViewBinding.weekDayAr.text = hijri?.weekday?.ar
                mViewBinding.dateTextAr.text = hijri?.day
                mViewBinding.monthTextAr.text = hijri?.month?.ar
                mViewBinding.yearTextAr.text = hijri?.year

            }
        }

        prayerTimings?.let { timings ->
            val prayerDateTimeMap = mapOf(
                "Fajr" to timings.fajr,
                "Dhuhr" to timings.dhuhr,
                "Asr" to timings.asr,
                "Maghrib" to timings.maghrib,
                "Isha" to timings.isha
            )

            val currentDateTime = LocalDateTime.now()
            var nextPrayerName: String? = null
            var nextPrayerTime: String? = null
            var minRemainingTime = Long.MAX_VALUE

            // Find the next upcoming prayer
            for ((prayerName, prayerTime) in prayerDateTimeMap) {
                prayerTime?.let {
                    val timePart = it.substringBefore(" ")
                    val prayerDateTime = LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"))
                    )

                    val remainingTime =
                        Duration.between(currentDateTime, prayerDateTime).toMinutes()

                    if (remainingTime > 0 && remainingTime < minRemainingTime) {
                        nextPrayerName = prayerName
                        nextPrayerTime = it
                        minRemainingTime = remainingTime
                    }
                }
            }



            mViewBinding.fajRemainingTime.visibility =
                if (nextPrayerName == "Fajr") View.VISIBLE else View.INVISIBLE
            mViewBinding.dhuhrRemainingTime.visibility =
                if (nextPrayerName == "Dhuhr") View.VISIBLE else View.INVISIBLE
            mViewBinding.asrRemainingTime.visibility =
                if (nextPrayerName == "Asr") View.VISIBLE else View.INVISIBLE
            mViewBinding.maghribRemainingTime.visibility =
                if (nextPrayerName == "Maghrib") View.VISIBLE else View.INVISIBLE
            mViewBinding.ishaRemainingTime.visibility =
                if (nextPrayerName == "Isha") View.VISIBLE else View.INVISIBLE


            nextPrayerName?.let { prayerName ->
                nextPrayerTime?.let { prayerTime ->
                    val timePart = prayerTime.substringBefore(" ")
                    val prayerDateTime = LocalDateTime.of(
                        LocalDate.now(),
                        LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"))
                    )


                    // HH:mm -> hh:mm:aa
                    val remainingTime =
                        Duration.between(currentDateTime, prayerDateTime).toMinutes()

                    when (prayerName) {
                        "Fajr" -> mViewBinding.fajRemainingTime.text =
                            formatRemainingTime(remainingTime)

                        "Dhuhr" -> mViewBinding.dhuhrRemainingTime.text =
                            formatRemainingTime(remainingTime)

                        "Asr" -> mViewBinding.asrRemainingTime.text =
                            formatRemainingTime(remainingTime)

                        "Maghrib" -> mViewBinding.maghribRemainingTime.text =
                            formatRemainingTime(remainingTime)

                        "Isha" -> mViewBinding.ishaRemainingTime.text =
                            formatRemainingTime(remainingTime)
                    }
                }
            }
        }
    }

    private fun formatTime(time: String?): String {
        time?.let {
            val parser = SimpleDateFormat("HH:mm", Locale.getDefault())
            val formatter = SimpleDateFormat("hh:mm aa", Locale.getDefault())
            return formatter.format(parser.parse(it)!!)
        }
        return ""
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPrayerLayoutColor(layout: View, prayerTime: String?, currentTime: LocalTime) {
        val cornerRadius = dpToPx(10).toFloat()
        layout.clipToOutline = true

        prayerTime?.let {
            val timePart = it.substringBefore(" ")
            val prayerDateTime = LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"))

            if (currentTime.isAfter(prayerDateTime)) {
                layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyColor))
            } else {
                layout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.brown))
            }
        }

        layout.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, cornerRadius)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }


    @SuppressLint("SetTextI18n")
    private fun formatRemainingTime(remainingMinutes: Long): String {
        if (remainingMinutes <= 0) {
            return ""
        }

        val hours = (remainingMinutes + 80) / 60
        val minutes = remainingMinutes % 60

        var format = ""
        if (hours > 0)
            format = "$hours hours and"
        format = "$format$minutes minutes"


        return "( $format )"
//        return "(${"%02d:%02d".format(hours, minutes)})"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun attachListener() {
        mViewBinding.cityName.setOnClickListener {
            val bottomSheetFragment = CityBottomSheet()
            bottomSheetFragment.setCitySelectedListener(this)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        mViewBinding.date.setOnClickListener {

            showDatePickerDialog()

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(requireContext(), dateSetListener, year, month, day)
        datePickerDialog?.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)

    private val dateSetListener = OnDateSetListener { _, year, month, dayOfMonth: Int ->
        val selectedYear = year
        val selectedMonth = month + 1
        val selectedDayOfMonth = dayOfMonth

        callPrayerTimeData(selectedYear, selectedMonth, selectedDayOfMonth)

        datePickerDialog?.dismiss()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun callPrayerTimeData(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {

        showProgressDialog()

        lifecycleScope.launch {
            delay(1000)

            mViewModel.prayerResponse.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showProgressDialog()
                    }

                    is Resource.Success -> {
                        hideProgressDialog()
                        val response = resource.value

                        val timeList = response.data
                        // Add null check here
                        if (!timeList.isNullOrEmpty()) {
                            val selectedPrayerTimeData = timeList.find { prayerData ->
                                prayerData.date?.gregorian?.day?.toIntOrNull() == selectedDayOfMonth
                            }
                            selectedPrayerTimeData?.let { populateTodayTimes(it) }
                        }
                    }

                    is Resource.Failure -> {
                        hideProgressDialog()
                        Toast.makeText(mainActivity, resource.errorString, Toast.LENGTH_LONG)
                            .show()
                    }

                    else -> {

                    }
                }
            }
        }
    }


    override fun observeViewModel() {
    }

    override fun onCitySelected(city: CityModel) {
        mViewBinding.cityName.text = city.cityName
        callCityPrayerTime(city)

    }

    private fun callCityPrayerTime(city: CityModel) {
        val apiUrl =
            "https://api.aladhan.com/v1/calendar/$year/$month?latitude=${city.latitude}&longitude=${city.longitude}"
        mViewModel.callGetPrayerData(apiUrl)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        unregisterNetworkCallback()
    }


}
