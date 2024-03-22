package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.data.model.SongData

class ChangeLanguageAdapter(private var list:ArrayList<String>):RecyclerView.Adapter<ChangeLanguageAdapter.ViewHolder>() {

    private val selectedPosition = -1
    private val lastSelectedPosition = -1

    private var onItemClickLanguage: ((Int) -> Unit)? = null
    fun setOnItemClickLanguage(block: ((Int) -> Unit)) {
        onItemClickLanguage = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_language,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.item.text = item
        holder.itemView.setOnClickListener {
            onItemClickLanguage?.invoke(position)
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val item: TextView =  itemView.findViewById(R.id.tv_language)
    }

}