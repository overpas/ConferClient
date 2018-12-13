package by.overpass.conferclient.ui.popular.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.fragment.PostListFragment
import by.overpass.conferclient.ui.popular.adapter.PopularPostAdapter
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.popular.PopularViewModel
import kotlinx.android.synthetic.main.fragment_popular.*
import timber.log.Timber

class PopularFragment : PostListFragment() {

    private lateinit var viewModel: PopularViewModel
    private lateinit var adapter: PopularPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularPostAdapter()
        rvPosts.layoutManager = LinearLayoutManager(context)
        rvPosts.adapter = adapter
        viewModel = ViewModelProviders.of(this, PopularViewModel.Factory(context!!))
            .get(PopularViewModel::class.java)
        viewModel.getPopular().observe(this, Observer {
            it?.run {
                adapter.posts = this
            }
        })
    }

    override fun getLayoutRes(): Int = R.layout.fragment_popular

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                shortToast("Popular")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PopularFragment()
    }

}