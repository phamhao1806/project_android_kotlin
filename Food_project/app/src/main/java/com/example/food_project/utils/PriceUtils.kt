package com.example.food_project.utils

import java.text.NumberFormat
import java.util.Locale

object PriceUtils {

    fun cleanPriceString(priceStr: String?): String? {
        return priceStr
            ?.replace(".", "")
            ?.replace(",", "")
            ?.replace("đ", "", ignoreCase = true)
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
    }

    fun parsePriceToDouble(priceStr: String?): Double? {
        val cleaned = cleanPriceString(priceStr)
        return cleaned?.toDoubleOrNull()
    }

    fun formatPriceVND(price: Double): String {
        val localeVN = Locale("vi", "VN")
        val formatter = NumberFormat.getCurrencyInstance(localeVN)
        return formatter.format(price)
    }
}
