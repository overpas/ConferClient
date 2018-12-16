package by.overpass.conferclient.ui.list.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import by.overpass.conferclient.R
import by.overpass.conferclient.ui.list.latest.fragment.LatestFragment
import by.overpass.conferclient.ui.list.popular.fragment.PopularFragment
import by.overpass.conferclient.util.replaceFragment
import by.overpass.conferclient.util.shortToast
import kotlinx.android.synthetic.main.app_bar_list.*
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_chat)
                .setTitle(getString(R.string.new_post))
                .setView(LayoutInflater.from(this).inflate(R.layout.view_new_post, null))
                .setPositiveButton(R.string.send) { dialog, which ->
                    shortToast(R.string.send)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel) { dialog, which ->
                    shortToast(R.string.cancel)
                    dialog.dismiss()
                }
                .create()
                .show()
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
