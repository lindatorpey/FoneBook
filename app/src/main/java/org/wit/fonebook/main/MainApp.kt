package org.wit.fonebook.main

import android.app.Application
import org.wit.fonebook.models.FonebookModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val fonebooks = ArrayList<FonebookModel>()

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Fonebook started")
        //fonebooks.add(FonebookModel("one","about"))
        //fonebooks.add(FonebookModel("two","about"))
        //fonebooks.add(FonebookModel("three","about"))
    }
}