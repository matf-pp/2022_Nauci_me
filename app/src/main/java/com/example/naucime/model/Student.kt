package com.example.naucime.model

import com.example.naucime.db.DatabaseServiceProvider
import kotlinx.serialization.Serializable

@Serializable
class Student (sName: String, sLastName: String, sEmail: String) {
    var name: String;
    var lastName: String;
    var email: String

    init {
        name = sName;
        lastName = sLastName;
        email = sEmail
    }

    fun subscribeToLesson(lname: String, professor: Professor) {
        var lesson = DatabaseServiceProvider.db.getCertainLesson(professor, lname)
        lesson.subscribers.add(this)
    }

    override fun toString() :String {
        return this.name + ", " + this.lastName + ", " + this.email
    }

}