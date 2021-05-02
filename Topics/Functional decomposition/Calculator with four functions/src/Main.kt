// Implement your functions here
fun subtractTwoNumbers(a: Long, b: Long) = println(a - b)


fun sumTwoNumbers(a: Long, b: Long) = println(a + b)


fun divideTwoNumbers(a: Long, b: Long) = when(b) {
    0L -> println("Division by 0!")
    else -> println(a / b)
}


fun multiplyTwoNumbers(a: Long, b: Long) = println(a * b)