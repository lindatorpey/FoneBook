package org.wit.fonebook.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.fonebook.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "fonebooks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FonebookModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FonebookJsonStore(private val context: Context) : FonebookStore {
    var fonebooks = mutableListOf<FonebookModel>()

    init {
        if (exists(context, JSON_FILE)){
            deserialize()
        }
    }

    override fun findAll(): MutableList<FonebookModel>{
        logAll()
        return fonebooks
    }

    override fun create(fonebook: FonebookModel) {
        fonebook.id = generateRandomId()
        fonebooks.add(fonebook)
        serialize()
    }

    override fun update(fonebook: FonebookModel) {
        val fonebooksList = findAll() as ArrayList<FonebookModel>
        var foundFonebook: FonebookModel? = fonebooksList.find { p -> p.id == fonebook.id }
        if (foundFonebook != null){
            foundFonebook.title = fonebook.title
            foundFonebook.address = fonebook.address
            foundFonebook.number = fonebook.number
            foundFonebook.email = fonebook.email
            foundFonebook.image = fonebook.image
            foundFonebook.lat = fonebook.lat
            foundFonebook.lng = fonebook.lng
            foundFonebook.zoom = fonebook.zoom
        }
        serialize()
    }
    override fun delete(placemark: FonebookModel) {
        fonebooks.remove(placemark)
        serialize()
    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(fonebooks, listType)
        write(context, JSON_FILE, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        fonebooks = gsonBuilder.fromJson(jsonString, listType)
    }
    private fun logAll(){
        fonebooks.forEach{Timber.i("$it")}
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
    context: JsonSerializationContext?
    ): JsonElement{
        return JsonPrimitive(src.toString())
    }

}