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

        holder.nama.text = currenItem.nama
        holder.contact.text = currenItem.contact
        holder.brand.text = currenItem.brand
        holder.problem.text = currenItem.problem
        holder.series.text = currenItem.series
        holder.diagnosa.text = currenItem.diagnosa

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nama : TextView = itemView.findViewById(R.id.txt_cv_name)
        val contact : TextView = itemView.findViewById(R.id.txt_cv_contact)
        val brand : TextView = itemView.findViewById(R.id.txt_cv_brand)
        val problem : TextView = itemView.findViewById(R.id.txt_cv_problem)
        val series : TextView = itemView.findViewById(R.id.txt_cv_series)
        val diagnosa : TextView = itemView.findViewById(R.id.txt_cv_diagnosa)
    }

}