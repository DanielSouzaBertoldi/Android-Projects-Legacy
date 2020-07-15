package com.example.quizapp

import java.util.*
import kotlin.collections.ArrayList

object Constants {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions() : ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val q1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            listOf("Argentina", "Australia", "Armenia", "Austria"),
            0)

        val q2 = Question(2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            listOf("Argentina", "Australia", "Armenia", "Austria"),
            1)

        val q3 = Question(3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            listOf("Belgium", "Brazil", "Bahamas", "Bangladesh"),
            0)

        val q4 = Question(4,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            listOf("Belgium", "Brazil", "Bahamas", "Bangladesh"),
            1)

        val q5 = Question(5,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            listOf("Denmark", "Djibouti", "Dominica", "Dominican Republic"),
            0)

        val q6 = Question(6,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            listOf("Falkland Islands", "Faroe Islands", "Fiji", "Finland"),
            2)

        val q7 = Question(7,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            listOf("Gabon", "Gambia", "Georgia", "Germany"),
            3)

        val q8 = Question(8,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            listOf("Iceland", "India", "Indonesia", "Iran"),
            1)

        val q9 = Question(9,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_kuwait,
            listOf("Kazakhstan", "Kenya", "Kiribati", "Kuwait"),
            3)

        val q10 = Question(10,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_new_zealand,
            listOf("Nauru", "Nepal", "Netherlands", "New Zealand"),
            3)

        questionsList.addAll(listOf(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10).shuffled())

        return questionsList
    }
}