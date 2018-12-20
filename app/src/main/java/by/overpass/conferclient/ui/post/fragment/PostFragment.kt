package by.overpass.conferclient.ui.post.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.data.network.dto.PostTree
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.viewmodel.post.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*

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
        tvStubPostTree.text = postTree.toString()
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

        fun newInstance(postId: Long) = Bundle()
            .apply { putLong(POST_ID_KEY, postId) }
            .run {
                PostFragment().also { it.arguments = this }
            }
    }

}