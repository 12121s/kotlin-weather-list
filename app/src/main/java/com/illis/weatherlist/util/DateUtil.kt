package com.illis.weatherlist.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val localDateFormat = SimpleDateFormat("E d MMM", Locale.ENGLISH)
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

    fun convertPrintDateString(date: Date = Date()): String = localDateFormat.format(date)
    fun convertPrintStringDate(date: String): Date = serverDateFormat.parse(date) as Date

    fun convertStringToDateString(date: String): String = convertPrintDateString(convertPrintStringDate(date))
}