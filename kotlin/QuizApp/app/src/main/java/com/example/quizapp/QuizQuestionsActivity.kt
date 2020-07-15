package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var currQuestion: Int = 0
    private var answerSelected: Int = 0
    private var questionsList: ArrayList<Question>? = null
    private var correctAnswer: Int = 0
    private var lockedAnswer: Boolean = false
    private var hits: Int = 0
    private var userName: String? = null
    private var userTapped: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        questionsList = Constants.getQuestions()
        Log.d("Questions", questionsList.toString())
        setQuestion()

        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnSubmitAnswer.setOnClickListener(this)
    }

    private fun setQuestion() {
        defaultOptionsView()

        val question = questionsList!![currQuestion]

        if(currQuestion == questionsList!!.size)
            btnSubmitAnswer.text = getString(R.string.finish_quiz)
        else
            btnSubmitAnswer.text = getString(R.string.go_to_next)

        tvQuestion.text = question.question
        ivCountryFlag.setImageResource(question.image)
        pbQuestionsAnswered.progress = currQuestion + 1
        tvProgressBar.text = "${currQuestion + 1}/${pbQuestionsAnswered.max}"
        btnOne.text = question.answers[0]
        btnTwo.text = question.answers[1]
        btnThree.text = question.answers[2]
        btnFour.text = question.answers[3]
        correctAnswer = question.correctAnswer
        answerSelected = 0
        lockedAnswer = false
    }

    private fun defaultOptionsView() {
        val options = ArrayList<Button>()

        options.add(0, btnOne)
        options.add(1, btnTwo)
        options.add(2, btnThree)
        options.add(3, btnFour)

        for(option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,
                R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        userTapped = true
        when(v?.id) {
            R.id.btnOne -> if(!lockedAnswer) selectedOptionView(btnOne, 0)
            R.id.btnTwo -> if(!lockedAnswer) selectedOptionView(btnTwo, 1)
            R.id.btnThree -> if(!lockedAnswer) selectedOptionView(btnThree, 2)
            R.id.btnFour -> if(!lockedAnswer) selectedOptionView(btnFour, 3)
            R.id.btnSubmitAnswer -> {
                lockedAnswer = true
                 if(answerSelected == -1 && userTapped) {
                    currQuestion++
                    when {
                         currQuestion < questionsList!!.size -> {
                             setQuestion()
                         } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, hits)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    if(correctAnswer != answerSelected)
                        answerView(answerSelected, R.drawable.wrong_option_border_bg)
                     else
                        hits++ // Correct answer!

                     answerView(correctAnswer, R.drawable.correct_option_border_bg)

                    if(currQuestion == questionsList!!.size)
                        btnSubmitAnswer.text = getString(R.string.finish_quiz)
                    else
                        btnSubmitAnswer.text = getString(R.string.go_to_next)
                    answerSelected = -1
                }
            }
        }
    }

    private fun selectedOptionView(btn: Button, selectedAnswerNumber: Int) {
        defaultOptionsView()
        answerSelected = selectedAnswerNumber
        btn.setTextColor(Color.parseColor("#363A43"))
        btn.typeface = Typeface.DEFAULT_BOLD
        btn.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer) {
            0 -> btnOne.background = ContextCompat.getDrawable(this, drawableView)
            1 -> btnTwo.background = ContextCompat.getDrawable(this, drawableView)
            2 -> btnThree.background = ContextCompat.getDrawable(this, drawableView)
            3 -> btnFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}