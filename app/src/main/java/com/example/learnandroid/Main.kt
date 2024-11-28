package com.example.learnandroid

fun main() {
    do {
        println("შეიყვანეთ X ცვლადის მნიშვნელობა: ")
        val x = readLine()!!
        println("შეიყვანეთ Y ცვლადის მნიშვნელობა: ")
        val y = readLine()!!

        if (getNumbersFromString(y) != 0.0) {
            val z = getNumbersFromString(x) / getNumbersFromString(y)
            println("X და Y განაყოფი არის: $z")
        }
        else {
            println("X-ის Y-ზე გაყოფა არ შეიძლება, Y=0.")
        }

        println("გსურთ პროგრამის ხელახლა დაწყება <Y/N>?")
        val answer = readLine()
    } while (answer?.lowercase() == "y")
}

fun getNumbersFromString(text: String): Double {
    var result = "0"
    for (i in text) {
        if(i.isDigit()) {
            result += i
        }
    }
    return result.toDouble()
}