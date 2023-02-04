package com.example.aston_intensive_course_work.presentation.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.aston_intensive_course_work.presentation.interfaces.Item
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ItemsPagingAdapter<I : Item, VH : ItemsViewHolder<I>, VB : ViewBinding>(
    private val onClick: (I) -> Unit,
    diffCallback: ItemsDiffUtils<I>,
    private val viewHolderClass: KClass<out VH>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB
) :
    PagingDataAdapter<I, VH>(diffCallback) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position = position)!!
        holder.bind(item = item)
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binder = bindingInflater.invoke(inflater, parent, false)
        val primaryConstructor = viewHolderClass.primaryConstructor
        return primaryConstructor!!.call(binder)
    }

    abstract class ItemsDiffUtils<I : Item> : DiffUtil.ItemCallback<I>() {
        override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
            return oldItem == newItem
        }
    }
}