package by.overpass.conferclient.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.latest.fragment.LatestFragment
import by.overpass.conferclient.ui.popular.fragment.PopularFragment
import by.overpass.conferclient.util.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigationDrawer()
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.action_latest)
            onNavigationItemSelected(navigationView.menu.getItem(0))
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
                replaceFragment(LatestFragment.newInstance(), R.id.flMainFragmentContainer, false)
            }
            R.id.action_popular -> {
                replaceFragment(PopularFragment.newInstance(), R.id.flMainFragmentContainer, false)
            }
            R.id.action_about -> {

            }
        }
        drawerLayoutMain.closeDrawer(GravityCompat.START)
        return true
    }
}
