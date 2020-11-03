package com.jess.zapchallenge

import android.widget.TextView
import java.util.*

fun TextView.formatValue(value: Double?) {
    this.text = "R$${String.format(Locale("pt", "BR"), "%.2f", value)}"
}