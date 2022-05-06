package net.starry.cleanarch.presentation.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun Activity.setStatusBarTransparent() {
    window.apply {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.navigationHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisibleOrGone(value: Boolean) {
    if (value) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}


fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)?.show()
}

fun Activity.toast(@StringRes id: Int) {
    Toast.makeText(this, resources.getString(id), Toast.LENGTH_SHORT)?.show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)?.show()
}

fun Fragment.toast(stringRes: Int) {
    Toast.makeText(requireContext(), resources.getString(stringRes), Toast.LENGTH_SHORT)?.show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)?.show()
}

fun Context.toast(stringRes: Int) {
    Toast.makeText(this, resources.getString(stringRes), Toast.LENGTH_SHORT)?.show()
}

fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)

fun Fragment.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(requireActivity(), id)


fun Activity.getStringRes(@StringRes id: Int) = resources.getString(id)

fun Fragment.getStringRes(@StringRes id: Int) = resources.getString(id)

fun GradientDrawable.setCornerRadiusX(
    topLeft: Float = 0F,
    topRight: Float = 0F,
    bottomRight: Float = 0F,
    bottomLeft: Float = 0F
) {
    cornerRadii = arrayOf(
        topLeft, topLeft,
        topRight, topRight,
        bottomRight, bottomRight,
        bottomLeft, bottomLeft
    ).toFloatArray()
}

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

var isClicked = false

fun View.onThrottleFirstClick(interval: Long = 150L, action: (v: View) -> Unit) {
    setOnClickListener { v ->
        if (isClicked.not()) {
            isClicked = true
            v?.run {
                postDelayed({
                    isClicked = false
                }, interval)
                action(v)
            }
        }
    }
}


internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) =
    ContextCompat.getDrawable(this, drawable)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

inline fun <reified T : Any> T.ordinal(): Int {
    if (T::class.isSealed) {
        return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
    }

    val klass = if (T::class.isCompanion) {
        javaClass.declaringClass
    } else {
        javaClass
    }

    return klass.superclass?.classes?.indexOfFirst { it == klass } ?: -1
}


