package com.android_test_assessment.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_test_assessment.data.model.ApiDateResponseItem
import com.android_test_assessment.databinding.RawItemLayoutBinding
import com.android_test_assessment.utils.base.BaseRecyclerViewAdapter

class ItemListAdapter(
    val context: Context,
    val onItemClick: (data: ApiDateResponseItem, position: Int) -> Unit?
) :
    BaseRecyclerViewAdapter<ApiDateResponseItem, ItemListAdapter.RawViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup): RawViewHolder {
        val itemBinding =
            RawItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RawViewHolder(itemBinding)
    }

    override fun bindItemViewHolder(holder: RawViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    inner class RawViewHolder(private val itemBinding: RawItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: ApiDateResponseItem, position: Int) {
            itemBinding.tvId.text = data.id.toString()
            itemBinding.tvTitle.text = data.title
            itemBinding.root.setOnClickListener {
                onItemClick.invoke(data, position)
            }
        }
    }
}