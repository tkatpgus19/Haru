package com.ssafy.diary.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.diary.MainActivity.Companion.bCheckbox
import com.ssafy.diary.MainActivity.Companion.backgroundImg
import com.ssafy.diary.MainActivity.Companion.cCheckbox
import com.ssafy.diary.MainActivity.Companion.characterImg
import com.ssafy.diary.R
import com.ssafy.diary.dto.InventoryItem
import com.ssafy.diary.dto.Item
import com.ssafy.diary.util.SharedPreferencesUtil

class ItemAdapter(val context: Context, val list: List<Int>, val itemList: List<Item>, val type: String): RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage = itemView.findViewById<ImageView>(R.id.img_store_item_type)
        val itemBlocked = itemView.findViewById<TextView>(R.id.text_store_item_blocked)
        val itemLockImg = itemView.findViewById<ImageView>(R.id.img_store_item_locked)
        val itemTv = itemView.findViewById<TextView>(R.id.text_item_heart_count)
        val setting = SharedPreferencesUtil(context).getSetting()

        fun bind(){
            var hasItem = false
            itemImage.setImageResource(list[layoutPosition])
            itemTv.setText("10")

            list.forEach {
                if(layoutPosition == it){
                    itemBlocked.visibility = View.GONE
                    itemLockImg.visibility = View.GONE
                    hasItem = true
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store_item_type, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }
}