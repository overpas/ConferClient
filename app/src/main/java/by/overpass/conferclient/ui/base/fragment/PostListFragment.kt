package by.overpass.conferclient.ui.base.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.data.dto.Status
import by.overpass.conferclient.util.shortToast
import kotlinx.android.synthetic.main.fragment_list.*

private const val REFRESH_PERIOD_MS = 2000L

abstract class PostListFragment : Fragment() {

    @StringRes
    abstract fun getActionBarTitleRes(): Int

    abstract fun fetchData(text: String? = null)

    protected open fun setupSwipeToRefresh() {
        srlRefresh.setOnRefreshListener {
            fetchData()
            srlRefresh.postDelayed({
                srlRefresh.isRefreshing = false
            }, REFRESH_PERIOD_MS)
        }
    }

    protected open fun onStatusChanged(status: Status) {
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

    protected open fun setLoading(loading: Boolean) {
        pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            if (this is AppCompatActivity) {
                this.supportActionBar?.let {
                    it.title = resources.getString(getActionBarTitleRes())
                }
            }
        }
        setupSwipeToRefresh()
    }

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = true

            override fun onQueryTextChange(text: String?): Boolean {
                if (!text.isNullOrEmpty()) {
                    fetchData(text)
                }
                return true
            }

        })
        searchView.setOnCloseListener {
            fetchData()
            false
        }
    }


}