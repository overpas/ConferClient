package by.overpass.conferclient.ui.post.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import by.overpass.conferclient.ui.post.fragment.PostFragment
import by.overpass.conferclient.util.replaceFragment
import by.overpass.conferclient.R

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val postId = intent.getLongExtra(POST_ID_KEY, -1)
        if (savedInstanceState == null && postId != -1L) {
            replaceFragment(
                PostFragment.newInstance(postId),
                R.id.flPostFragmentContainer,
                false
            )
        }
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
