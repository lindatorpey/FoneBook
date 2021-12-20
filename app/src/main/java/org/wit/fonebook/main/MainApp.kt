package org.wit.fonebook.main

import android.app.Application
import org.wit.fonebook.models.FonebookJsonStore
import org.wit.fonebook.models.FonebookMemStore
import org.wit.fonebook.models.FonebookModel
import org.wit.fonebook.models.FonebookStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    //val fonebooks = ArrayList<FonebookModel>()
    lateinit var fonebooks: FonebookStore

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //fonebooks = FonebookMemStore()
        fonebooks = FonebookJsonStore(applicationContext)
        i("Fonebook started")

    }
}