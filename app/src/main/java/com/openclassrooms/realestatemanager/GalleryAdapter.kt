package com.openclassrooms.realestatemanager
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class GalleryAdapter : ListAdapter<Bitmap,GalleryAdapter.MyViewHolder>(GALLERY_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
                showDialog(it.context,current)
         println("clique photo$position")
        }
        holder?.bind(current)

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val realtyShot: ImageView = itemView.findViewById(R.id.img)
        fun bind(current: Bitmap) {
            realtyShot.setImageBitmap(current)
        }
        companion object {
            fun create(parent: ViewGroup ): MyViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.itemshot, parent, false)
                return MyViewHolder(view)
            }
        }
    }
    companion object {
        private val GALLERY_COMPARATOR = object : DiffUtil.ItemCallback<Bitmap>() {
            override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
                return oldItem.sameAs(newItem)
            }
        }
    }
    private fun showDialog(context: Context, current: Bitmap) {
        // custom dialog
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialo)

        //set the custom dialog components - text, image and button
        val close = dialog.findViewById(R.id.btnClose) as ImageButton
        val imageView=dialog.findViewById(R.id.imageview) as ImageView
        imageView.setImageBitmap(current)
        // Close Button
          close.setOnClickListener {
          dialog.dismiss()
        //TODO Close button action
    }

    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}

}

