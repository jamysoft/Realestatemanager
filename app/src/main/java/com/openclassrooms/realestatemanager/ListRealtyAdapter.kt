package com.openclassrooms.realestatemanager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.models.Realty

const val KEY_ID_REALTY = "key_id_realty"

class ListRealtyAdapter: ListAdapter<Realty, ListRealtyAdapter.MyViewHolder>(REALTY_COMPARATOR) {



    override fun onCreateViewHolder(parent: ViewGroup,ViewType: Int):MyViewHolder {
        return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            Toast.makeText(holder.itemView.context,"item", Toast.LENGTH_LONG).show()
            intent.putExtra(KEY_ID_REALTY,current.idRealty )
            // start detail activity
          startActivity(holder.itemView.context,intent,null)
        }
        holder.bind(current)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

      private val realtyShot: ImageView = itemView.findViewById(R.id.realtyShot)
        private val realtyTown: TextView = itemView.findViewById(R.id.realtyTown)
        private val realtyPrice: TextView = itemView.findViewById(R.id.realtyPrice)
        private val realtyType: TextView = itemView.findViewById(R.id.realtyType)

        fun bind(currentRealty: Realty) {
            /*****  CONVERT Drawable to  bitmap ****/
            var bitmap = BitmapFactory.decodeResource(itemView.context.getResources(),
            R.drawable.belleappart);
            realtyShot.setImageBitmap(bitmap)
            realtyTown.setText(currentRealty.town)
            realtyPrice.setText("${currentRealty.price}")
            realtyType.setText(currentRealty.type)
        }
        companion object {
            fun create(parent: ViewGroup): MyViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.itemlistrealty, parent, false)
                return MyViewHolder(view)
            }
        }

    }
    companion object {
        private val REALTY_COMPARATOR = object : DiffUtil.ItemCallback<Realty>() {
            override fun areItemsTheSame(oldItem: Realty, newItem: Realty): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Realty, newItem: Realty): Boolean {
                return oldItem.address == newItem.address
            }
        }
    }
}

