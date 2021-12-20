package org.wit.fonebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.fonebook.R
import org.wit.fonebook.adapters.FonebookAdapter
import org.wit.fonebook.adapters.FonebookListener
import org.wit.fonebook.databinding.ActivityFonebookListBinding
import org.wit.fonebook.main.MainApp
import org.wit.fonebook.models.FonebookModel

class FonebookListActivity : AppCompatActivity(), FonebookListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFonebookListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFonebookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = FonebookAdapter(app.fonebooks.findAll(), this)
        loadFonebooks()

        loadFonebooks()
        registerRefreshCallback()
        registerMapCallback()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FonebookActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, FonebookMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFonebookClick(fonebook: FonebookModel) {
        val launcherIntent = Intent(this, FonebookActivity::class.java)
        launcherIntent.putExtra("fonebook_edit", fonebook)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadFonebooks() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {}
    }

    private fun loadFonebooks(){
        showFonebooks(app.fonebooks.findAll())
    }
    fun showFonebooks(fonebooks: List<FonebookModel>){
        binding.recyclerView.adapter = FonebookAdapter(fonebooks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}


