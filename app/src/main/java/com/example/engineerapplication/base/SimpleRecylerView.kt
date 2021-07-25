package com.example.engineerapplication.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

typealias EventClick<T> = (T) -> Unit

abstract class SimpleRecyclerView<T : Any> :
    RecyclerView.Adapter<SimpleRecyclerView<T>.SimpleViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return getAreItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    })

    private val list: MutableList<T>
        get() = asyncListDiffer.currentList

    private var onClick: (EventClick<T>)? = null
    private var onLongClick: (EventClick<T>)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayout(), parent, false)
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        if (position > 0) {
            holder.itemView.onBindViewHolder(list[position], list[position - 1])
        } else {
            holder.itemView.onBindViewHolder(list[position], null)
        }
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun View.onBindViewHolder(currentData: T, beforeData: T?)

    open fun getAreItemsTheSame(oldItem: T, newItem: T): Boolean = false

    fun submitList(list: List<T>) = asyncListDiffer.submitList(list)

    fun setOnClickListener(onClick: EventClick<T>) {
        this.onClick = onClick
    }

    fun setOnLongClickListener(onLongClick: EventClick<T>) {
        this.onLongClick = onLongClick
    }

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
//            itemView.setOnClickListener {
//                val entity = list[absoluteAdapterPosition]
//                onClick?.invoke(entity)
//            }
//
//            itemView.setOnLongClickListener {
//                val entity = list[absoluteAdapterPosition]
//                onLongClick?.invoke(entity)
//                true
//            }
        }

    }

}

abstract class SingleRecyclerView<T : Any> :
    RecyclerView.Adapter<SingleRecyclerView<T>.ItemViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    })

    private val list: MutableList<T?>
        get() = asyncListDiffer.currentList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleRecyclerView<T>.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayout(), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.onBindViewHolder(list[position])
    }

    override fun getItemCount(): Int = list.size

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun View.onBindViewHolder(data: T?)

    fun submitData(data: T) = asyncListDiffer.submitList(listOf(data))

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

fun <T : Any> RecyclerView.submitList(
    adt: SimpleRecyclerView<T>,
    data: List<T>,
    lm: RecyclerView.LayoutManager = LinearLayoutManager(this.context),
) {
    this.apply {
        layoutManager = lm
        adapter = adt
    }
    adt.submitList(data)
}