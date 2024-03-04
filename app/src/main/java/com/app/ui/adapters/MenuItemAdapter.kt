package com.app.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.listeners.MenuListener
import com.app.models.MenuItemModel
import com.app.databinding.AdapterMenuBinding
import com.app.constants.AppConstants

class MenuItemAdapter(
    private val context: Context,
    private var listener: MenuListener
) :
    RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val itemView =
            AdapterMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model = listener.getMenuItems()[position]
        holder.bind(context, model)
        holder.itemView.setOnClickListener {
            AppConstants.SELECTED_MENU_ID = model.menuID
            listener.onMenuItemClicked(model)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listener.getMenuItems().size
    }

    class ViewHolder(private val binding: AdapterMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, model: MenuItemModel) {
            binding.ivMenu.setImageResource(model.iconID)
            binding.tvMenu.text = model.menuText
            val colorID =
                if (model.menuID == AppConstants.SELECTED_MENU_ID) {
                    R.color.colorPrimaryAlpha
                } else {
                    R.color.white
                }
            binding.llRow.setBackgroundColor(ContextCompat.getColor(context, colorID))

        }
    }

}