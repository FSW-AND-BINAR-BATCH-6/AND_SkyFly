package com.kom.skyfly.utils

import java.text.NumberFormat
import java.util.Locale

fun Int?.intToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getNumberInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        "IDR " + numberFormat.format(this).replace(",", ".")
    } catch (e: Exception) {
        null
    }
}

fun Int?.formatToRupiah() = this.intToCurrency("in", "ID")
