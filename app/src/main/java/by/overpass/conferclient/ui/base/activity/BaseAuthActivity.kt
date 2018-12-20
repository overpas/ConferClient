package by.overpass.conferclient.ui.base.activity

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import by.overpass.conferclient.ui.list.fragment.create.NewPostDialogFragment
import by.overpass.conferclient.ui.list.fragment.login.LoginDialogFragment
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.viewmodel.list.ListViewModel

abstract class BaseAuthActivity : AppCompatActivity(),
    LoginDialogFragment.OnLoggedInListener, NewPostDialogFragment.NewPostDialogCreator {

    protected lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVm(this, ListViewModel::class.java, ListViewModel.Factory::class.java)
        onViewModelReady()
    }

    @CallSuper
    protected open fun onViewModelReady() {
    }

    protected fun isLoggedIn() = Preferences.tokenExists()

    protected fun showLoginDialog(shouldOfferToCreateNewPost: Boolean) {
        LoginDialogFragment.newInstance(shouldOfferToCreateNewPost)
            .show(supportFragmentManager, LoginDialogFragment.TAG)
    }

    override fun onLoggedIn(shouldOfferToCreateNewPost: Boolean) {
        if (shouldOfferToCreateNewPost) {
            offerToCreateNewPost()
        }
    }

}