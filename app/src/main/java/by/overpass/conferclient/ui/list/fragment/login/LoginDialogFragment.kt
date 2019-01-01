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
import by.overpass.conferclient.util.parentVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.text
import by.overpass.conferclient.viewmodel.post.PostingViewModel
import kotlinx.android.synthetic.main.dialog_login.*

class LoginDialogFragment : DialogFragment() {

    private var onLoggedInListener: OnLoggedInListener? = null

    private val viewModel: PostingViewModel by parentVm(PostingViewModel.Factory::class.java)

    private var shouldCreateNewPost = false
    private var postId = 0L

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLoggedInListener) {
            onLoggedInListener = context
        } else {
            throw ClassCastException(
                "$context must implement " +
                        OnLoggedInListener::class.java.simpleName
            )
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
        dismiss()
    }

    private fun onLoginClicked() {
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

    private fun setLoading(loading: Boolean) {
        pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
        ivLoginImage.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        btnCancel.isClickable = !loading
        btnLogin.isClickable = !loading
        btnNotRegistered.isClickable = !loading
        etUsername.isFocusable = !loading
        etUsername.isFocusableInTouchMode = !loading
        etPassword.isFocusable = !loading
        etPassword.isFocusableInTouchMode = !loading
    }

    private fun onAuthStatusChanged(authStatus: AuthStatus) {
        when (authStatus) {
            is AuthStatus.Error -> {
                setLoading(false)
                shortToast(authStatus.message)
                dismiss()
            }
            is AuthStatus.LoggedIn -> {
                setLoading(false)
                onLoggedInListener?.onLoggedIn(shouldCreateNewPost, postId)
                dismiss()
            }
            else -> {
                setLoading(true)
            }
        }
    }

    interface OnLoggedInListener {
        fun onLoggedIn(shouldOfferToCreateNewPost: Boolean, postId: Long = 0)
    }

    companion object {
        val TAG = LoginDialogFragment::class.java.simpleName

        fun newInstance(shouldCreateNewPost: Boolean, postId: Long): LoginDialogFragment {
            return LoginDialogFragment().apply {
                this.shouldCreateNewPost = shouldCreateNewPost
                this.postId = postId
            }
        }

    }

}