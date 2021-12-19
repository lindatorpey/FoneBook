package org.wit.fonebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.fonebook.R
import org.wit.fonebook.databinding.ActivityFonebookBinding
import org.wit.fonebook.main.MainApp
import org.wit.fonebook.models.FonebookModel
import timber.log.Timber
import timber.log.Timber.i

class FonebookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFonebookBinding
    var fonebook = FonebookModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityFonebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

    app = application as MainApp
        i("Fonebook Activity Started")

        if (intent.hasExtra("fonebook_edit")){
            edit = true
            fonebook = intent.extras?.getParcelable("fonebook_edit")!!
            binding.fonebookTitle.setText(fonebook.title)
            binding.address.setText(fonebook.address)
            binding.number.setText(fonebook.number)
            binding.email.setText(fonebook.email)
            binding.btnAdd.setText(R.string.save_fonebook)
        }

        binding.btnAdd.setOnClickListener() {
            fonebook.title = binding.fonebookTitle.text.toString()
            fonebook.address = binding.address.text.toString()
            fonebook.number = binding.number.text.toString()
            fonebook.email = binding.email.text.toString()
            if (fonebook.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_fonebook_title, Snackbar.LENGTH_LONG)
                    .show()
            }else{
                if (edit){
                    app.fonebooks.update(fonebook.copy())
                }else{
                    app.fonebooks.create(fonebook.copy())
                }
            }

            setResult(RESULT_OK)
            finish()
            }
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_fonebook, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}