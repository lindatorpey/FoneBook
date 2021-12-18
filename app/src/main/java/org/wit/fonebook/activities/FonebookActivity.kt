package org.wit.fonebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.fonebook.databinding.ActivityFonebookBinding
import org.wit.fonebook.models.FonebookModel
import timber.log.Timber
import timber.log.Timber.i

class FonebookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFonebookBinding
    var fonebook = FonebookModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFonebookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Placemark Activity started..")

        binding.btnAdd.setOnClickListener() {
            fonebook.title = binding.fonebookTitle.text.toString()
            if (fonebook.title.isNotEmpty()) {
                i("add Button Pressed: $fonebook.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
}}