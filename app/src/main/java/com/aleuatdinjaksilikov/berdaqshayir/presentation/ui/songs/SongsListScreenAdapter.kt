package com.aleuatdinjaksilikov.berdaqshayir.presentation.ui.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aleuatdinjaksilikov.berdaqshayir.R
import com.aleuatdinjaksilikov.berdaqshayir.data.model.SongData
import com.aleuatdinjaksilikov.berdaqshayir.databinding.RvItemSongBinding

class SongsListScreenAdapter: ListAdapter<SongData, SongsListScreenAdapter.SongsListScreenAdapterVH>(
    object : DiffUtil.ItemCallback<SongData>(){
        override fun areItemsTheSame(oldItem: SongData, newItem: SongData) = oldItem == newItem
        override fun areContentsTheSame(oldItem: SongData, newItem: SongData) = oldItem.id == newItem.id
    }
){

    private var onItemClickListener: ((SongData) -> Unit)? = null
    fun setOnItemClickListener(block: ((SongData) -> Unit)) {
        onItemClickListener = block
    }

    inner class SongsListScreenAdapterVH(private val binding:RvItemSongBinding):ViewHolder(binding.root){
        fun bind(position: Int){
            val itemData = getItem(position)

            binding.itemText.text = itemData.name

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(itemData)
            }

            binding.root.startAnimation(AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_in))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  SongsListScreenAdapterVH(
        RvItemSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: SongsListScreenAdapterVH, position: Int) {
        holder.bind(position)
    }


}