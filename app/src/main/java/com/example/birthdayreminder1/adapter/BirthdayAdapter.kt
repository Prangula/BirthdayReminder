package com.example.birthdayreminder1.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.birthdayreminder1.R
import com.example.birthdayreminder1.db.BirthdayItem
import com.example.birthdayreminder1.ui.BirthdayViewModel
import kotlinx.android.synthetic.main.birthday_main_item.view.*

class BirthdayAdapter(var items:List<BirthdayItem>
,private val viewModel:BirthdayViewModel)
    :RecyclerView.Adapter<BirthdayAdapter.ViewHolder>(){

        inner class ViewHolder(itemView:View)
            :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.birthday_main_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {

            main_item_title.text = item.title

            main_item_to.text = item.to

            Glide.with(holder.itemView)
                .load(item.image)
                .into(main_item_image)

            main_item_time.text = item.time

           main_item_card_main.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,item.color))
        }

        holder.itemView.main_item_delete.setOnClickListener {

            val alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle("Do you want to delete?")
            alertDialog.setMessage("Are you sure?")
            alertDialog.setCancelable(false)

            alertDialog.setPositiveButton("Yes"){dialog,_->

                viewModel.delete(item)
                dialog.dismiss()

            }

            alertDialog.setNegativeButton("No"){dialog,_->

                dialog.dismiss()

            }

            val dialog = alertDialog.create()
            dialog.show()


        }


    }

}