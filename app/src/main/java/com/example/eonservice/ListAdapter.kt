package com.example.eonservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_service.view.*

class ListAdapter(private val historyList : ArrayList<TransactionModel>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_history, parent, false)
        return ListViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val currenItem = historyList[position]

        holder.name.text = currenItem.brand
        holder.problem.text = currenItem.problem
        holder.number.text = currenItem.series

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.txt_cv_name)
        val problem : TextView = itemView.findViewById(R.id.txt_cv_problem)
        val number : TextView = itemView.findViewById(R.id.txt_cv_number)

    }

}