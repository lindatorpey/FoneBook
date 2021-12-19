package org.wit.fonebook.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FonebookModel(var id: Long = 0,
                         var title: String = "",
                        var address: String = "",
                        var number: String = "",
                        var email: String = "") : Parcelable
