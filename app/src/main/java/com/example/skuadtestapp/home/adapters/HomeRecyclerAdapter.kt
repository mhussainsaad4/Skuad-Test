package com.example.skuadtestapp.home.adapters

import ResultsModel
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skuadtestapp.home.callbacks.IHomeRecyclerCallBack
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.RecyclerLayoutHomeBinding
import com.google.android.material.shape.CornerFamily


class HomeRecyclerAdapter(private val context: Context, private val callback: IHomeRecyclerCallBack) : RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder>() {

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

    inner class MyViewHolder(private val view: View, private val context: Context, private val binding: RecyclerLayoutHomeBinding, private val callback: IHomeRecyclerCallBack, private val resultsList: List<ResultsModel>) : RecyclerView.ViewHolder(view), View.OnClickListener {

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
            resultsList[position].price_level?.let {
                binding.tvPrice.visibility = View.VISIBLE
                binding.tvPrice.text = String.format(context.getString(R.string.nearby_recycler_price), it)
            }
            resultsList[position].opening_hours?.open_now?.let {
                binding.tvOpen.visibility = View.VISIBLE
                binding.tvOpen.text = if (it) context.getString(R.string.nearby_recycler_open)
                else context.getString(R.string.nearby_recycler_closed)
            }
            resultsList[position].types?.let {
                var typesString = it[0] + " * "
                for (type in 2 until it.size - 1) typesString += (it[type] + " * ")
                typesString += it[it.size - 1]
                binding.tvFacilities.text = typesString
            }
            loadPics(position)
        }

        /*
        For Image concatenate url
        https://maps.googleapis.com/maps/api/place/photo?

        maxwidth=[width value in your json (photos[0] -> width)]
        &
        photoreference=[photos[0] -> photo_reference]
        &
        key=[your api key]
        So your final URL will be

        https://maps.googleapis.com/maps/api/place/photo?maxwidth=5184&photoreference=CmRaAAAADmroqcfJMAxlbbpIC4r8bk5jU-8fPx5udw2ng-XPvO9a-E46QlvggSxlgkxDsnjJyKacRSqvPkm69dbEvPlX6fgaGtEaApGROn-U6wDjpXlr5PTcjEDN6UACil904bHMEhAtOschikf4lATKyBULOsnLGhTQTXKMKJnV_Z_0kdqUIyh6ZIDZ0A&key=xyzyourapikey

         */

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