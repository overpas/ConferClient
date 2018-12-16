package by.overpass.conferclient.ui.base.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import by.overpass.conferclient.R

abstract class PostListFragment : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    @StringRes
    abstract fun getActionBarTitleRes(): Int

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
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            if (this is AppCompatActivity) {
                this.supportActionBar?.let {
                    it.title = resources.getString(getActionBarTitleRes())
                }
            }
        }
    }

    /**
     * Should be used to set necessary observers
     */
    protected open fun onViewModelReady() {}

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}