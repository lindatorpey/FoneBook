package org.wit.fonebook.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.fonebook.R
import org.wit.fonebook.databinding.ActivityFonebookBinding
import org.wit.fonebook.helpers.showImagePicker
import org.wit.fonebook.main.MainApp
import org.wit.fonebook.models.FonebookModel
import org.wit.fonebook.models.Location
import timber.log.Timber
import timber.log.Timber.i

class FonebookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFonebookBinding
    var fonebook = FonebookModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)

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
            Picasso.get()
                .load(fonebook.image)
                .into(binding.fonebookImage)
            if (fonebook.image != Uri.EMPTY){
                binding.chooseImage.setText(R.string.change_fonebook_image)
            }
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.fonebookLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (fonebook.zoom != 0f) {
                location.lat =  fonebook.lat
                location.lng = fonebook.lng
                location.zoom = fonebook.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            fonebook.image = result.data!!.data!!
                            Picasso.get()
                                .load(fonebook.image)
                                .into(binding.fonebookImage)
                            binding.chooseImage.setText(R.string.change_fonebook_image)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            fonebook.lat = location.lat
                            fonebook.lng = location.lng
                            fonebook.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}