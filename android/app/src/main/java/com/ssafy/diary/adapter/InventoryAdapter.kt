package com.ssafy.diary.adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ssafy.diary.R
import com.ssafy.diary.dto.InventoryItem
import com.ssafy.diary.util.SharedPreferencesUtil

class InventoryAdapter(val context: Context, val list: List<InventoryItem>, val itemList: List<Int>): RecyclerView.Adapter<InventoryAdapter.InventoryHolder>() {
    inner class InventoryHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage = itemView.findViewById<ImageView>(R.id.img_item_type)
        val itemBlocked = itemView.findViewById<TextView>(R.id.text_item_blocked)
        val itemLockImg = itemView.findViewById<ImageView>(R.id.img_item_locked)
        val itemCheckbox = itemView.findViewById<TextView>(R.id.checkbox_used_item)
        val setting = SharedPreferencesUtil(context).getSetting()

        fun bind(){
            Log.d("해위", setting.background.toString())
            itemImage.setImageResource(itemList[layoutPosition])
            list.forEach {
                if(layoutPosition == it.itemId){
                    itemBlocked.visibility = View.GONE
                    itemLockImg.visibility = View.GONE
                }
            }
            if(setting.character == itemList[layoutPosition] || setting.background == itemList[layoutPosition]){
                itemCheckbox.setBackgroundResource(R.drawable.check_box_style2)
                // 주석추가
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_item_type, parent, false)
        return InventoryHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: InventoryHolder, position: Int) {
        holder.bind()
    }
}