package by.overpass.conferclient.ui.list.fragment.login

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.AuthStatus
import by.overpass.conferclient.ui.register.activity.RegisterActivity
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.text
import by.overpass.conferclient.viewmodel.list.ListViewModel
import kotlinx.android.synthetic.main.dialog_login.*

class LoginDialogFragment : DialogFragment() {

    private var onLoggedInListener: OnLoggedInListener? = null

    private lateinit var viewModel: ListViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLoggedInListener) {
            onLoggedInListener = context
        } else {
            throw ClassCastException("$context must implement ${OnLoggedInListener::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_login, container, false)
    }

    override fun onViewCreated(theView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(theView, savedInstanceState)
        viewModel = getVm(activity!!, ListViewModel::class.java, ListViewModel.Factory::class.java)
        btnLogin.setOnClickListener { view ->
            onLoginClicked()
        }
        btnCancel.setOnClickListener {
            onCancelClicked()
        }
        btnNotRegistered.setOnClickListener {
            onNotRegisteredClicked()
        }
    }

    override fun onDetach() {
        super.onDetach()
        onLoggedInListener = null
    }

    private fun onCancelClicked() {
        shortToast(R.string.cancel)
        dismiss()
    }

    private fun onLoginClicked() {
        setLoading()
        val username = etUsername.text()
        val password = etPassword.text()
        viewModel.login(username, password).observe(this, Observer {
            if (it != null) {
                onAuthStatusChanged(it)
            }
        })
    }

    private fun onNotRegisteredClicked() {
        startActivity(Intent(context, RegisterActivity::class.java))
    }

    private fun setLoading() {
        pbLoading.visibility = View.VISIBLE
        ivLoginImage.visibility = View.INVISIBLE
        btnCancel.isClickable = false
        btnLogin.isClickable = false
        btnNotRegistered.isClickable = false
        etUsername.isFocusable = false
        etUsername.isFocusableInTouchMode = false
        etPassword.isFocusable = false
        etPassword.isFocusableInTouchMode = false
    }

    private fun unsetLoading() {
        pbLoading.visibility = View.GONE
        ivLoginImage.visibility = View.VISIBLE
        btnCancel.isClickable = true
        btnLogin.isClickable = true
        btnNotRegistered.isClickable = true
        etUsername.isFocusable = true
        etUsername.isFocusableInTouchMode = true
        etPassword.isFocusable = true
        etPassword.isFocusableInTouchMode = true
    }

    private fun onAuthStatusChanged(authStatus: AuthStatus) {
        when (authStatus) {
            is AuthStatus.Error -> {
                unsetLoading()
                shortToast(authStatus.message)
                dismiss()
            }
            is AuthStatus.LoggedIn -> {
                unsetLoading()
                val shouldOfferToCreateNewPost =
                    arguments?.getBoolean(SHOULD_CREATE_NEW_POST_KEY, false) ?: false
                onLoggedInListener?.onLoggedIn(shouldOfferToCreateNewPost)
                dismiss()
            }
            else -> {
            }
        }
    }

    interface OnLoggedInListener {
        fun onLoggedIn(shouldOfferToCreateNewPost: Boolean)
    }

    companion object {

        val TAG = LoginDialogFragment::class.java.simpleName
        private const val SHOULD_CREATE_NEW_POST_KEY = "SHOULD_CREATE_NEW_POST_KEY"

        fun newInstance(shouldCreateNewPost: Boolean): LoginDialogFragment {
            return Bundle()
                .apply { putBoolean(SHOULD_CREATE_NEW_POST_KEY, shouldCreateNewPost) }
                .run {
                    LoginDialogFragment().also { it.arguments = this }
                }
        }

    }

}