package by.overpass.conferclient.ui.list.fragment.about

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import by.overpass.conferclient.BuildConfig
import by.overpass.conferclient.R

class AboutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setTitle("About")
            .setMessage("${getString(R.string.app_name)} ${BuildConfig.VERSION_NAME}" +
                    "\n${getString(R.string.made_by)}")
            .create()
    }

    companion object {
        val TAG = AboutDialogFragment::class.java.simpleName

        fun newInstance() = AboutDialogFragment()
    }

}