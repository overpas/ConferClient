package by.overpass.conferclient.ui.register.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.RegistrationStatus
import by.overpass.conferclient.data.dto.UserRegistration
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.text
import by.overpass.conferclient.util.vm
import by.overpass.conferclient.viewmodel.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private val viewModel: RegistrationViewModel by vm(RegistrationViewModel.Factory::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener {
            onRegisterClicked()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    private fun onRegisterClicked() {
        if (!isUsernameValid()) {
            etUsername.error = getString(R.string.bad_username)
            etUsername.requestFocus()
            return
        }
        if (!isEmailValid()) {
            etEmail.error = getString(R.string.bad_email)
            etEmail.requestFocus()
            return
        }
        if (!isPasswordValid()) {
            etPassword.error = getString(R.string.bad_password)
            etPassword.requestFocus()
            return
        }
        if (!doPasswordsMatch()) {
            etConfirmPassword.error = getString(R.string.passwords_dont_match)
            etConfirmPassword.requestFocus()
            return
        }
        viewModel.register(gatherInfo()).observe(this, Observer {
            it?.run {
                onRegistrationStatusReceived(this)
            }
        })
    }

    private fun gatherInfo() = UserRegistration(
        etUsername.text(),
        etFullName.text(),
        etPassword.text(),
        etConfirmPassword.text(),
        etEmail.text()
    )

    private fun onRegistrationStatusReceived(registrationStatus: RegistrationStatus) {
        when (registrationStatus) {
            is RegistrationStatus.Error -> {
                setLoading(false)
                shortToast(registrationStatus.message)
            }
            is RegistrationStatus.Success -> {
                setLoading(false)
                shortToast(getString(R.string.registration_success))
                activity?.finish()
            }
            else -> {
                setLoading(true)
            }
        }
    }

    private fun isUsernameValid(): Boolean {
        val pattern = Pattern.compile("[A-Za-z0-9_]+")
        return pattern.matcher(etUsername.text()).matches()
    }

    private fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(etEmail.text()).matches()
    }

    private fun isPasswordValid(): Boolean {
        val pattern = Pattern.compile("[A-Za-z0-9_]+")
        return pattern.matcher(etPassword.text()).matches()
    }

    private fun doPasswordsMatch(): Boolean {
        return etPassword.text() == etConfirmPassword.text()
    }

    private fun setLoading(loading: Boolean) {
        pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
        etUsername.isFocusable = !loading
        etUsername.isFocusableInTouchMode = !loading
        etFullName.isFocusable = !loading
        etFullName.isFocusableInTouchMode = !loading
        etPassword.isFocusable = !loading
        etPassword.isFocusableInTouchMode = !loading
        etConfirmPassword.isFocusable = !loading
        etConfirmPassword.isFocusableInTouchMode = !loading
        etEmail.isFocusable = !loading
        etEmail.isFocusableInTouchMode = !loading
    }

    companion object {
        val TAG = RegisterFragment::class.java.simpleName

        fun newInstance() = RegisterFragment()
    }

}