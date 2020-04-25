package com.evilgeniuses.hackathonyohack.models

data class FaqQuestion(
        val question: String,
        val tags: String,
        val answer: String,
        var relevantIndex: Int = 0
)