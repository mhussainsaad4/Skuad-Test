package com.example.skuadtestapp.search.adapters

import SearchResults
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.skuadtestapp.databinding.RecyclerLayoutSearchBinding
import com.example.skuadtestapp.search.callbacks.ISearchRecyclerCallBack


class SearchRecyclerAdapter(val context: Context, val callback: ISearchRecyclerCallBack) : RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder>() {

    private var seacrhResultsList = mutableListOf<SearchResults>()

    init {
        seacrhResultsList.clear()
    }

    fun setResultsList(dataList: MutableList<SearchResults>) {
        this.seacrhResultsList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerLayoutSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)     //no need to write layout name here as binding is made on Layout Name
        val view: View = binding.root

        return MyViewHolder(view, context, binding, callback, seacrhResultsList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        myViewHolder.setRowData(position)
    }

    override fun getItemCount(): Int {
        return seacrhResultsList.size
    }

    inner class MyViewHolder(val view: View, val context: Context, val binding: RecyclerLayoutSearchBinding, val callback: ISearchRecyclerCallBack, val resultsList: List<SearchResults>) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
//            view.subInfo.setOnClickListener(this)
        }

        fun setRowData(position: Int) {
        }

        override fun onClick(v: View?) = callback.onSelectedRecyclerClick(adapterPosition)
    }

}