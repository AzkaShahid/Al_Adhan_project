package com.app.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.databinding.AdapterTimeBinding
import com.app.models.prayer.Data

class PrayerAdapter(
    private val context: Context,
    private var arrayList: List<Data>,
) :
    RecyclerView.Adapter<PrayerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView =
            AdapterTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val prayerTime = arrayList[position]
        val binding = holder.getBinding()
        holder.bind(context, prayerTime, position)


    }

    fun updateAdapter(arrayList: ArrayList<Data>) {
        this.arrayList =arrayList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(private val binding: AdapterTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, model : Data, position: Int) {
            binding.Fajrtiming.text = model.timings?.fajr
            binding.sunrisetiming.text = model.timings?.sunrise
            binding.duhrtiming.text = model.timings?.dhuhr
            binding.asrtiming.text = model.timings?.asr
            binding.maghribtiming.text = model.timings?.maghrib
            binding.ishatiming.text = model.timings?.isha



        }

        fun getBinding(): AdapterTimeBinding {
            return binding
        }
    }

}
