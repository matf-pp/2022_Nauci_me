package com.example.naucime.model

import com.example.naucime.db.DatabaseServiceProvider
import kotlinx.serialization.Serializable

@Serializable
class Student (sName: String, sLastName: String) {
    var name: String;
    var lastName: String;

    init {
        name = sName;
        lastName = sLastName;
    }

    // da li kada dodam u lesson novog studenta mogu da vidim da je on dodat iz profesora
    fun subscribeToLesson(lname: String, professor: Professor) {
        var lesson = DatabaseServiceProvider.db.getCertainLesson(professor, lname)
        lesson.subscribers.add(this)
    }

}