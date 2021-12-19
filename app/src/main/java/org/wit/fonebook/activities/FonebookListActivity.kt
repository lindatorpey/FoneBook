package org.wit.fonebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFonebookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FonebookAdapter(app.fonebooks.findAll(), this)
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

    override fun onFonebookClick(fonebook: FonebookModel) {
        val launcherIntent = Intent(this, FonebookActivity::class.java)
        launcherIntent.putExtra("fonebook_edit", fonebook)
        startActivityForResult(launcherIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}


