package by.overpass.conferclient.ui.list.fragment.popular

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.ui.base.fragment.PostListFragment
import by.overpass.conferclient.ui.list.fragment.popular.adapter.PopularPostAdapter
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.vm
import by.overpass.conferclient.viewmodel.popular.PopularViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class PopularFragment : PostListFragment() {

    private val viewModel: PopularViewModel by vm(PopularViewModel.Factory::class.java)
    private lateinit var adapter: PopularPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularPostAdapter()
        rvPosts.layoutManager = LinearLayoutManager(context)
        rvPosts.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchData()
    }

    override fun getActionBarTitleRes(): Int = R.string.popular

    override fun fetchData(text: String?) {
        viewModel.getProgress().removeObservers(this)
        viewModel.getProgress().observe(this, Observer {
            it?.run {
                onStatusChanged(this)
            }
        })
        viewModel.getPopular(text).removeObservers(this)
        viewModel.getPopular(text).observe(this, Observer {
            it?.run {
                adapter.posts = this
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean = true
//
//            override fun onQueryTextChange(text: String?): Boolean {
//                if (!text.isNullOrEmpty()) {
//                    fetchData(text)
//                }
//                return true
//            }
//
//        })
//        searchView.setOnCloseListener {
//            fetchData()
//            false
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance() = PopularFragment()
    }

}