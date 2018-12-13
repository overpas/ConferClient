package by.overpass.conferclient.ui.latest.fragment


import android.view.MenuItem
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.fragment.PostListFragment
import by.overpass.conferclient.util.shortToast

class LatestFragment : PostListFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_latest

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                shortToast("Latest")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LatestFragment()
    }
}
