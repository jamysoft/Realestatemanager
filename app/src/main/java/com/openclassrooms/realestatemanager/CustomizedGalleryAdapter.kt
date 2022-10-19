package com.openclassrooms.realestatemanager
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


 class CustomizedGalleryAdapter(private var context: Context, private var images: MutableList<Bitmap>) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    override fun getCount(): Int {
       return images.size
    }

    override fun getItem(position: Int): Any {
       return images[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
           var  view= layoutInflater!!.inflate(R.layout.itempic,viewGroup,false)
            // convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        var imageView=view.findViewById<ImageView>(R.id.img)
        imageView.setImageBitmap(images.get(position))
        return view
    }
}