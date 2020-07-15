package com.example.helloworld

import java.lang.IllegalArgumentException
import kotlin.math.floor

fun main() {
    var daniel: Person = Person("Daniel", "Bertoldi", 24)
    daniel.stateHobby()
    daniel.hobby = "gaming"
    daniel.stateHobby()

    var john = Person() //Will receive "John Doe" as name
    john.hobby = "cooking"
    john.stateHobby()
    var johnPeterson = Person(lastName = "Peterson") // Will receive "John" as first name

    var car = Car()
    //car.myModel = "M5" // Can't do it, the setter is private :(

    // Exercise
    var motorola = MobilePhone("Android", "Motorola", "OneVision")

    val user1 = User(1, "Daniel")
    val name = user1.name
    user1.name = "Damn!"
    val user2 = User(1, "Damn!")
    println(user1.equals(user2)) // or user1 == user2
    println("User Details: $user1")
    val updatedUser = user1.copy(name="Daniel Bertoldi") // It'll update all parameters except the ones declared
    println(updatedUser.component1()) // component1() -> will print the ID (first parameter of data class)

    val (id, name2) = updatedUser // the same as doing val id = updatedUser.id and val name2 = updatedUser.name

    // Exercise2
    var moto = MobilePhone2("Android", "Motorola", "OneVision")
    moto.chargeBattery(30)
    moto.chargeBattery(20)

    // Inheritance
    var myMoto = Moto("RX700", "Toyota", 200.0)
    var myEMoto = EletricMoto("RZ1000", "Ninja", 87.0, 300.0)
    // Polymorphism
    myMoto.drive(200.0)
    myEMoto.drive(200.0)

    myMoto.extendRange(10.0)
    // Any class inherits from the Any Class.
    myEMoto.extendRange(2.0)
    myEMoto.drive(20.0)
    myEMoto.drive()

    // Typecasting
    val stringList: List<String> = listOf("Daniel", "Carla", "Zilda", "Ana")
    val mixedList: List<Any> = listOf("Daniel", 24.3, "Carla", 24, "Zilda", 50, "Ana", 26)

    for(value in mixedList) {
        if(value is Int)
            println("Int: $value")
        else if (value is Double)
            println("Double: $value with Floor value ${floor(value)}")
        else if (value is String)
            println("String: $value of length ${value.length}")
        else
            println("Unknown type.")
    }

    for(value in mixedList) {
        when(value) {
            is Int -> { println("Int: $value") }
            is Double -> { println("Double: $value with Floor value ${floor(value)}") }
            is String -> { println("String: $value of length ${value.length}") }
            else -> { println("Unknown type.") }
        }
    }

    // SMART CAST
    val obj1: Any = "I have a dream."
    if(obj1 !is String)
        println("Not a String")
    else
        println("Foun a string of length ${obj1.length}") // obj is automatically cast to a String in this scope

    // Explicit (unsafe) Casting using the 'as' keyword - can go wrong
    val str1: String = obj1 as String
    println(str1.length)

    val obj2: Any = 1337
    val str2: String = obj2 as String
    println(str2)

    // Explicit (safe) Casting using the 'as?' keyword
    val obj3: Any = 1338
    val str3: String? = obj3 as? String // Works
    println(str3) // Prints null
}

// = <var> is default value
class Person (firstName: String = "John", lastName: String = "Doe") { //you can use "constructor", like: class Person constructor(...)
    // Member Variables - Properties
    var age: Int? = null
    var hobby: String = "watching Netflix" // Default values
    var firstName: String? = null
    // var firstName: String? = firstName
    var lastName: String = lastName
    var eyeColor: String? = null

    //Initializer, basically the constructor?
    init {
        this.firstName = firstName;
        println("Person created with firstname = $firstName and lastname = $lastName.")
    }

    // Secondary constructor
    constructor(firstName: String, lastName: String, age: Int):
            this(firstName, lastName) { // You have to use "this" to refer to the values in the primary constructor. It will automatically assigned the values to the parameters passed.
        this.age = age
        println("Person created with firstname = $firstName and lastname = $lastName and age = $age.")
    }

    constructor(firstName: String, lastName: String, age: Int, eyeColor: String):
            this(firstName, lastName, age) {
        this.eyeColor = eyeColor
    }

    // Member functions - Methods
    fun stateHobby() {
        println("$firstName\'s hobby is $hobby")
    }
}

