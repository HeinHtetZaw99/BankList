package com.daniel.banklist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.daniel.banklist.adapters.BaseRecyclerAdapter
import com.daniel.banklist.adapters.BaseSelectableRecyclerAdapter

import com.daniel.banklist.fragments.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daniel.banklist.BuildConfig
import com.daniel.banklist.components.ClickGuard
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

fun showLogD(text: String) {
    if (BuildConfig.DEBUG)
        Log.v("APP_TAG", text)
}

fun showLogD(customTag: String, text: String) {
    if (BuildConfig.DEBUG)
        Log.d(customTag, text)
}

fun String.removeSpaces() = this.replace("\\s".toRegex(), "")


fun <T> Class<T>.showLogE(text: String) {
    if (BuildConfig.DEBUG)
        Log.e(this.name, text)
}


fun showLogE(text: String) {
    if (BuildConfig.DEBUG)
        Log.e("APP_TAG", text)
}

fun showLogE(customTag: String, text: String) {
    if (BuildConfig.DEBUG)
        Log.e(customTag, text)
}


fun showLogE(throwable: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e("APP_TAG", throwable.localizedMessage ?: "")

}

fun showLogE(text: String, throwable: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e(
            "APP_TAG",
            "-------------------------------------\n" +
                    "$text" +
                    "@ ${throwable.stackTrace} \n" + throwable.javaClass.name + "\t\t\t:\t\t\t" + throwable.message.toString()
                    + "\n-------------------------------------"
                    + "\n------------ Stack Trace ------------" +
                    "\n ${throwable.printStackTrace()}"
        )
}

@SuppressLint("BinaryOperationInTimber")
fun <T> Class<T>.showLogE(throwable: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e(
            "APP_TAG",
            "-------------------------------------\n" +
                    "@ ${throwable.stackTrace} \n" + throwable.javaClass.name + "\t\t\t:\t\t\t" + throwable.message.toString()
                    + "\n-------------------------------------"
                    + "\n------------ Stack Trace ------------" +
                    "\n ${throwable.printStackTrace()}"
        )
}


fun View.setVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

//Extension Function for showing image
fun ImageView.show(imageUrl: String, @DrawableRes placeHolderImageRes: Int) {
    Glide.with(this)
        .load(imageUrl)
        .apply(
            RequestOptions.centerCropTransform().placeholder(placeHolderImageRes).error(
                placeHolderImageRes
            )
        )
        .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageUrl: String, placeHolderImageRes: Drawable) {
    Glide.with(this)
        .load(imageUrl)
        .apply(
            RequestOptions.fitCenterTransform().placeholder(placeHolderImageRes).error(
                placeHolderImageRes
            )
        )
        .into(this)
}


//Extension Function for showing image
fun ImageView.show(@DrawableRes imageRes: Int) {
    Glide.with(this)
        .load(imageRes)
        .apply(RequestOptions.centerInsideTransform())
        .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .centerInside()

        .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageUri: Uri) {
    Glide.with(this)
        .load(imageUri)
        .apply(RequestOptions.centerCropTransform())
        .into(this)
}


fun Array<TextInputLayout>.removeFoci() {
    this.forEach {
        it.clearFocus()
    }
}

fun View.setEnable(enabled: Boolean) {
    this.isEnabled = enabled
}

fun Array<TextInputLayout>.enable(enabled: Boolean) {
    this.forEach {
        it.isEnabled = enabled
    }
}


fun Array<out View>.setVisible(isVisible: Boolean) {

    val visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }

    this.forEach {
        it.visibility = visibility
    }
}


fun ViewGroup.inflater(): LayoutInflater {
    return LayoutInflater.from(this.context)
}

fun ComponentActivity.showShortToast(message: CharSequence) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun ComponentActivity.showShortToast(@StringRes resId: Int) {
    Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
}

fun ComponentActivity.showLongToast(message: CharSequence) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

fun ComponentActivity.showLongToast(@StringRes resId: Int) {
    Toast.makeText(applicationContext, resId, Toast.LENGTH_LONG).show()
}

