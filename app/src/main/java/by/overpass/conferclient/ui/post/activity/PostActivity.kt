package by.overpass.conferclient.ui.post.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import by.overpass.conferclient.ui.post.fragment.PostFragment
import by.overpass.conferclient.util.replaceFragment
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.activity.BaseAuthActivity

class PostActivity : BaseAuthActivity(), PostFragment.PostOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        val postId = intent.getLongExtra(POST_ID_KEY, -1)
        if (savedInstanceState == null && postId != -1L) {
            replaceFragment(
                PostFragment.newInstance(postId),
                R.id.flPostFragmentContainer,
                false
            )
        }
    }

    override fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun forwardNewPostAttempt() {
        attemptNewPost()
    }

    companion object {
        private const val POST_ID_KEY = "POST_ID_KEY"

        fun startPostActivity(context: Context, postId: Long) {
            val intent = Intent(context, PostActivity::class.java).apply {
                putExtra(POST_ID_KEY, postId)
            }
            context.startActivity(intent)
        }
    }

}
