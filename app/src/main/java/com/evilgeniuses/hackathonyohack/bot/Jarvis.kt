package com.evilgeniuses.hackathonyohack.bot

import com.evilgeniuses.hackathonyohack.dict.JarvisRequestDictionary.*
import com.evilgeniuses.hackathonyohack.dict.JarvisResponseDictionary.*
import com.evilgeniuses.hackathonyohack.models.FaqQuestion

class Jarvis(private val listOfQuestions: List<FaqQuestion>) {

    fun precessMessage(msg: String): String {
        return when (msg) {
            HELLO1.text, HELLO.text, HELLO3.text, HELLO4.text -> JARVIS_HELLO.text
            else -> {
                val findFaq = findFaq(msg)
                if (findFaq.isEmpty()) {
                    return getRandomAnswer()
                }
                return findFaq
            }
        }
    }

    private fun getRandomAnswer(): String {
        val randomAnswer = listOf(JARVIS_RESPONSE1, JARVIS_RESPONSE2, JARVIS_RESPONSE3)
        return randomAnswer.random().text
    }

    private fun findFaq(question: String): String {
        val splitQuestion = question.trim().split(" ")
        for (faqQestion in listOfQuestions) {
            val splitTags = faqQestion.tags.split(",")
            for (word in splitQuestion) {
                for (tag in splitTags) {
                    //Поиск по тэгу
                    if (word.contains(tag, true) || tag.contains(word, true)) {
                        faqQestion.relevantIndex++
                    }
                }
                //Поиск по вопросу
                if (faqQestion.question.contains(word, true)) {
                    faqQestion.relevantIndex++
                }
                //Поиск по ответу
                if (faqQestion.answer.contains(word, true)) {
                    faqQestion.relevantIndex++
                }
            }
        }

        val relevantQestion = findRelevantQuestion(listOfQuestions)
        if (relevantQestion.relevantIndex > 0) {
            resetRelevantIndexes()
            return "${relevantQestion.question}\n${relevantQestion.answer}"
        }

        return ""

    }

    private fun resetRelevantIndexes() {
        for (faqQustion in listOfQuestions) {
            faqQustion.relevantIndex = 0
        }
    }

    private fun findRelevantQuestion(questions: List<FaqQuestion>): FaqQuestion {
        var relevantQuestion: FaqQuestion = questions[0]
        for (question in questions) {
            question.apply {
                if (relevantIndex > relevantQuestion.relevantIndex) {
                    relevantQuestion = question
                }
            }
        }

        return relevantQuestion
    }

    companion object {
        @JvmStatic
        fun getBotInstance(listOfQuestions: List<FaqQuestion>) = Jarvis(listOfQuestions)
    }
}