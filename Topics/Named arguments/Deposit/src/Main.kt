import kotlin.math.pow
import kotlin.reflect.full.findParameterByName

fun main() {
    // write your code here
    val name = readLine()!!
    val value = readLine()!!.toDouble()

    val function = ::calculateAmount
    val parameter = function.findParameterByName(name)
    function.callBy(mapOf(parameter!! to value))
}

fun calculateAmount(amount: Double = 1_000.0, percent: Double = 5.0, years: Double = 10.0) {
    val finalAmount = amount * (1 + percent / 100).pow(years)
    println(finalAmount.toInt())
}