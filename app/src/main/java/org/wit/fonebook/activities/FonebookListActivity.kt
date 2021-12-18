package org.wit.fonebook.activities

import android.content.Intent
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.fonebook.R
import org.wit.fonebook.databinding.ActivityFonebookBinding
import org.wit.fonebook.databinding.ActivityFonebookListBinding
import org.wit.fonebook.databinding.CardFonebookBinding
import org.wit.fonebook.main.MainApp
import org.wit.fonebook.models.FonebookModel

class FonebookListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFonebookListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFonebookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FonebookAdapter(app.fonebooks)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FonebookActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class FonebookAdapter constructor(private var fonebooks: List<FonebookModel>):
        RecyclerView.Adapter<FonebookAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder{
        val binding = CardFonebookBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int){
        val fonebook = fonebooks[holder.adapterPosition]
        holder.bind(fonebook)
    }
    override fun getItemCount(): Int = fonebooks.size

    class MainHolder(private val binding : CardFonebookBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(fonebook: FonebookModel){
                    binding.fonebookTitle.text = fonebook.title
                    binding.address.text = fonebook.address
                    binding.number.text = fonebook.number
                    binding.email.text = fonebook.email
                }
            }
        }