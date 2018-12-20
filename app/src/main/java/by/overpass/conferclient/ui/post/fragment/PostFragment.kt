package by.overpass.conferclient.ui.post.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.formatPostDate
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.viewmodel.post.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.item_post_tree.view.*
import java.util.*

class PostFragment : Fragment() {

    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getVm(this, PostViewModel::class.java, PostViewModel.Factory::class.java)
        val postId = arguments?.getLong(POST_ID_KEY, -1)
        if (postId != null && postId != -1L) {
            viewModel.getPostTreeById(postId).observe(this, Observer {
                it?.run {
                    onPostTreeReceived(this)
                }
            })
        }
    }

    private fun onPostTreeReceived(postTree: PostTree) {
        addPostTreeView(postTree, 0, llPostTreeContainer.top)
    }

    private fun addPostTreeView(postTree: PostTree, replyLevel: Int, parentTop: Int) {
        val postTreeView = LayoutInflater.from(context).run {
            inflate(R.layout.item_post_tree, llPostTreeContainer, false)
        }
        if (replyLevel == 0) {
            postTreeView.ivReplyImage.visibility = View.GONE
        } else {
            postTreeView.ivReplyImage.setOnClickListener {
                svPostParent.scrollTo(0, parentTop)
            }
        }
        postTreeView.tvTitle.text = postTree.title
        postTreeView.tvBody.text = postTree.body
        postTreeView.tvFullName.text = postTree.authorFullName
        postTreeView.tvUsername.text = postTree.authorUsername
        postTreeView.tvDate.text = formatPostDate(Date(postTree.date))
        val layoutParams = postTreeView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = layoutParams.marginStart + RELATIVE_LEVEL_MARGIN * replyLevel
        postTreeView.layoutParams = layoutParams
        llPostTreeContainer.addView(postTreeView)
        if (postTree.replies != null) {
            postTree.replies!!.forEach {
                addPostTreeView(it, replyLevel + 1, postTreeView.top)
            }
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

    companion object {
        val TAG = PostFragment::class.java.simpleName
        private const val POST_ID_KEY = "POST_ID_KEY"
        private const val RELATIVE_LEVEL_MARGIN = 40

        fun newInstance(postId: Long) = Bundle()
                .apply { putLong(POST_ID_KEY, postId) }
                .run {
                    PostFragment().also { it.arguments = this }
                }
    }

}