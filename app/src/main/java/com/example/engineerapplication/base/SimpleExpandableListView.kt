package com.example.engineerapplication.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter

abstract class SimpleExpandableListAdapter<MAIN, DETAIL> : BaseExpandableListAdapter() {

    private var list = mutableListOf<MAIN>()

    override fun getGroupCount(): Int {
        return list.size
    }

    override fun getChildrenCount(i: Int): Int {
        return getPropertyDetailList(list[i]).size
    }

    override fun getGroup(i: Int): MAIN {
        return list[i]
    }

    override fun getChild(i: Int, i1: Int): DETAIL {
        return getPropertyDetailList(list[i])[i1]
    }

    abstract fun getPropertyDetailList(item: MAIN): List<DETAIL>

    override fun getGroupId(i: Int): Long {
        return 0
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(i: Int, b: Boolean, view: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup?.context)
            .inflate(onCreateViewHolderMain(), viewGroup, false)
        view.onBindViewHolderMain(getGroup(i))
        return view
    }

    abstract fun onCreateViewHolderMain(): Int

    abstract fun View.onBindViewHolderMain(item: MAIN)

    override fun getChildView(
        i: Int,
        i1: Int,
        b: Boolean,
        view: View?,
        viewGroup: ViewGroup?
    ): View {
        val view = LayoutInflater.from(viewGroup?.context)
            .inflate(onCreateViewHolderDetail(), viewGroup, false)
        view.onBindViewHolderDetail(getChild(i, i1))
        return view
    }

    abstract fun onCreateViewHolderDetail(): Int

    abstract fun View.onBindViewHolderDetail(item: DETAIL)

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return false
    }

    fun setList(list: List<MAIN>) {
        this.list = list as MutableList<MAIN>
        notifyDataSetChanged()
    }

}
