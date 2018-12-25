package by.overpass.conferclient.ui.base.activity

import android.support.v7.app.AppCompatActivity
import by.overpass.conferclient.ui.list.fragment.create.NewPostDialogFragment
import by.overpass.conferclient.ui.list.fragment.login.LoginDialogFragment
import by.overpass.conferclient.util.Preferences

abstract class BaseAuthActivity : AppCompatActivity(),
    LoginDialogFragment.OnLoggedInListener, NewPostDialogFragment.NewPostDialogCreator {

    protected open fun isLoggedIn() = Preferences.tokenExists()

    protected open fun showLoginDialog(shouldOfferToCreateNewPost: Boolean, postId: Long = 0) {
        LoginDialogFragment.newInstance(shouldOfferToCreateNewPost, postId)
            .show(supportFragmentManager, LoginDialogFragment.TAG)
    }

    protected open fun showNewPostDialog(postId: Long) {
        NewPostDialogFragment.newInstance(postId)
            .show(supportFragmentManager, NewPostDialogFragment.TAG)
    }

    protected open fun attemptNewPost(postId: Long = 0) {
        if (!isLoggedIn()) {
            showLoginDialog(true, postId)
        } else {
            showNewPostDialog(postId)
        }
    }

    override fun onLoggedIn(shouldOfferToCreateNewPost: Boolean, postId: Long) {
        if (shouldOfferToCreateNewPost) {
            offerToCreateNewPost(postId)
        }
    }

    override fun offerToCreateNewPost(postId: Long) {
        showNewPostDialog(postId)
    }

}