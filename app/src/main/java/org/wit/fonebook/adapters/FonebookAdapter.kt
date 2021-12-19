package org.wit.fonebook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.fonebook.databinding.CardFonebookBinding
import org.wit.fonebook.models.FonebookModel

interface FonebookListener {
    fun onFonebookClick(fonebook: FonebookModel)
}

class FonebookAdapter constructor(private var fonebooks: List<FonebookModel>,
                                    private val listener:FonebookListener):
                    RecyclerView.Adapter<FonebookAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder{
        val binding = CardFonebookBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder:MainHolder, position: Int) {
        val fonebook = fonebooks[holder.adapterPosition]
        holder.bind(fonebook, listener)
    }
    override fun getItemCount(): Int = fonebooks.size

    class MainHolder(private val binding: CardFonebookBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(fonebook: FonebookModel, listener: FonebookListener){
                    binding.fonebookTitle.text = fonebook.title
                    binding.address.text = fonebook.address
                    binding.number.text = fonebook.number
                    binding.email.text = fonebook.email
                    binding.root.setOnClickListener { listener.onFonebookClick(fonebook) }
                }
            }
}