package org.wit.fonebook.models

interface FonebookStore {
    fun findAll(): List<FonebookModel>
    fun create(fonebook: FonebookModel)
    fun update(fonebook: FonebookModel)
}