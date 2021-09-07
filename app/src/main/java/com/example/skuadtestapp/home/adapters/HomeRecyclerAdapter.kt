package com.example.skuadtestapp.home.adapters

import ResultsModel
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skuadtestapp.home.callbacks.IHomeRecyclerCallBack
import android.view.LayoutInflater
import com.example.skuadtestapp.databinding.RecyclerLayoutHomeBinding


class HomeRecyclerAdapter(val context: Context, val callback: IHomeRecyclerCallBack) : RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder>() {

    private var resultsList = mutableListOf<ResultsModel>()

    init {
        resultsList.clear()
    }

    fun setResultsList(dataList: MutableList<ResultsModel>) {
        this.resultsList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerLayoutHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)     //no need to write layout name here as binding is made on Layout Name
        val view: View = binding.root

        return MyViewHolder(view, context, binding, callback, resultsList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        myViewHolder.setRowData(position)
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    inner class MyViewHolder(val view: View, val context: Context, val binding: RecyclerLayoutHomeBinding, val callback: IHomeRecyclerCallBack, val resultsList: List<ResultsModel>) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
//            view.subInfo.setOnClickListener(this)
        }

        fun setRowData(position: Int) {
        }

        override fun onClick(v: View?) = callback.onSelectedRecyclerClick(adapterPosition)
    }

}