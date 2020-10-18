package com.sn.accountbooks

import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ImmersionBar
import com.sn.accountbooks.base.BaseAppCompatActivity

class HomeActivity : BaseAppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun init() {
    }

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun setViewId(): Int {
        return R.layout.activity_main
    }

    override fun initStatusBar() {
//        ImmersionBar.with(this)
//            .titleBarMarginTop(R.id.toolbar) //可以为任意view
//            .init()
    }
}