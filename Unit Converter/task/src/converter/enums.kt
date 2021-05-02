package converter

enum class MeasureUnit(
    val type: MeasureType,
    val names: Array<String>,
    val proportion: Double
) {
    BAD_UNIT(
        type = MeasureType.NULL,
        names = arrayOf("???"),
        proportion = Double.NaN
    ),
    METER(
        type = MeasureType.DISTANCE,
        names = arrayOf("meter", "m", "meters"),
        proportion = 1.0
    ),
    KILOMETER(
        type = MeasureType.DISTANCE,
        names = arrayOf("kilometer", "km", "kilometers"),
        proportion = 1_000.0
    ),
    CENTIMETER(
        type = MeasureType.DISTANCE,
        names = arrayOf("centimeter", "cm", "centimeters"),
        proportion = 0.01
    ),
    MILLIMETER(
        type = MeasureType.DISTANCE,
        names = arrayOf("millimeter", "mm", "millimeters"),
        proportion = 0.001
    ),
    MILE(
        type = MeasureType.DISTANCE,
        names = arrayOf("mile", "mi", "miles"),
        proportion = 1609.35
    ),
    YARD(
        type = MeasureType.DISTANCE,
        names = arrayOf("yard", "yd", "yards"),
        proportion = 0.9144
    ),
    FOOT(
        type = MeasureType.DISTANCE,
        names = arrayOf("foot", "ft", "feet"),
        proportion = 0.3048),
    INCH(
        type = MeasureType.DISTANCE,
        names = arrayOf("inch", "in", "inches"),
        proportion = 0.0254
    ),
    GRAM(
        type = MeasureType.WEIGHT,
        names = arrayOf("gram", "g", "grams"),
        proportion = 1.0
    ),
    KILOGRAM(
        type = MeasureType.WEIGHT,
        names = arrayOf("kilogram", "kg", "kilograms"),
        proportion = 1_000.0
    ),
    MILLIGRAM(
        type = MeasureType.WEIGHT,
        names = arrayOf("milligram", "mg", "milligrams"),
        proportion = 0.001
    ),
    POUND(
        type = MeasureType.WEIGHT,
        names = arrayOf("pound", "lb", "pounds"),
        proportion = 453.592
    ),
    OUNCE(
        type = MeasureType.WEIGHT,
        names = arrayOf("ounce", "oz", "ounces"),
        proportion = 28.3495
    ),
    CELSIUS(
        type = MeasureType.TEMPERATURE,
        names = arrayOf("degree Celsius", "c", "dc", "celsius","degrees Celsius"),
        proportion = Double.NaN
    ),
    KELVIN(
        type = MeasureType.TEMPERATURE,
        names = arrayOf("kelvin", "k", "kelvins"),
        proportion = Double.NaN
    ),
    FAHRENHEIT(
        type = MeasureType.TEMPERATURE,
        names = arrayOf("degree Fahrenheit", "f", "df", "fahrenheit","degrees Fahrenheit"),
        proportion = Double.NaN
    );

    enum class MeasureType {
        NULL, DISTANCE, WEIGHT, TEMPERATURE
    }

    companion object {
        fun whatUnit(s: String): MeasureUnit {
            for (unit in values())
                for (name in unit.names)
                    if (s.equals(name, ignoreCase = true))
                        return unit
            return BAD_UNIT
        }

        fun convert(value: Double, source: MeasureUnit, destination: MeasureUnit) {
            val sourceName = if (value == 1.0) source.names.first() else source.names.last()
            var convertedValue = 0.0
            var destinationName = ""
            if (source.type != MeasureType.TEMPERATURE) {
                convertedValue = value * source.proportion / destination.proportion
                destinationName = if (convertedValue == 1.0) destination.names.first() else destination.names.last()
            } else {
                convertedValue =  when {
                    source == CELSIUS && destination == FAHRENHEIT -> value * 9.0 / 5.0 + 32
                    source == CELSIUS && destination == KELVIN -> value + 273.15
                    source == FAHRENHEIT && destination == CELSIUS -> 5.0 / 9.0 * (value - 32)
                    source == FAHRENHEIT && destination == KELVIN -> (value + 459.67) * 5.0 / 9.0
                    source == KELVIN && destination == CELSIUS -> value - 273.15
                    source == KELVIN && destination == FAHRENHEIT -> value * 9.0 / 5.0 - 459.67
                    else -> value
                }
                destinationName = if (convertedValue == 1.0) destination.names.first() else destination.names.last()
            }
            println("$value $sourceName is $convertedValue $destinationName")
        }
    }
}