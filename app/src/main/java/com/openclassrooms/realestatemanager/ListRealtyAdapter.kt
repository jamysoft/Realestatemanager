package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.models.Realty
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import java.util.zip.Inflater


const val KEY_ID_REALTY = "key_id_realty"

class ListRealtyAdapter(val menuInflater: MenuInflater, val myViewModel: RealtyViewModel): ListAdapter<Realty, ListRealtyAdapter.MyViewHolder>(REALTY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup,ViewType: Int):MyViewHolder {
        return MyViewHolder.create(parent,myViewModel)
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

        holder.itemView.setOnLongClickListener {
            Toast.makeText(holder.itemView.context,"long click ${current.type}", Toast.LENGTH_LONG).show()
            /*************/
            val callback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: androidx.appcompat.view.ActionMode?, menu: Menu?): Boolean {
                    menuInflater.inflate(R.menu.menucontextuel, menu)
                    return true
                }
                @SuppressLint("ResourceType")
                override fun onPrepareActionMode(mode: androidx.appcompat.view.ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: androidx.appcompat.view.ActionMode?, item: MenuItem?): Boolean {
                    Intent(holder.itemView.context,AddActivity::class.java)
                    return when (item?.itemId) {
                        R.id.delete -> {
                            myViewModel.delete(current)
                            Toast.makeText( holder.itemView.context, "delete ${current.toString()}", Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.makeItSold -> {
                            Toast.makeText(holder.itemView.context, "Sold click detected", Toast.LENGTH_SHORT).show()
                                myViewModel.updateStatusRealty()
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {

                }
            }

            /*************/
            val actionMode =  ( it.context as AppCompatActivity).startSupportActionMode(callback)
          //  actionMode?.title =  "${current.idRealty} is selected"

            true
        }
        holder.bind(current)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

      private val realtyShot: ImageView = itemView.findViewById(R.id.realtyShot)
        private val realtyTown: TextView = itemView.findViewById(R.id.realtyTown)
        private val realtyAvailable: TextView = itemView.findViewById(R.id.realtyAvailable)
        private val realtyPrice: TextView = itemView.findViewById(R.id.realtyPrice)
        private val realtyType: TextView = itemView.findViewById(R.id.realtyType)
        private val updateButton: Button = itemView.findViewById(R.id.updateButton)

        fun bind(currentRealty: Realty) {
            /*****  CONVERT Drawable to  bitmap ****/

            var bitmap = BitmapFactory.decodeResource(itemView.context.getResources(),
            R.drawable.belleappart);
            realtyShot.setImageBitmap(bitmap)
            realtyTown.setText(currentRealty.town)
            realtyPrice.setText("$ ${currentRealty.price}")
            realtyType.setText(currentRealty.type)
            if(currentRealty.isAvailable){
                realtyAvailable.setText("AVAILABLE")
            }
            else{
                realtyAvailable.setText("VENDU")
            }

            updateButton.setOnClickListener {
                println("update")
             val intent = Intent(itemView.context,DetailActivity::class.java)
                intent.putExtra(KEY_ID_REALTY,5 )
                // start detail activity
                startActivity(itemView.context,intent,null)
            }
        }
        companion object {
            fun create(parent: ViewGroup, myViewModel: RealtyViewModel): MyViewHolder {
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

