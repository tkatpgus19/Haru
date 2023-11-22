package com.ssafy.diary.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.diary.LoginActivity
import com.ssafy.diary.MainActivity.Companion.bCheckbox
import com.ssafy.diary.MainActivity.Companion.backgroundImg
import com.ssafy.diary.MainActivity.Companion.cCheckbox
import com.ssafy.diary.MainActivity.Companion.characterImg
import com.ssafy.diary.R
import com.ssafy.diary.dto.InventoryItem
import com.ssafy.diary.dto.Item
import com.ssafy.diary.util.RetrofitUtil
import com.ssafy.diary.util.SharedPreferencesUtil
import kotlinx.coroutines.launch

class StoreAdapter(val context: Context, val inventoryList: ArrayList<InventoryItem>, val list: ArrayList<Item>, val itemList: List<Int>, val type: String): RecyclerView.Adapter<StoreAdapter.StoreHolder>() {
    inner class StoreHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage = itemView.findViewById<ImageView>(R.id.img_store_item_type)
        val itemBlocked = itemView.findViewById<TextView>(R.id.text_store_item_blocked)
        val itemLockImg = itemView.findViewById<ImageView>(R.id.img_store_item_locked)
        val itemPrice = itemView.findViewById<TextView>(R.id.text_item_heart_count)
        val itemHeart = itemView.findViewById<ImageView>(R.id.img_heart)

        fun bind(){
            var hasItem = false
            itemImage.setImageResource(itemList[layoutPosition])
            itemPrice.text = list[layoutPosition].itemPrice.toString()

            inventoryList.forEach {
                if(layoutPosition == it.itemId){
                    itemBlocked.visibility = View.GONE
                    itemLockImg.visibility = View.GONE
                    itemPrice.visibility = View.GONE
                    itemHeart.visibility = View.GONE
                    hasItem = true
                }
            }

            itemImage.setOnClickListener {
                if(hasItem){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)


                    val inflater = LayoutInflater.from(context).inflate(R.layout.dialog_match_password, null)
                    builder.apply {
                        setView(inflater)
                        setPositiveButton("확인"){ dialog, _ ->
                            dialog.cancel()
                        }
                        setNegativeButton("취소"){dialog, _ ->
                            dialog.cancel()
                        }
                    }
                    builder.create().show()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store_item_type, parent, false)
        return StoreHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        holder.bind()
    }
}