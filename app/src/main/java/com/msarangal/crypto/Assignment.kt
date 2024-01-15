package com.msarangal.crypto

data class Course(val id: Int, val name: String, val isPaid: Boolean)

data class Student(val id: Int, val name: String, val subscribedCourses: List<Course>)

interface Repository<T> {
    fun get(): Iterable<T>
}

val abc = "Manu"

class University(private val repository: Repository<Student>) {
    fun getPaidCoursesWithTheNumbersOfSubscribedStudents(coursesCount: Int): Map<Course, Int> =
        repository.get().flatMap {
            it.subscribedCourses
        }.filter {
            it.isPaid
        }.groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(coursesCount)
            .toMap()

}

fun main() {
    val repo = RepositoryImpl()
    val university = University(repo)

    val map = university.getPaidCoursesWithTheNumbersOfSubscribedStudents(3)

    map.forEach {
        print("${it.key.name} : ${it.value} \n")
    }
}

class RepositoryImpl : Repository<Student> {

    override fun get(): Iterable<Student> {
        return getStudents().asIterable()
    }

    private fun getStudents(): List<Student> {
        return listOf(
            Student(
                id = 1,
                name = "Mandeep",
                subscribedCourses = listOf(
                    Course(id = 1, name = "Maths", isPaid = false),
                    Course(id = 2, name = "Arts", isPaid = true)
                )
            ),
            Student(
                id = 1,
                name = "Navdeep",
                subscribedCourses = listOf(
                    Course(id = 1, name = "History", isPaid = true),
                    Course(id = 2, name = "Biology", isPaid = true)
                )
            ),
            Student(
                id = 1,
                name = "Roger",
                subscribedCourses = listOf(
                    Course(id = 1, name = "Physics", isPaid = true),
                    Course(id = 2, name = "History", isPaid = true)
                )
            ),
            Student(
                id = 1,
                name = "Sam",
                subscribedCourses = listOf(
                    Course(id = 1, name = "Physics", isPaid = true),
                    Course(id = 2, name = "History", isPaid = true)
                )
            )
        )
    }
}

/**
 *
 * Student(subscribedCourses = [Course(name = "Maths", isPaid = false), Course(name = "Arts", isPaid = true)])
 * Student(subscribedCourses = [Course(name = "History", isPaid = true), Course(name = "Biology", isPaid = true)])
 * Student(subscribedCourses = [Course(name = "Physics", isPaid = true), Course(name = "History", isPaid = true)])
 *
 *
 */