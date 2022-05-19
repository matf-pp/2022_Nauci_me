package com.example.naucime.db

import com.example.naucime.model.Lesson
import com.example.naucime.model.Professor
import com.example.naucime.model.Student

interface DatabaseService {

    fun connect() : DatabaseService
    fun getProfessors (): List<Professor>
    fun getStudents () : List<Student>
    fun getLessons(): MutableList<Lesson>
    fun addLesson (lesson: Lesson)
    fun removeLesson (lesson: Lesson)
    fun getLessonsByProfessor (professor: Professor) : MutableList<Lesson>
    fun getCertainLesson (professor: Professor, lname: String) : Lesson
    fun addProfessor(professor: Professor)
    fun getProfessor(email: String) : Professor
    fun getSubscribedStudents(pEmail: String, lname: String): MutableList<Student>
    fun deleteSubscribedStudent(professor: Professor, lname: String, email: String)
    fun getStudent(email: String?): Student
    fun addStudent(student: Student)
    fun subscribeToLesson(lesson: Lesson, student: Student): Boolean
    fun dataInit()

}