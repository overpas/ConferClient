package by.overpass.conferclient.ui.popular.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.fragment.PostListFragment
import by.overpass.conferclient.ui.popular.adapter.PopularPostAdapter
import by.overpass.conferclient.util.getVm
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.popular.PopularViewModel
import kotlinx.android.synthetic.main.fragment_popular.*

private typealias VM = PopularViewModel
private typealias VMFactory = PopularViewModel.Factory

class PopularFragment : PostListFragment() {

    private lateinit var viewModel: VM
    private lateinit var adapter: PopularPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PopularPostAdapter()
        rvPosts.layoutManager = LinearLayoutManager(context)
        rvPosts.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getVm(this, VM::class.java, VMFactory::class.java)
        onViewModelReady()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_popular

    override fun onViewModelReady() {
        viewModel.getPopular().observe(this, Observer {
            it?.run {
                adapter.posts = this
            }
        })
    }

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