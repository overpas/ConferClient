package by.overpass.conferclient.ui.list.fragment.latest


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.ui.base.fragment.PostListFragment
import by.overpass.conferclient.ui.list.fragment.latest.adapter.LatestPostAdapter
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.util.vm
import by.overpass.conferclient.viewmodel.latest.LatestViewModel
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_list.*

class LatestFragment : PostListFragment() {

    private val adapter = LatestPostAdapter()
    private val viewModel: LatestViewModel by vm(LatestViewModel.Factory::class.java)

    override fun getActionBarTitleRes(): Int = R.string.latest

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // TODO: Implement
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPosts.layoutManager = LinearLayoutManager(context)
        rvPosts.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchData()
    }

    override fun setupSwipeToRefresh() {
        super.setupSwipeToRefresh()
        // TODO: Override to fetch new data
    }

    override fun fetchData(text: String?) {
        viewModel.getLatestPosts().removeObservers(this)
        viewModel.getLatestPosts().observe(this, Observer {
            it?.run {
                adapter.submitList(this)
            }
        })
        viewModel.getProgress().removeObservers(this)
        viewModel.getProgress().observe(this, Observer {
            it?.run {
                onStatusChanged(this)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = LatestFragment()
    }
}
