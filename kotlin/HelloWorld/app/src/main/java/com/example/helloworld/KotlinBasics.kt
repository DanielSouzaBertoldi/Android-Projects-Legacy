package com.example.helloworld

fun main() {
    // type inference. Kotlin will automatically find the type being assigned to a variable
    var name = "Daniel"
    //var -> variables that are mutable. They can be changed in runtime.
    //val -> cannot be changed in runtime. Not immutable.
    print("Hello " + name + "!") //Prints in the console (a.j.a 4:Run tab)

    // Integer Types: Byte (8 bits), Short (16 bits), Int (32 bits) and Long (64 bits)
    val myByte: Byte = 24
    val myShort: Short = 300
    val myInt: Int = 293000
    val myLong: Long = 12_039_812_309_487_120

    // Floating Point number Types: Float (32 bit), Double (64 bit)
    val myFloat: Float = 13.37F
    val myDouble: Double = 3.14159265358979323846

    // Boolean type
    val isBoolean: Boolean = true

    // Character type
    val letterChar: Char = 'A'

    // Strings
    val myString = "Hello world!"
    val firstCharInStr = myString[0]
    val lastCharInStr = myString[myString.length - 1]

    // EXERCISE 1
    var courseName :String = "Android Masterclass"
    val leet : Float = 13.37F
    val pi : Double = 3.14159265358979
    var age : Byte = 25
    var year : Short = 2020
    var phoneNumber: Long = 18881234567
    var isGood : Boolean = true
    var firstCharacter : Char = 'a'

    // Arithmetic Operators (+, -, *, /, %) and Assignment Operators (+=, -=, *=, /=, %=)
    var result = 5 + 3 // 8
    result /= 2 // 4
    result *= 2 // 8
    result -= 2 // 4
    result %= 3 // 1

    // Comparison operators (==, !=, <, >, <=, >=)
    val isEqual = 5==3 // false
    print("isEqual is $isEqual") // Template (String Interpolation), easier to read than concatenation
    val isNotEqual = 5!=5 // true
    val isGreater = 5 > 8 // false
    print("is5greater3 ${5 > 3}") // Template with expression o.O
    val isLower = 5 < 8 // true
    val isGreaterOrEqual = 5 > 8 // false
    val isLowerOrEqual = 5 < 5 // true

    // Increment and Decrement Operators
    var myIncrement = 1
    println("myIncrement: $myIncrement")
    myIncrement++
    myIncrement--
    println("myIncrement: ${myIncrement++}")
    println("myIncrement: ${--myIncrement}")

    // If Statement
    var heightPerson1 = 170
    var heightPerson2 = 189

    if (heightPerson1 > heightPerson2)
        println("person 1 is higher.")
    else if (heightPerson1 == heightPerson2)
        println("they have the same height.")
    else
        println("person2 is higher")

    // If Exercise
    age = 28
    if (age >= 21)
        println("you can drink.")
    else if (age >= 18)
        println("you can vote.")
    else if (age >= 16)
        println("you can drive.")
    else
        println("you are too young brother.")

    // When Expressions (replacement for Switch Case :( darn)
    var season = 3

    when(season) {
        1 -> println("Spring")
        2 -> println("Summer")
        3 -> { //Multiline commands
            println("Fall")
            println("Autumn")
        }
        4 -> println("Winter")
        else -> println("Invalid season.")
    }

    var month = 3

    when (month) { // With range of values
        in 3..5 -> println("Spring/")
        in 6..8 -> println("Summer")
        in 9..11 -> {
            println("Fall")
            println("Autumn")
        }
        // It's possible to use the keyword "downTo" and check values from top to bottom like "in 12 downTo 2 -> do something"
        12, 1, 2 -> println("Winter") // If the month is one of the three values listed, print Winter
        else -> println("Invalid season.")
    }

    when (age) {
        !in 0..20 -> println("You can drink.")
        in 18..100 -> println("You can vote.")
        in 16..100 -> println("You can drive.")
        else -> println("You're either too young or dead.")
    }

    var x: Any = 13.37
    when(x) { // stops at first statement that's true
        is Int -> println("$x is an Int")
        is Double -> println("$x is a Double")
        is String -> println("$x is a String")
        !is Float -> println("$x is not a Float")
        else -> println("$x is none of the above")
    }

    // While loop executes a block of code repeatedly as long as a given condition is true
    x = 1
    while(x <= 10) {
        print(x)
        x++
    }
    println("\nWhile loop is done.")

    // Do..While
    do {
        print(x)
        x--
    } while(x >= 0)
    println("\nDo..While is done.")

    var feltTemp = "cold"
    var roomTemp = 10
    while(feltTemp == "cold") {
        if(++roomTemp >= 20){
            feltTemp = "comfy"
            println("It's comfy now.")
        }
    }

    // For Loops
    for (num in 1..10)
        print(num)
    print("\n")
    for (i in 1 until 10) // Same as - for (i in 1.until(10))
        print(i)
    print(" - Until\n")
    for (i in 10 downTo 1) // Same as - for (i in 10.downTo(1))
        print(i)
    print("- downTo\n")
    for (i in 10 downTo 1 step 2) // Same as - for (i in 10.downTo(1).step(2))
        print(i)
    print("- downTo with steps of 2\n")

    // For Loop - Exercise 1
    for (i in 1 until 10000) {
        if (i > 9000) {
            println("IT'S OVER 9000!")
            break
        }
    }

    // For Loop - Exercise 2
    var humidityLevel = 80
    var humidity = "humid"
    while(humidity == "humid") {
        humidityLevel -= 5
        println("Humidity decreased.")

        if (humidityLevel < 60) {
            print("It's comfy now.")
            humidity = "comfy"
        }
    }

    myFunction()
}

// fun nameOfFun(nameOfVar: Type): Return Type
fun addUp(a: Int, b: Int): Int {
    return a+b
}

fun avg(a: Double, b: Double): Double {
    return (a+b)/2
}

fun myFunction() {
    print("Called my Function\n")
    println(addUp(5, 3))
    println(avg(5.6, 3.2))
}

fun nullableFun() {
    var name: String = "Daniel"
    // name = null -> Compilation ERROR
    var nunnableName: String? = "Danielzão"
    nunnableName = null

    var len1 = name.length
    var len2 = nunnableName?.length //? is a safe call operator
    //println(nunnableName?.toLowerCase())
    //nunnableName?.let { println(it.length) }

    val name2 = nunnableName ?: "Guest" //?: if is empty, use default. Also called Elvis Operator.

    // Not null assertion : !! Operator
    // The !! operator converts a nullable type to a
    // non-null type, and throws a NullPointerException
    // if the nullable type holds a null value.
    // This is risky, and you should only use it if
    // you are 100% certain, that there will be a value in
    // the variable.
    //nunnableName!!.toLowerCase()

    // Chaining null checks
    //val wifesAge = String? = user?.wife?.age ?: 0
}