package by.overpass.conferclient.ui.post.fragment

import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.overpass.conferclient.ConferApp
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.formatPostDate
import by.overpass.conferclient.util.parentVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.post.PostingViewModel
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.item_post_tree.view.*
import java.util.*

class PostFragment : Fragment() {

    private val random = Random()
    private var postOwner: PostOwner? = null

    private lateinit var simplePostTreeBackground: Drawable

    private val viewModel: PostingViewModel by parentVm(PostingViewModel.Factory::class.java)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PostOwner) {
            postOwner = context
        } else {
            throw RuntimeException("$context must implement ${PostOwner::class.java.simpleName}")
        }
    }

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
        setupSwipeToRefresh()
        subscribeToUpdates()
        fetchData()
    }

    private fun setupSwipeToRefresh() {
        srlRefresh.setOnRefreshListener {
            fetchData()
            srlRefresh.postDelayed({
                srlRefresh.isRefreshing = false
            }, REFRESH_PERIOD_MS)
        }
    }

    private fun subscribeToUpdates() {
        viewModel.updates.observe(this, Observer { updated ->
            updated
                ?.takeIf { it }
                ?.run { fetchData() }
        })
    }

    private fun fetchData() {
        val postId = arguments?.getLong(POST_ID_KEY) ?: 0L
        if (postId != 0L) {
            viewModel.getProgress().observe(this, Observer {
                it?.run {
                    onStatusChanged(this)
                }
            })
            viewModel.getPostTreeById(postId).observe(this, Observer {
                it?.run {
                    onPostTreeReceived(this)
                }
            })
        }
    }

    override fun onDetach() {
        super.onDetach()
        postOwner = null
    }

    private fun onStatusChanged(status: Status) {
        when (status) {
            is Status.Error -> {
                setLoading(false)
                shortToast(status.message)
            }
            is Status.Success -> {
                setLoading(false)
            }
            else -> {
                setLoading(true)
            }
        }
    }

    private fun onPostTreeReceived(postTree: PostTree) {
        llPostTreeContainer.removeAllViews()
        postOwner?.setActionBarTitle(postTree.title)
        addPostTreeView(postTree, 0, llPostTreeContainer.id)
    }

    private fun addPostTreeView(postTree: PostTree, replyLevel: Int, parentViewId: Int) {
        val postTreeView: View
        if (replyLevel == 0) {
            postTreeView = LayoutInflater.from(context).run {
                inflate(R.layout.item_first_post_tree, llPostTreeContainer, false)
            }
            simplePostTreeBackground = postTreeView.background
            postTreeView.findViewById<TextView>(R.id.tvOp).setOnClickListener {
                postOwner?.replyToPost(postTree.id)
            }
        } else {
            postTreeView = LayoutInflater.from(context).run {
                inflate(R.layout.item_post_tree, llPostTreeContainer, false)
            }
            postTreeView.tvTitle.text = postTree.title
            postTreeView.ivReplyImage.setOnClickListener {
                val parentPostView = llPostTreeContainer.findViewById<CardView>(parentViewId)
                highlightParent(parentPostView)
                svPostParent.scrollTo(0, parentPostView.top)
            }
            postTreeView.ivReply.setOnClickListener {
                postOwner?.replyToPost(postTree.id)
            }
        }
        postTreeView.tvBody.text = postTree.body
        postTreeView.tvFullName.text = postTree.authorFullName
        postTreeView.tvUsername.text = postTree.authorUsername
        postTreeView.tvDate.text = formatPostDate(Date(postTree.date))
        val layoutParams = postTreeView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = layoutParams.marginStart + LEVEL_COEFFICIENT * replyLevel
        layoutParams.marginEnd = resources.getDimension(R.dimen.margin_m).toInt()
        postTreeView.layoutParams = layoutParams
        postTreeView.id = random.nextInt() and Integer.MAX_VALUE
        llPostTreeContainer.addView(postTreeView)
        if (postTree.replies != null) {
            postTree.replies!!.forEach {
                addPostTreeView(it, replyLevel + 1, postTreeView.id)
            }
        }
    }

    private fun highlightParent(parentPostView: CardView) {
        parentPostView.post {
            parentPostView.background = resources.getDrawable(
                R.drawable.bg_post_highlighted,
                ConferApp.getAppContext().theme
            )
            parentPostView.postDelayed({
                parentPostView.background = simplePostTreeBackground
            }, HIGHLIGHT_PERIOD_MS)
        }

    }

    private fun setLoading(loading: Boolean) {
        pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
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

    interface PostOwner {
        fun setActionBarTitle(title: String)
        fun replyToPost(postId: Long)
    }

    companion object {
        val TAG = PostFragment::class.java.simpleName
        private const val POST_ID_KEY = "POST_ID_KEY"
        private const val LEVEL_COEFFICIENT = 40
        private const val HIGHLIGHT_PERIOD_MS = 2000L
        private const val REFRESH_PERIOD_MS = 2000L

        fun newInstance(postId: Long) = Bundle()
            .apply { putLong(POST_ID_KEY, postId) }
            .let {
                PostFragment().apply {
                    arguments = it
                }
            }
    }

}