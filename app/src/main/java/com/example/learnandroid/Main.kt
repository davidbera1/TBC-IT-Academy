package com.example.learnandroid

fun main() {
    while(true){
        var doubleX: Double
        var doubleY: Double

        println("შეიყვანეთ X ცვლადის მნიშვნელობა: ")
        var x = readLine()

        println("შეიყვანეთ Y ცვლადის მნიშვნელობა: ")
        var y = readLine()

        if (x == null){
            doubleX = 0.0
        }
        else {
            doubleX = findNumbersInString(x)
        }

        if (y == null) {
            doubleY = 0.0
        }
        else {
            doubleY = findNumbersInString(y)
        }

        if (doubleY == 0.0) {
            println("X-ის Y-ზე გაყოფა არ შეიძლება, Y=0.")
        }
        else {
            val z = doubleX / doubleY
            println("X და Y განაყოფი არის: $z")
        }

        println("გსურთ პროგრამის ხელახლა დაწყება <Y/N>")
        val answer = readLine()

        if(answer?.lowercase() == "y") {
            continue
        }
        else {
            break
        }
    }
}

fun findNumbersInString(text: String) : Double {
    var numbers = ""
    var result: Double = 0.0

    for (i in text) {
        if (i.isDigit()) {
            numbers += i
        }
    }
    result = numbers.toDouble()
    return result
}
