package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.models.RealtyItem
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel


const val KEY_ID_REALTY = "key_id_realty"

class ListRealtyAdapter(
    val menuInflater: MenuInflater,
    val myViewModel: RealtyViewModel,
    val mainFragment: MainFragment
) : ListAdapter<RealtyItem, ListRealtyAdapter.MyViewHolder>(REALTY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): MyViewHolder {
        return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
          //  val bundle = bundleOf(KEY_ID_REALTY to current.idRealty)
          //  NavHostFragment.findNavController(mainFragment)
           //     .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            intent.putExtra(KEY_ID_REALTY,current.idRealty )
            //start detail activity
            startActivity(holder.itemView.context,intent,null)

        }

        holder.itemView.setOnLongClickListener {
            // Toast.makeText(holder.itemView.context,"long click ${current.type}", Toast.LENGTH_LONG).show()
            val callback = showTextualActionBar(holder.itemView.context, current)
            val actionMode = (it.context as AppCompatActivity).startSupportActionMode(callback)
            actionMode?.title = "${current.type}-${current.town} "
            true
        }
        holder.bind(current)
    }


    private fun showTextualActionBar(context: Context, current: RealtyItem): ActionMode.Callback {

        val callback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.menucontextuel, menu)
                return true
            }

            @SuppressLint("ResourceType")
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

                return when (item?.itemId) {
                    R.id.delete -> {
                        myViewModel.deleteByIdRealty(current.idRealty)
                        Toast.makeText(context, "delete $current", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.makeItSold -> {
                        Toast.makeText(context, "Sold click detected", Toast.LENGTH_SHORT).show()
                        myViewModel.updateStatusRealty(current.idRealty)
                        true
                    }
                    R.id.upDate -> {
                        val intent = Intent(context, EditActivity::class.java)
                        Toast.makeText(context, "update click detected", Toast.LENGTH_SHORT).show()
                        intent.putExtra(KEY_EDIT_REALTY, current.idRealty)
                        // start detail activity
                        startActivity(context, intent, null)
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }
        }
        return callback
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val realtyShot: ImageView = itemView.findViewById(R.id.realtyShot)
        private val realtyTown: TextView = itemView.findViewById(R.id.realtyTown)
        private val realtyAvailable: TextView = itemView.findViewById(R.id.realtyAvailable)
        private val realtyPrice: TextView = itemView.findViewById(R.id.realtyPrice)
        private val realtyType: TextView = itemView.findViewById(R.id.realtyType)
        private val realtyDateEntry: TextView = itemView.findViewById(R.id.realtyDateEntry)
        fun bind(currentRealty: RealtyItem) {
            realtyShot.setImageBitmap(currentRealty.shot)
            realtyTown.text =itemView.context.getString(R.string.cama)+ currentRealty.town
            realtyPrice.text = currentRealty.price.toString()+itemView.context.getString(R.string.money)
            realtyType.text = currentRealty.type
            realtyDateEntry.text=itemView.context.getString(R.string.dateOnLine) + currentRealty.entryDate
            if (currentRealty.isAvailable) {
                realtyAvailable.text = itemView.context.getString(R.string.availableText)
                realtyAvailable.setTextColor(Color.parseColor("#38A563"))
                realtyAvailable.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_baseline_info_24), null, null, null
                )
            } else {
                realtyAvailable.text = itemView.context.getString(R.string.SoldText)
                realtyAvailable.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_baseline_info_red_24
                    ), null, null, null
                )
            }

        }

        companion object {
            fun create(parent: ViewGroup): MyViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemlistrealty, parent, false)
                return MyViewHolder(view)
            }
        }

    }

    companion object {
        private val REALTY_COMPARATOR = object : DiffUtil.ItemCallback<RealtyItem>() {
            override fun areItemsTheSame(oldItem: RealtyItem, newItem: RealtyItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: RealtyItem, newItem: RealtyItem): Boolean {
                return oldItem.address == newItem.address
            }
        }
    }


}

