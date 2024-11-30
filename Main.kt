// kotlinc Main.kt -include-runtime -d Main.jar 
// java -jar Main.jar

import java.util.Scanner

fun main() {
    val reader = Scanner(System.`in`)

    println("Welcome to SimpleCalc!")
    print("Please enter a math problem: ")

    var problem:String = reader.nextLine()

    println("You entered: $problem")
}