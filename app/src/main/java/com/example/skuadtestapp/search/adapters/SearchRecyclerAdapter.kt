package com.example.skuadtestapp.search.adapters

import SearchResults
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.RecyclerLayoutHomeBinding
import com.example.skuadtestapp.databinding.RecyclerLayoutSearchBinding
import com.example.skuadtestapp.search.callbacks.ISearchRecyclerCallBack
import com.google.android.material.shape.CornerFamily


class SearchRecyclerAdapter(val context: Context, val callback: ISearchRecyclerCallBack) : RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder>() {

    private var searchResultsList = mutableListOf<SearchResults>()

    init {
        searchResultsList.clear()
    }

    fun setResultsList(dataList: MutableList<SearchResults>) {
        this.searchResultsList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerLayoutSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)     //no need to write layout name here as binding is made on Layout Name
        val view: View = binding.root

        return MyViewHolder(view, context, binding, callback, searchResultsList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        myViewHolder.setRowData(position)
    }

    override fun getItemCount(): Int {
        return searchResultsList.size
    }

    inner class MyViewHolder(val view: View, val context: Context, val binding: RecyclerLayoutSearchBinding, val callback: ISearchRecyclerCallBack, val resultsList: List<SearchResults>) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            makeImageViewRounded()
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) = callback.onSelectedRecyclerClick(adapterPosition)

        private fun makeImageViewRounded() {
            binding.ivRestaurantPic.shapeAppearanceModel = binding.ivRestaurantPic.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 21.1f)
                .setTopRightCorner(CornerFamily.ROUNDED, 21.1f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 21.1f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 21.1f)
                .build()
        }

        fun setRowData(position: Int) {
            resultsList[position].name?.let {
                binding.tvHeading.text = it
            }
            resultsList[position].vicinity?.let {
                binding.tvSubHeading.text = it
            }
            resultsList[position].rating?.let {
                binding.tvRating.text = it.toString()
            }
            resultsList[position].opening_hours?.open_now?.let {
                binding.tvOpen.visibility = View.VISIBLE
                binding.tvOpen.text = if (it) context.getString(R.string.nearby_recycler_open)
                else context.getString(R.string.nearby_recycler_closed)
            }

            loadPics(position)
        }

        private fun loadPics(position: Int) {
            //concatenate url
            var restaurantImageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth="
            resultsList[position].photos?.let {
                it[0].width?.let { width ->
                    restaurantImageUrl += width
                    it[0].photo_reference?.let { photoRef ->
                        restaurantImageUrl = "$restaurantImageUrl&photoreference=$photoRef&key=AIzaSyDxVclNSQGB5WHAYQiHK-VxYKJelZ_9mjk"

                        Glide.with(context).load(restaurantImageUrl).into(binding.ivRestaurantPic)
                    }
                }
            }
        }
    }

}