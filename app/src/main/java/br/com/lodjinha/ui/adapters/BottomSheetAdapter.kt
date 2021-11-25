package br.com.lodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lodjinha.R

class BottomSheetAdapter: RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private var bottomSheetItens = arrayOf("A-Z", "Z-A", "Original")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_filter_row, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listItem.text = bottomSheetItens[position]
    }

    override fun getItemCount(): Int {
        return bottomSheetItens.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var listItem: TextView

        init {
            listItem = itemView.findViewById(R.id.bsItem)
        }
    }
}