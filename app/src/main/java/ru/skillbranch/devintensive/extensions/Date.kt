package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var prefix = ""
    var postfix = ""

    var diff = date.time - this.time

    if (diff < 0){
        prefix = "через "
        diff = -diff
    } else {
        postfix = " назад"
    }

    return when(diff) {
        in 0..1* TimeUnits.SECOND.value -> "только что"
        in 1* TimeUnits.SECOND.value..45* TimeUnits.SECOND.value -> "${prefix}несколько секунд$postfix"
        in 45* TimeUnits.SECOND.value..75* TimeUnits.SECOND.value -> "${prefix}минуту$postfix"
        in 75* TimeUnits.SECOND.value..45* TimeUnits.MINUTE.value -> "$prefix${TimeUnits.MINUTE.plural(diff/ TimeUnits.MINUTE.value)}$postfix"
        in 45* TimeUnits.MINUTE.value..75* TimeUnits.MINUTE.value -> "${prefix}час$postfix"
        in 75* TimeUnits.MINUTE.value..22* TimeUnits.HOUR.value -> "$prefix${TimeUnits.HOUR.plural(diff/ TimeUnits.HOUR.value)}$postfix"
        in 22* TimeUnits.HOUR.value..26* TimeUnits.HOUR.value -> "${prefix}день$postfix"
        in 26* TimeUnits.HOUR.value..360* TimeUnits.DAY.value -> "$prefix${TimeUnits.DAY.plural(diff/ TimeUnits.DAY.value)}$postfix"
        else -> if(date.time - this.time < 0) "более чем через год" else "более года назад"
    }
}

enum class TimeUnits(val value: Long, private val ONE: String, private val FEW: String, private val MANY: String) {

    SECOND(1000L,"секунду", "секунды", "секунд"),
    MINUTE(1000L*60L, "минуту", "минуты", "минут"),
    HOUR(1000L*60L*60L, "час", "часа", "часов"),
    DAY(1000L*60L*60L*24L, "день", "дня", "дней");

    fun plural(num: Long) : String {
        return "$num ${this.getAmount(num)}"
    }

    private fun getAmount(num: Long) : String {
        return when{
            num in 5..20L -> MANY
            num%10  == 1L  -> ONE
            num%10 in 2..4L  -> FEW
            else -> MANY
        }
    }
}

fun Date.isSameDay(date: Date) : Boolean {
    return this.time/ TimeUnits.DAY.value == date.time/ TimeUnits.DAY.value
}

fun Date.shortFormat(): String? {
    val pattern = if (this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}