package by.overpass.conferclient.ui.base.activity

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import by.overpass.conferclient.ui.list.fragment.create.NewPostDialogFragment
import by.overpass.conferclient.ui.list.fragment.login.LoginDialogFragment
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.vm
import by.overpass.conferclient.viewmodel.list.ListViewModel

abstract class BaseAuthActivity : AppCompatActivity(),
    LoginDialogFragment.OnLoggedInListener, NewPostDialogFragment.NewPostDialogCreator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewModelReady()
    }

    @CallSuper
    protected open fun onViewModelReady() {
    }

    protected open fun isLoggedIn() = Preferences.tokenExists()

    protected open fun showLoginDialog(shouldOfferToCreateNewPost: Boolean) {
        LoginDialogFragment.newInstance(shouldOfferToCreateNewPost)
            .show(supportFragmentManager, LoginDialogFragment.TAG)
    }

    protected open fun showNewPostDialog() {
        NewPostDialogFragment.newInstance().show(supportFragmentManager, NewPostDialogFragment.TAG)
    }

    protected open fun attemptNewPost() {
        if (!isLoggedIn()) {
            showLoginDialog(true)
        } else {
            showNewPostDialog()
        }
    }

    override fun onLoggedIn(shouldOfferToCreateNewPost: Boolean) {
        if (shouldOfferToCreateNewPost) {
            offerToCreateNewPost()
        }
    }

    override fun offerToCreateNewPost() {
        showNewPostDialog()
    }

}