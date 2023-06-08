package com.example.recyclerviewapplication1

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapplication1.databinding.ItemViewBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class HomeRecyclerViewAdapter(val dataList : MutableList<Email>) : RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewViewHolder>() {

    inner class HomeRecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemViewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return HomeRecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewViewHolder, position: Int) {
        val transformation: Transformation = object : Transformation {
            override fun transform(source: Bitmap): Bitmap {
                val desiredWidth = 100
                val desiredHeight = 100

                val scaledBitmap = Bitmap.createScaledBitmap(source, desiredWidth, desiredHeight, true)
                if (scaledBitmap != source) {
                    source.recycle()
                }

                return scaledBitmap
            }

            override fun key(): String {
                return "resizeTransformation"
            }
        }

        val item = dataList[position]
        holder.binding.apply {
            emailAuthorTV.text = item.author
            emailSubjectTV.text = item.subject
            emailContentTV.text = item.content
            Picasso.get().load(item.image).transform(transformation).into(imageView)
        }
    }

    override fun getItemCount() = dataList.size
}