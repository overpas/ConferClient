package by.overpass.conferclient.ui.popular.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import by.overpass.conferclient.R
import by.overpass.conferclient.util.shortToast
import by.overpass.conferclient.viewmodel.PopularViewModel
import kotlinx.android.synthetic.main.fragment_popular.*
import timber.log.Timber

class PopularFragment : Fragment() {

    private var param1: String? = null
    private lateinit var viewModel: PopularViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, PopularViewModel.Factory(context!!))
            .get(PopularViewModel::class.java)
        /*viewModel.getPopularNoCache().observe(this, Observer {
            it?.run {
                Timber.d(this.toString())
                tvStub.text = this.toString()
            }
        })*/
        viewModel.getPopular().observe(this, Observer {
            it?.run {
                Timber.d(this.toString())
            }
        })
        if (param1 != null) {
            tvStub.text = "$param1"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                shortToast("Popular")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: String) =
            PopularFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

    }

}