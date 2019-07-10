package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String {
    val result = this.trim()
    return if (result.length <= len) result else "${result.take(len).trimEnd()}..."
}

fun String.stripHtml(): String {
    return this
        .replace("""<.*?>""".toRegex(), "")
        .replace("""&(#\d+?|\w+?);""".toRegex(), "")
        .split(""" +""".toRegex()).joinToString(" ")
}