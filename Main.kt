/* 
kotlinc Main.kt -include-runtime -d Main.jar
java -jar Main.jar
*/

import java.util.Scanner

fun main() {
    val reader = Scanner(System.`in`)

    println("Welcome to SimpleCalc!")
    print("Please enter a math problem: ")

    val userString: String = reader.nextLine()
    val validChars = setOf('+', '-', '*', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    println("You entered: $userString")

    // Strip any space chars
    var problem: String = userString.replace(" ", "")

    // Validate characters in input end program if equation is invalid
    for (char in problem) {
        if (char !in validChars) {
            println("$char is an invalid character")
            return
        }
    }

    var finished = false

    // Start the calculation loop
    while (!finished) {
        var index = 0

        // PEMDAS * and / operators first 
        while (index < problem.length) {
            if (problem[index] == '*' || problem[index] == '/') {
                val operator = problem[index]

                // Extract left operand
                val leftOperandEnd = index
                val leftOperandStart = problem.substring(0, leftOperandEnd).lastIndexOfAny("+-*/".toCharArray()) + 1
                val leftOperand = problem.substring(leftOperandStart, leftOperandEnd).toIntOrNull() ?: 0

                // Extract right operand
                val rightOperandStart = index + 1
                val rightOperandEnd = problem.substring(rightOperandStart).indexOfAny("+-*/".toCharArray()).let {
                    if (it == -1) problem.length else rightOperandStart + it
                }
                val rightOperand = problem.substring(rightOperandStart, rightOperandEnd).toIntOrNull() ?: 0

                // Perform the operation
                val result = when (operator) {
                    '*' -> mult(leftOperand, rightOperand)
                    '/' -> div(leftOperand, rightOperand)
                    else -> 0
                }

                // Update the equation
                problem = problem.substring(0, leftOperandStart) +
                          if (result < 0) "($result)" else result.toString() +
                          problem.substring(rightOperandEnd)
                index = 0 // Restart from the beginning
            } else {
                index++
            }
        }

        // PEMDAS + and - operators next
        index = 0
        while (index < problem.length) {
            // Check if the current character is '+' or '-' and ensure it is a valid operator
            if (problem[index] == '+' || problem[index] == '-') {
                val operator = problem[index]

                // Extract left operand
                val leftOperandEnd = index
                val leftOperandStart = problem.substring(0, leftOperandEnd).lastIndexOfAny("+-*/".toCharArray()) + 1
                val leftOperand = problem.substring(leftOperandStart, leftOperandEnd).toIntOrNull() ?: 0

                // Extract right operand
                val rightOperandStart = index + 1
                val rightOperandEnd = problem.substring(rightOperandStart).indexOfAny("+-*/".toCharArray()).let {
                    if (it == -1) problem.length else rightOperandStart + it
                }
                val rightOperand = problem.substring(rightOperandStart, rightOperandEnd).toIntOrNull() ?: 0

                // Perform the operation
                val result = when (operator) {
                    '+' -> add(leftOperand, rightOperand)
                    '-' -> sub(leftOperand, rightOperand)
                    else -> 0
                }

                // Update the equation
                problem = problem.substring(0, leftOperandStart) +
                          if (result < 0) "($result)" else result.toString() +
                          problem.substring(rightOperandEnd)
                index = 0 // Restart from the beginning
            } else {
                index++
            }
        }

        // Check if there are no more operators to process
        finished = !problem.contains(Regex("[+\\-*/]"))
    }

    println("The result is: $problem")
}

// Arithmetic functions
fun add(a: Int, b: Int): Int = a + b
fun sub(a: Int, b: Int): Int = a - b
fun mult(a: Int, b: Int): Int = a * b
fun div(a: Int, b: Int): Int {
    if (b == 0) {
        println("Division by zero error.")
        return 0
    }
    return a / b
}
