package by.overpass.conferclient.util

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import by.overpass.conferclient.ConferApp

fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes resId: Int, addToBackStack: Boolean) {
    supportFragmentManager.transact {
        replace(resId, fragment).takeIf { addToBackStack }?.addToBackStack(null)
    }
}

inline fun FragmentManager.transact(transactionFunction: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.transactionFunction()
    transaction.commit()
}

fun Fragment.shortToast(message: String) {
    Toast.makeText(context ?: ConferApp.getAppContext(), message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.shortToast(@StringRes stringRes: Int) {
    Toast.makeText(context ?: ConferApp.getAppContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.shortToast(@StringRes stringRes: Int) {
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
}