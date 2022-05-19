package com.example.naucime.db

import com.example.naucime.model.Lesson
import com.example.naucime.model.Professor
import com.example.naucime.model.Student

private var firstTimeIn: Boolean = true

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
        println("AddLesson function: ${lesson.name}, ${lesson.price}, ${lesson.professor.email}" )
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
        println("addProfessor function: " + professorList)
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
                return l
            }
        }
        return tmp
    }

    override fun getSubscribedStudents(pEmail: String, lname:String): MutableList<Student> {
        var professor = getProfessor(pEmail)
        var lesson = getCertainLesson(professor, lname)
        return lesson.subscribers
    }

    override fun getStudent(email: String?): Student{
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

    override fun subscribeToLesson(lesson: Lesson, student: Student): Boolean {
        if(lesson.professor.email != student.email) {
            lesson.subscribers.add(student)
            return true
        }
        else
            return false
    }

    override fun dataInit() {

        if(firstTimeIn) {
            var professorMilos =
                Professor("Milos", "Delic", "milosdelic@gmail.com") //pass: milos123
            var professorPera = Professor("Pera", "Peric", "peraperic@gmail.com") // pass: pera123
            var professorAna = Professor("Ana", "Anic", "anaanic@yahoo.com") // pass: ana123

            DatabaseServiceProvider.db.addProfessor(professorMilos)
            DatabaseServiceProvider.db.addProfessor(professorPera)
            DatabaseServiceProvider.db.addProfessor(professorAna)

            DatabaseServiceProvider.db.addLesson(
                Lesson(
                    "Diskretne strukture 1",
                    500,
                    professorMilos
                )
            )
            DatabaseServiceProvider.db.addLesson(Lesson("Geometrija I smer", 600, professorPera))
            DatabaseServiceProvider.db.addLesson(Lesson("Programiranje 1", 1000, professorAna))
            DatabaseServiceProvider.db.addLesson(Lesson("Programiranje 2", 1100, professorAna))

            var studentMirko: Student = Student("Mirko", "Kordic", "mirko@gmail.com")
            addStudent(studentMirko)
            var lessonDS: Lesson =
                DatabaseServiceProvider.db.getCertainLesson(professorMilos, "Diskretne strukture 1")
            studentMirko.subscribeToLesson(lessonDS)

            firstTimeIn = false
        }
    }

}