fun Fragment.showShortToast(message: CharSequence) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showShortToast(@StringRes resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.showLongToast(message: CharSequence) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showLongToast(@StringRes resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
}

fun String.isNotEmptyString() = this.removeSpaces() != "" && this.removeSpaces().isNotEmpty()


fun String.isEmptyString() = this.removeSpaces().isEmpty()

fun getRandom(min: Double, max: Double): Double {
    return Math.random() * (max + 1 - min) + min
}

fun Array<out View>.addClickGuards() {
    try {
        this.forEach {
            ClickGuard.guard(it)
        }
    } catch (e: IllegalStateException) {
        showLogE(e)
    }
}

fun getDefaultDateFormatter() = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

infix fun <T> List<T>.nonintersect(
    other: List<T>
): Set<T> {
    val set = this.toHashSet()
    set.removeAll(other)
    return set
}

inline fun <reified T> T.itemToTypeDefinedArray(): Array<T> {
    return arrayOf(this)
}


inline fun <reified T> T.toTypeDefinedList(): List<T> {
    return arrayOf(this).toList()
}

@Suppress("UNCHECKED_CAST")
/**
 * for handling onNavigationListener with ktx
 * @param fragmentList -> fragment array mapped with navigationItemID
 * @param function -> method reference for handling setOnNavigationItemSelectedListener event
 * */
inline fun BottomNavigationView.handleNavigationTransactions(
    fragmentList: SparseArray<BaseFragment>,
    crossinline function: (navigationId: Int) -> Any
) {
    this.setOnNavigationItemSelectedListener {
        if (fragmentList.get(it.itemId) != null) {
            function(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        return@setOnNavigationItemSelectedListener false
    }
}


/**
 * for building and adding fragment list to fragmentContainer
 * */
fun SparseArray<BaseFragment>.buildFragmentList(
    fragmentManager: FragmentManager,
    @IdRes container: Int,
    @IdRes defaultFragmentId: Int
) {
    showLogD("List : $this")
    this.forEach { key, fragment ->
        if (fragment.isAdded) {
            showLogD("Fragment $fragment is already added")
            return@forEach //or return false/true, based on where you are calling from
        } else {
            if (key == defaultFragmentId) {
                fragmentManager.beginTransaction()
                    .add(container, fragment, key.toString())
                    .commitAllowingStateLoss()
                showLogD("Fragment $fragment is added as default")
            } else {
                fragmentManager.beginTransaction()
                    .add(container, fragment).hide(fragment)
                    .commitAllowingStateLoss()
                showLogD("Fragment $fragment is added")
            }
        }
    }
}

fun RecyclerView.configure(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.configure(context: Context, adapter: BaseSelectableRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.configureWithHorizontal(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}


fun RecyclerView.configureWithReverseLayout(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true).apply {
        stackFromEnd = true
    }
}


fun TextView.changeTextAppearance(context: Context?, resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this.setTextAppearance(context, resId)
    } else {
        this.setTextAppearance(resId)
    }
}

@SuppressLint("SimpleDateFormat")
fun getTime(): String {
    return SimpleDateFormat("HH:mm a").format(Calendar.getInstance().time)
}

fun getDp(context: Context, sizeInDp: Int): Int {
    return (sizeInDp * context.resources.displayMetrics.density + 0.5f).toInt()
}

fun <E> SparseArray<E>.getKeyByValue(value: E): Int {
    return this.keyAt(this.indexOfValue(value))
}

fun Toolbar.addBackNavButton(activity: AppCompatActivity, @DrawableRes backIcon: Int) {
    activity.setSupportActionBar(this)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity.supportActionBar?.setDisplayShowHomeEnabled(true)
    activity.supportActionBar?.title = ""
    this.navigationIcon = ContextCompat.getDrawable(activity, backIcon)
    this.setNavigationOnClickListener {
        //What to do on back clicked
        activity.onBackPressed()
    }
}

fun BottomSheetDialog.enableExitOnTouchOutside(enabled: Boolean){
    this.setCancelable(enabled)
    this.setCanceledOnTouchOutside(enabled)
}

fun String.shorten(): String {
    return if (this.length > 20)
        "${this.substring(0, 20)}...${this.substring(this.length-10, this.length)} [truncated for ${this.length} chars]"
    else
        this
}

fun String.parseNumber() : Int {
    return  try {
        this.toInt()
    }catch (nfe : NumberFormatException){
        showLogE("NumberFormatException Occurred : ${nfe.message}")
        0
    }
}

fun calculateFileSize(fileSizeInBytes: Long): String {
    showLogD("calculateFileSize(fileSizeInBytes = $fileSizeInBytes)")
    val sizeUnit = arrayOf("B", "KB", "MB", "GB")
    var sizeIndex = 0
    var current: Double = fileSizeInBytes.toDouble()
    while (current > 1024) {
        current /= 1024.0
        sizeIndex++
    }
    showLogD("calculated File Size : $current and sizeIndex is $sizeIndex")
    return "${current.toInt()} ${sizeUnit[sizeIndex]}"
}