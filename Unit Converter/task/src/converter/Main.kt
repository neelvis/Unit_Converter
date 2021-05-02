package converter

import java.lang.Exception

fun main() {
    do {
        print("Enter what you want to convert (or exit): ")

        val userInput = readLine()!!.split(" ")
        val size = userInput.size

        if (userInput[0] == "exit") break

        val value = try {
            userInput[0].toDouble()
        }
        catch(e: Exception) {
            println("Parse error\n")
            continue
        }

        /* parsing:
        userInput[0] represents value
        userInput[1] represents source conversion unit or "degree" word
        if userInput[1] == "degree" then userInput[2] represents source conversion unit
        destination unit is parsed same way (using 2 last elements of userInput
        TODO:  "^(?<number>[-\\d.]+) (?<source>[\\w ]+?) (?:\\w+) (?<target>(degrees? )?[\\w]+)$"
         */
        val parsing = parseInput(
            value,
            source = userInput[1].toLowerCase() + if (userInput[1].toLowerCase().contains("degree")) " ${userInput[2].toLowerCase()}" else "",
            destination = if (userInput[size - 2].toLowerCase().contains("degree")) "${userInput[size - 2]} ${userInput[size - 1]}".toLowerCase() else userInput[size - 1].toLowerCase()
        )

        if (!parsing.validationResult) {
            println(parsing.validationMessage)
            continue
        }

        MeasureUnit.convert(value, parsing.source, parsing.destination)
    } while (true)
}

data class ValidationObject(val validationResult: Boolean, val validationMessage: String, var source: MeasureUnit = MeasureUnit.BAD_UNIT, var destination: MeasureUnit = MeasureUnit.BAD_UNIT)

fun parseInput(value: Double, source: String, destination: String): ValidationObject {
    val sourceMeasureUnit = MeasureUnit.whatUnit(source)
    val destMeasureUnit = MeasureUnit.whatUnit(destination)

    if (sourceMeasureUnit.type == MeasureUnit.MeasureType.NULL)
        return if (destMeasureUnit.type == MeasureUnit.MeasureType.NULL)
            ValidationObject(
                validationResult = false,
                validationMessage = "Conversion from ??? to ??? is impossible\n"
            ) else ValidationObject(
                validationResult = false,
                validationMessage = "Conversion from ??? to ${destMeasureUnit.names.last()} is impossible\n"
            )

    if (destMeasureUnit.type == MeasureUnit.MeasureType.NULL) return ValidationObject(
        validationResult = false,
        validationMessage = "Conversion from ${sourceMeasureUnit.names.last()} to ??? is impossible\n"
        )

    if (sourceMeasureUnit.type != destMeasureUnit.type)
        return ValidationObject(
            validationResult = false,
            validationMessage = "Conversion from ${sourceMeasureUnit.names.last()} to ${destMeasureUnit.names.last()} is impossible\n"
        )

    if (sourceMeasureUnit.type == MeasureUnit.MeasureType.DISTANCE && value < 0)
        return ValidationObject(
            validationResult = false,
            validationMessage = "Length shouldn't be negative"
        )

    if (sourceMeasureUnit.type == MeasureUnit.MeasureType.WEIGHT && value < 0)
        return ValidationObject(
            validationResult = false,
            validationMessage = "Weight shouldn't be negative"
        )

    return ValidationObject(
        validationResult = true,
        validationMessage = "OK",
        source = sourceMeasureUnit,
        destination = destMeasureUnit
    )
}

