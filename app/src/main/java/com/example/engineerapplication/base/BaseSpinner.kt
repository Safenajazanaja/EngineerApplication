package com.example.easyfix.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.LayoutRes

abstract class BaseSpinner<T>(
    context: Context,
    list: MutableList<T>
) : ArrayAdapter<T>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract infix fun View.onBindViewHolder(data: T)

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(getLayout(), parent, false)
        getItem(position)?.let { view onBindViewHolder it }
        return view
    }

}

@Suppress("UNCHECKED_CAST")
fun <T> Spinner.onItemSelected(item: (T) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View?,
            position: Int,
            id: Long
        ) {
            val model = parent.getItemAtPosition(position) as T
            item.invoke(model)
        }
    }
}
