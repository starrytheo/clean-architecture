package net.starry.cleanarch.app.utils

import java.text.SimpleDateFormat
import java.util.*

val formatYYYYMMDD_HHMMSS = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())

val formatYYYYMMDD = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
val formatE = SimpleDateFormat("E", Locale.getDefault())
val formatHHMMSS = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
val formatMMSS = SimpleDateFormat("mm:ss", Locale.getDefault())

fun Date.convertDate(): String = formatYYYYMMDD.format(this)
fun Date.convertWeek(): String = formatE.format(this)
fun Date.convertTime(): String = formatHHMMSS.format(this)