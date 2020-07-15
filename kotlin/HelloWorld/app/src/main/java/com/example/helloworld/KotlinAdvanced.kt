package com.example.helloworld

import java.lang.ArithmeticException

fun main () {
    val numbers = ArrayList<Double>()

    numbers.add(4.3)
    numbers.add(2.3)
    numbers.add(1.3)
    numbers.add(6.3)
    numbers.add(6.9)

    var total: Double = 0.0
    for (number in numbers)
        total += number

    println("Avg: ${total / numbers.size}")

    // Lambda Expressions
    fun addNumbers(a: Int, b: Int): Int {
        return a + b
    } // this is the 'normal' way, the lambda equivalent is:

    val sum1: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    println(sum1(5, 10))

    // even shorter
    val sum2 = { a: Int, b: Int -> println(a + b) }
    sum2(5, 20)

    // Visibility Modifiers
    // - public (element is accessible from anywhere in the project. It's the default visibility.)
    public class Example {
        public fun hello() {}
        fun demo{}
        public val x = 5
        val y = 10
    }
    class Demo {}

    // - private (elements are accessible only within the block in which properties, fields, etc. are declared.)
    private class Example1 {
        private val x = 1
        private fun doSomething() {}
    }

    // - protected (allows visibility to its class or subclass only. A protected overriden in its
    // subclass is also protected, unless it's explicit changed. This modifier cannot be declared
    // at top level - for Packages.)
    open class Base {
        protected val i = 0
    }

    class Derived : Base() {
        fun getValue(): Int {
            return i
        }
    }

    // --- overriding protected types
    open class Base1 {
        protected open val i = 5
    }

    class Another : Base1() {
        fun getValue(): Int {
            return i
        }

        override val i = 10
    }

    // - internal (element is visible only inside the module in which it's implemented.)
    internal class Example2 {
        internal val x = 5
        internal fun getValue() {}
    }
    internal val y = 10

    // By default, all classes are FINAL in Kotlin, so they can't be inherited by default.
    // So you need to mark a class 'open' so it can be inherited

    // NESTED CLASS
    // it's created inside another class. In Kotlin a nested class is by default static,
    // so its attributes and methods can be accessed without creating an object of the class
    // but the nested class cannot access data from the outer class

    class OuterClass {
        private var name: String = "Mr X"

        class NestedClass {
            var description: String = "i'm inside the nest."
            private var id: Int = 101
            fun foo() {
                // println("name is $name") <- cannot access the outer class attribute
                println("id is $id")
            }
        }
    }

    // to access an attribute: println(OuterClass.NestedClass.description)
    // to access a method: var obj = OuterClass.NestedClass(); obj.foo()

    // INNER CLASS
    // basically, it's just a nested class with the keyword "inner". But they cannot be declared
    // inside interfaces or non-inner nested classes. The good thing about it though is that it
    // can access the attributes of the outer class even when it's private, since it keeps an
    // reference to an object of its outer class

    class OuterClass2 {
        private var name: String = "Daniel"
        inner class InnerClass {
            var  description: String = "Mumblemumble..."
            private var id: Int = 101
            fun foo() {
                println("name is $name") // totally possible now ;)
                println("Id is $id")
            }
        }
    }

    // UNSAFE AND SAFE CAST OPERATORS

    // Unsafe cast operator: as
    // Sometimes it's not possible to cast a variable and it throws an exception, this is called
    // unsafe cast.
    // Example: a nullable string (String?) cannot be cast to non nullable string (String), this throws
    // an exception.
    fun unsafe() {
        var obj: Any? = null
        val str: String = obj as String // Expection in thread 'main' kotlin.TypeCastException: null cannot be cast to non-null type Kotlin String.
        println(str)
    }

    // Generates a ClassCastException
    // Trying to cast an integer value of the Any type into a string type leads to a ClassCastException
    val obj: Any = 123
    val str: String = obj as String // Throws java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String

    // Nullable for Casting to work:
    // Source and target variables needs to be a nullable for casting to work:
    fun workingCast() {
        val obj: Any? = "String unsafe cast"
        val str: String? = obj as String? // Works
        println(str) // Output: String unsafe cast
    }

    // Safe cast operator: as?
    // It returns null if the casting is not possible rather than throwing a ClassCastException.
    fun safe() {
        val location: Any = "Kotlin"
        val safeString: String? = location as? String
        val safeInt: Int? = location as? Int
        println(safeString)
        println(safeInt) // Output: Kotlin\nnull
    }

    // EXCEPTION HANDLING
    // Is a technique which handlesthe runtime problems and maintains the flow of program execution.
    // There are four different keywords used in exception handling:
    // * try -> contains a set of statements which might generate an exception. It must be followed by catch or finally or both.
    // * catch -> catches the exception thrown by the try block.
    // * finally -> always executes whether an exception occurred or not.
    // * throw -> used to throw an exception explicitly.

    // Throwable Class
    // throw MyException ("this throws an exception.")

    // Unchecked Exception
    // Is that exception which is thrown due to mistakes in our code. This exception extends the RuntimeException class.
    // The Unchecked Exception is checked at run time. Examples:
    // * ArithmeticException -> trying to divide a number by 0.
    // * ArrayIndexOutOfBoundsException -> trying to access an incorrect index value
    // * SecurityException -> thrown by the security manager to indicate a security violation.
    // * NullPointerException -> trying to invoke a method or property on a null object

    // Checked Exception
    // Is checked at compile time. This exception type extends the Throwable class. Examples:
    // * IOExcepetion
    // * SQLException

    // try-catch
    val strTry = "10.6"
    var done = "No!"
    try {
        if (strTry !is String)
            throw ArithmeticException("it needs to be a String.");
        Integer.parseInt(strTry)
    } catch (e: ArithmeticException) {
        0
    } finally {
        done = "Yes!"
    }
    println(strTry)
}