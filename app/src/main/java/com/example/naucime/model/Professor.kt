package com.example.naucime.model

import com.example.naucime.db.DatabaseServiceProvider
import com.example.naucime.db.InMemoryDatabaseService
import kotlinx.serialization.Serializable


@Serializable
class Professor (fName: String, lName: String, pEmail: String){

    var name: String
    var lastName: String;
    var email: String = ""

    init{
        name = fName
        lastName = lName
        email = pEmail
    }


    fun addLesson(lname: String, lprice: Int){
        var l: Lesson = Lesson(lname, lprice, this)
        DatabaseServiceProvider.db.addLesson(l)
    }

    override fun toString() : String {
        return this.name + ", " + this.lastName + ", " + this.email
    }



}