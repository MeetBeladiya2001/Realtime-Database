package com.example.realtimedatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.realtimedatabase.databinding.AdapterViewBinding

class AdapterView : RecyclerView.Adapter<AdapterView.MyViewHolder>() {

    class MyViewHolder(val binding: AdapterViewBinding) : ViewHolder(binding.root)

    var dataList = emptyList<UserData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userData = dataList[position]

        holder.binding.usernameTXT.text = userData.username
        holder.binding.fullnameTXT.text = userData.fullname
        holder.binding.emailTXT.text = userData.email
        holder.binding.mobileTXT.text = userData.mobile.toString()
        holder.binding.passwordTXT.text = userData.password
    }

    fun setData (dataCreList : List<UserData>) {
        dataList = dataCreList
        notifyDataSetChanged()
    }

}