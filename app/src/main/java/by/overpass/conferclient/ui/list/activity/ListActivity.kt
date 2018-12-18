package by.overpass.conferclient.ui.list.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.base.activity.BaseActivity
import by.overpass.conferclient.ui.list.fragment.create.NewPostDialogFragment
import by.overpass.conferclient.ui.list.fragment.latest.LatestFragment
import by.overpass.conferclient.ui.list.fragment.popular.PopularFragment
import by.overpass.conferclient.util.Preferences
import by.overpass.conferclient.util.replaceFragment
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.app_bar_list.*

class ListActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tvUserName: TextView
    private lateinit var btnLogin: Button

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

    override fun onPause() {
        super.onPause()
        // TODO: Stub to ease checks
        Preferences.deleteToken()
    }

    override fun offerToCreateNewPost() {
        showNewPostDialog()
    }

    private fun setupFab() {
        fabNewPost.setOnClickListener {
            if (!isLoggedIn()) {
                showLoginDialog(true)
            } else {
                showNewPostDialog()
            }
        }
    }

    private fun showNewPostDialog() {
        // TODO: DialogFragment
        NewPostDialogFragment.newInstance().show(supportFragmentManager, NewPostDialogFragment.TAG)
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
            tvUserName.text = getString(R.string.authorized)
            btnLogin.visibility = View.GONE
        }
        btnLogin.setOnClickListener {
            showLoginDialog(false)
        }
        Preferences.addTokenListener(object : Preferences.OnTokenChangedListener {
            override fun onTokenChanged() {
                if (isLoggedIn()) {
                    tvUserName.text = getString(R.string.authorized)
                    btnLogin.visibility = View.GONE
                } else {
                    tvUserName.text = getString(R.string.nav_header_name)
                    btnLogin.visibility = View.VISIBLE
                }
            }
        })
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

            }
        }
        drawerLayoutMain.closeDrawer(GravityCompat.START)
        return true
    }

}