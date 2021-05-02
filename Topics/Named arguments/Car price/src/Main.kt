fun main(args: Array<String>) {
    // write your code here
    val parameter = readLine()!!
    val value = readLine()!!.toInt()

    carPrice(parameter, value)

}

fun carPrice(parameter: String, value: Int) {
    val price = when (parameter) {
        "old" -> calculatePrice(old = value)
        "passed" -> calculatePrice(kilometers = value)
        "speed" -> calculatePrice(maximumSpeed = value)
        "auto" -> calculatePrice(haveAMT = value)
        else -> calculatePrice()
    }

    println(price)
}

fun calculatePrice(old: Int = 5, kilometers: Int = 100_000, maximumSpeed: Int = 120, haveAMT: Int = 0): Int {
    var price = 20_000
    price -= old * 2000
    price += (maximumSpeed - 120) * 100
    price -= kilometers / 10_000 * 200
    price += haveAMT * 1500
    return price
}