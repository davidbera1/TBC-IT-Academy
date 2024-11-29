package com.example.learnandroid

fun main() {

    val class1 = ArithmeticOperations()

    // task 1 example
    println("Task 1 example:")
    println(class1.usg(a=10, b=0))
    println(class1.usg(a=7, b=7))
    println(class1.usg(a=18942, b=1224))
    println(class1.usg(a=0, b=0))

    // task 2 example
    println("\nTask 2 example:")
    println(class1.usj(a=1105, b=2341))
    println(class1.usj(a=0, b=0))

    // task 3 example
    println("\nTask 3 example:")
    println(class1.hasDollar(text="Hello, Wo\$rld"))
    println(class1.hasDollar(text="Hello, World"))

    // task 4 example
    println("\nTask 4 example:")
    println(class1.evenSumTo100()) // by default num=0, ითვლის 0-დან
    println(class1.evenSumTo100(num=50)) // ჯამის დათვლას დაიწყებს 50 დან

    // task 5.1 example
    println("\nTask 5.1 example:")
    println(class1.reverseNumber(number=123450))

    // task 5.2 example
    println("\nTask 5.2 example:")
    println(class1.reverseNumber1(number=1034567890))

    // task 6.1 example
    println("\nTask 6.1 example:")
    println(class1.isPalindrome(text="davit"))
    println(class1.isPalindrome(text="level"))
    println(class1.isPalindrome(text="refer"))

    // task 6.2 example
    println("\nTask 6.2 example:")
    println(class1.isPalindrome1(text="davit"))
    println(class1.isPalindrome1(text="level"))
    println(class1.isPalindrome1(text="refer"))

}

class ArithmeticOperations {
    // task 1
    fun usg(a: Int, b: Int): Int {
        var a = a
        var b = b
        // ევკლიდეს ალგორითმი: თუ b=0, usg=a ან პირიქით. თუ b!=0, usg(a, b) = usg(b, a % b)
        if (b == 0) {
            return a
        }
        else if(a == 0) {
            return b
        }
        else if(a == b) {
            return a
        }
        else {
            var temp = 0
            do {
                temp = a
                a = b
                b = temp % b

            } while(a % b != 0)
        }
        return b
    }

    // task 2
    // აქაც ევკლიდეს ალგორითმი უსგ-ს დახმარებით: usj(a,b) = (a*b) / usg(a,b)
    fun usj(a: Int, b: Int): Int{
        try {
            return (a * b) / usg(a, b)
        } catch (e: ArithmeticException) {
            println("Error $e, returning 0")
            return 0
        }

    }

    // task 3
    fun hasDollar(text: String): Boolean {
        for (i in text) {
            if (i == '$') {
                return true
            }
        }
        return false
    }

    // task 4
    fun evenSumTo100(num: Int=0, sum: Int=0): Int {
        if (num > 100) {
            return sum
        }
        return evenSumTo100(num=num+2, sum=sum+num)
    }

    // task 5 reversed() გამოყენებით
    fun reverseNumber(number: Int): Int {
        val reversed = number.toString().reversed().toInt()
        return reversed
    }

    // task 5 reversed()-ის გარეშე
    fun reverseNumber1(number: Int): Int {
        val num = number.toString()
        var reversedNum = ""
        val numSize = num.length
        for (i in numSize-1 downTo 0) {
            reversedNum+= num[i]
        }
        return reversedNum.toInt()
    }

    // task 6 reversed() გამოყენებით
    fun isPalindrome(text: String): Boolean {
        return text == text.reversed()
    }

    // task 6 reversed()-ის გარეშე
    fun isPalindrome1(text: String): Boolean {
        var reversedText = ""
        val textSize = text.length
        for (i in textSize-1 downTo 0) {
            reversedText += text[i]
        }
        return text == reversedText
    }

}