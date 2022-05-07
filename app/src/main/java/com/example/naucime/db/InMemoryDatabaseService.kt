package com.example.naucime.db

import com.example.naucime.model.Lesson
import com.example.naucime.model.Professor
import com.example.naucime.model.Student

class InMemoryDatabaseService : DatabaseService {

    var professorList : MutableList<Professor> = mutableListOf()
    var studentList : MutableList<Student> = mutableListOf()
    var lessonList : MutableList<Lesson> = mutableListOf()

    override fun connect(): DatabaseService {
        return this
    }

    override fun getProfessors(): List<Professor> {
        return this.professorList
    }

    override fun getStudents(): List<Student> {
        return this.studentList
    }

    override fun getLessons(): MutableList<Lesson> {
        return lessonList
    }

    override fun addLesson (lesson: Lesson) {
        lessonList.add(lesson)
        println("${lesson.name}, ${lesson.price}, ${lesson.professor.name}" )
    }

    override fun removeLesson(lesson: Lesson) {
        val iterator = lessonList.iterator()
        while(iterator.hasNext()) {
            val l = iterator.next()
            if(l.name == lesson.name && lesson.professor.email == l.professor.email) {
                iterator.remove()
            }
        }
    }

    override fun getLessonsByProfessor(professor: Professor) : MutableList<Lesson>{
       var list : MutableList<Lesson> = mutableListOf()
        for(l in lessonList){
            if (l.professor.email == professor.email)
                list.add(l)
        }
        return list
    }

    override fun addProfessor(professor: Professor) {
        professorList.add(professor)
        println(professorList)
    }

    override fun getProfessor(email: String): Professor {
        var help : Professor = Professor("","", "")
        for (p in professorList){
            if(p.email == email ) {
                help = p
                break
            }
        }
        return help
    }

    override fun getCertainLesson(professor: Professor, lname: String): Lesson {
        var lessons = getLessonsByProfessor(professor)
        var tmp = Lesson("", -1, Professor("", "", ""))
        for (l in lessons){
            if(l.name == lname){
                tmp = l
                break;
            }
        }
        return tmp

    }

    override fun getSubscribedStudents(professor: Professor, lname:String): MutableList<Student> {
        var lesson = getCertainLesson(professor, lname)
        return lesson.subscribers
    }

    override fun getStudent(email :String): Student{
        var iter = studentList.iterator()

        while(iter.hasNext()){
            var student = iter.next()
            if(student.email == email){
                return student
            }
        }
        return Student("","","")
    }

    override fun addStudent(student: Student) {
        this.studentList.add(student)
        println("The ${student} is added")
    }

    override fun deleteSubscribedStudent(professor: Professor, lname: String, email: String) {
        var student = getStudent(email)
        var lesson = getCertainLesson(professor, lname)
        var iterator = lesson.subscribers.iterator()
        while(iterator.hasNext()){
            var student = iterator.next()
            if (student.email == email)
                iterator.remove()
        }
    }

}