package org.wit.fonebook.models

import timber.log.Timber.i

var lastId =0L

internal fun getId(): Long{
    return lastId++
}

class FonebookMemStore: FonebookStore {
    val fonebooks = ArrayList<FonebookModel>()
    override fun findAll(): List<FonebookModel> {
        return fonebooks
    }
    override fun create(fonebook: FonebookModel){
        fonebook.id = getId()
        fonebooks.add(fonebook)
        logAll()
    }
    override fun update(fonebook: FonebookModel){
        var foundFonebook: FonebookModel? = fonebooks.find { p -> p.id == fonebook.id}
        if (foundFonebook != null){
            foundFonebook.title = fonebook.title
            foundFonebook.address = fonebook.address
            foundFonebook.number = fonebook.number
            fonebook.email = fonebook.email
            logAll()
        }
    }
    fun logAll() {
        fonebooks.forEach{ i("${it}")}
    }
}