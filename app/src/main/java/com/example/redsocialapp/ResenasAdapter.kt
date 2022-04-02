package com.example.redsocialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ResenasAdapter(
    var resenaList: MutableList<String>):
    RecyclerView.Adapter<ResenasAdapter.ResenasViewHolder>() {

        class ResenasViewHolder(item: View):RecyclerView.ViewHolder(item){
            var cardView: CardView
            var tvResena: TextView

            init {
                cardView = item.findViewById(R.id.cv_resenas)
                tvResena = item.findViewById(R.id.tv_resena)

            }

            fun bind(resenas: String){
                tvResena.text = resenas
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResenasViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return ResenasViewHolder(view)
    }

    override fun onBindViewHolder(resenasViewHolder: ResenasViewHolder, position: Int) {
        val resena: String = resenaList[position]
        resenasViewHolder.cardView.tag = position
        resenasViewHolder.bind(resena)
    }

    override fun getItemCount(): Int {
        return resenaList.size
    }
}