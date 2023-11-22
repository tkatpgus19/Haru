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
import com.ssafy.diary.util.SharedPreferencesUtil

class InventoryAdapter(val context: Context, val list: List<Int>, val itemList: List<Int>, val type: String): RecyclerView.Adapter<InventoryAdapter.InventoryHolder>() {
    inner class InventoryHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage = itemView.findViewById<ImageView>(R.id.img_item_type)
        val itemBlocked = itemView.findViewById<TextView>(R.id.text_item_blocked)
        val itemLockImg = itemView.findViewById<ImageView>(R.id.img_item_locked)
        val itemCheckbox = itemView.findViewById<TextView>(R.id.checkbox_used_item)
        val setting = SharedPreferencesUtil(context).getSetting()

        fun bind(){
            var hasItem = false
            itemImage.setImageResource(itemList[layoutPosition])
            list.forEach {
                if(layoutPosition == it){
                    itemBlocked.visibility = View.GONE
                    itemLockImg.visibility = View.GONE
                    hasItem = true
                }
            }
            if(setting.character == itemList[layoutPosition] || setting.background == itemList[layoutPosition]){
                itemCheckbox.setBackgroundResource(R.drawable.check_box_style2)
                if(type == "B")
                    bCheckbox = itemCheckbox
                else{
                    cCheckbox = itemCheckbox
                }

            }
            itemImage.setOnClickListener {
                if(bCheckbox != itemCheckbox && cCheckbox != itemCheckbox) {
                    if (hasItem) {
                        itemCheckbox.setBackgroundResource(R.drawable.check_box_style2)
                        if (type == "B") {
                            backgroundImg = itemList[layoutPosition]
                            bCheckbox.setBackgroundResource(R.drawable.check_box_style)
                            bCheckbox = itemCheckbox
//                            SharedPreferencesUtil(context).saveSetting(itemList[layoutPosition], -1)
                        } else {
                            characterImg = itemList[layoutPosition]
                            cCheckbox.setBackgroundResource(R.drawable.check_box_style)
                            cCheckbox = itemCheckbox
//                            SharedPreferencesUtil(context).saveSetting(-1, itemList[layoutPosition])
                        }
                    } else {
                        Toast.makeText(context, "구입하지 않은 아이템입니다...", Toast.LENGTH_SHORT).show()
                    }
                }
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