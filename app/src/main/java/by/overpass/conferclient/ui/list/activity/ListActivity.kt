package by.overpass.conferclient.ui.list.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.activity.BaseAuthActivity
import by.overpass.conferclient.ui.list.fragment.about.AboutDialogFragment
import by.overpass.conferclient.ui.list.fragment.latest.LatestFragment
import by.overpass.conferclient.ui.list.fragment.logout.LogoutDialogFragment
import by.overpass.conferclient.ui.list.fragment.popular.PopularFragment
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.replaceFragment
import by.overpass.conferclient.util.vm
import by.overpass.conferclient.viewmodel.list.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.app_bar_list.*

class ListActivity : BaseAuthActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tvUserName: TextView
    private lateinit var btnLogin: Button

    private val viewModel: ListViewModel by vm(ListViewModel.Factory::class.java)

    private val tokenChangedListener = object : Preferences.OnTokenChangedListener {
        override fun onTokenChanged() {
            if (isLoggedIn()) {
                fetchCurrentUser()
            } else {
                tvUserName.text = getString(R.string.nav_header_name)
                btnLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)
        setupNavigationDrawer()
        setupFab()
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.action_latest)
            onNavigationItemSelected(navigationView.menu.getItem(0))
        }
    }

    private fun setupFab() {
        fabNewPost.setOnClickListener {
            attemptNewPost()
        }
    }

    private fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayoutMain,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayoutMain.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)
        tvUserName = header.findViewById(R.id.tvUserName)
        btnLogin = header.findViewById(R.id.btnLogin)
        if (isLoggedIn()) {
            fetchCurrentUser()
        }
        btnLogin.setOnClickListener {
            showLoginDialog(false)
        }

    }

    override fun onStart() {
        super.onStart()
        Preferences.addTokenListener(tokenChangedListener)
    }

    override fun onStop() {
        super.onStop()
        Preferences.removeTokenListener(tokenChangedListener)
    }

    override fun onBackPressed() {
        if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutMain.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_latest -> {
                replaceFragment(LatestFragment.newInstance(), R.id.flListFragmentContainer, false)
            }
            R.id.action_popular -> {
                replaceFragment(PopularFragment.newInstance(), R.id.flListFragmentContainer, false)
            }
            R.id.action_about -> {
                showAboutDialog()
            }
            R.id.action_logout -> {
                showLogoutDialog()
            }
        }
        drawerLayoutMain.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showAboutDialog() {
        AboutDialogFragment.newInstance()
            .show(supportFragmentManager, AboutDialogFragment.TAG)
    }

    private fun showLogoutDialog() {
        LogoutDialogFragment.newInstance()
            .show(supportFragmentManager, LogoutDialogFragment.TAG)
    }

    private fun fetchCurrentUser() {
        viewModel.getCurrentUser().observe(this, Observer {
            it?.run {
                tvUserName.text = fullName
                btnLogin.visibility = View.GONE
            } ?: run {
                Preferences.deleteToken()
                tvUserName.text = getString(R.string.nav_header_name)
                btnLogin.visibility = View.VISIBLE
            }
        })
    }

}
