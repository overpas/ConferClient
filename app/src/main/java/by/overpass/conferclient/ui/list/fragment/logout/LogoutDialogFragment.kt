package by.overpass.conferclient.ui.list.fragment.logout

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import by.overpass.conferclient.R
import by.overpass.conferclient.util.Preferences

class LogoutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (!Preferences.tokenExists()) {
            AlertDialog.Builder(context!!)
                .setTitle(R.string.already_logged_out)
                .setNeutralButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                    dismiss()
                }
                .create()
        } else {
            AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.logout))
                .setIcon(R.drawable.ic_logout)
                .setMessage(getString(R.string.want_to_logout))
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    Preferences.deleteToken()
                    dismiss()
                }
                .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                    dismiss()
                }
                .create()
        }
    }

    companion object {
        val TAG = LogoutDialogFragment::class.java.simpleName

        fun newInstance() = LogoutDialogFragment()
    }

}