// Class exercise 1
class MobilePhone constructor (osName: String, brand: String, model: String) {
    init {
        println("Created new mobile phone with the following specs:\n" +
                "osName = $osName, brand = $brand and model = $model")
    }
}

fun myFunction(a: Int) { // This is a parameter.
   //a = 5 // Cannot assign a value to a parameter within an function.
    var ab = 5 // Is variable.
    var a = a // Shadowing. You can't access the parameter _a_ anymore.
    println("a is $a") //it'll print the variable, not the parameter.
}

class Car {
    // lateinit = it'll be initialized later on.
    lateinit var owner: String
    val brand: String = "BMW"
    // Custom getter
    get() {
        return field.toLowerCase()
    }

    var maxSpeed: Int = 250
    // Everytime we declare a new property, the following code is automatically created.
    get() = field
    set(value) {
        // field = value Default code generated by Kotlin
        field = if (value > 0) value else throw IllegalArgumentException("Max speed cannot be less than 0.")
    }

    var myModel: String = "M6"
    private set

    init {
        owner = "Frank"
        myModel = "M3"
    }
}

// User class with a Primary constructor that accepts
// three parameters
class Car2(_brand: String, _model: String, _maxSpeed: Int) {
    // Properties of User class
    val brand: String = _brand         // Immutable (Read only)
    var model: String = _model  // Mutable
    var maxSpeed: Int = _maxSpeed       // Mutable
}

// Kotlin internally generates a default getter and setter for mutable properties, and a getter (only) for read-only properties.
// It calls these getters and setters internally whenever
// you access or modify a property using the dot(.) notation.
// This is how it would look like internally
class Car3(_brand: String, _model: String, _maxSpeed: Int) {
    val brand: String = _brand
        get() = field

    var model: String = _model
        get() = field
        set(value) {
            field = value
        }

    var maxSpeed: Int = _maxSpeed
        get() = field
        set(value) {
            field = value
        }
}

// value
// We use value as the name of the setter parameter. This is the default convention in Kotlin but you’re free to use any other name if you want.
// The value parameter contains the value that a property is assigned to. For example, when you write user.name = "Elon Musk",
// the value parameter contains the assigned value "Elon Musk".

// 2. Backing Field (field)
// Backing field helps you refer to the property
// inside the getter and setter methods.
// This is required because if you use the property
// directly inside the getter or setter then you’ll
// run into a recursive call which will generate
// a StackOverflowError.

// data class needs to have at least one parameter.
// cannot be abstract, open or sealed classes.
data class User(val id: Long, var name: String)

// Exercise 2
class MobilePhone2(osName: String, brand: String, model: String){
    var battery: Int = 0

    init {
        println("The phone $model from $brand uses $osName as its Operating System")
    }

    fun chargeBattery(charge: Int) {
        println("Battery: $battery")
        battery += charge
        println("Charged: $battery")
    }
}

// Needs to be open so that we can use inheritance
// All classes are 'final' by default in Kotlin, which means they're not inheritable.
// Super Class, Parent Class or Base Class
open class Vehicle {

}

interface Drivable {
    val maxSpeed: Double
    fun drive(): String
    fun brake() {
        println("The drivable is breaking.")
    }
}

// If you want to make sure a class can't be inherited, use the 'sealed' keyword instead of open.
// Sub Class, Child Class, Derived Class of Vehicle Class
// Super Class, Parent Class or Base Class of EletricMoto()
open class Moto(val name: String, val brand: String, override val maxSpeed: Double): Drivable {
    open var range: Double = 0.0 // Using 'open' also enables us to change it's functionality.
    var chargedType: String = "Type 1"

    fun extendRange(amount: Double) {
        if (amount > 0)
            range += amount
    }

    open fun drive(distance: Double) {
        println("Drove for $distance km")
    }

    override fun drive(): String = "Driving the interface drive."
}

// Sub Class, Child Class, Derived Class of Moto Class
class EletricMoto(name: String, brand: String, batteryLife: Double, maxSpeed: Double) :
    Moto(name, brand,  maxSpeed) {

    override var range = batteryLife * 5

    override fun drive(distance: Double) {
        println("Drove for $distance on electricity")
    }

    override fun drive(): String {
        return "Drove for $range on electricity"
    }

    override fun brake() {
        super.brake()
        println("brake inside of eletric motorbike.")
    }
}