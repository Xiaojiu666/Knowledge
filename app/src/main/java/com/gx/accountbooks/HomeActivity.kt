package com.gx.accountbooks

import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.gx.base.base.BaseAppCompatActivity
import com.gx.utils.log.LogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseAppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    val homeVIewModel:HomeVIewModel by viewModels()

    override fun init() {
        LogUtil.e(homeVIewModel.homeR.name)
    }

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // 为导航组件配置NavigationUI
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_home, R.id.menu_account, R.id.menu_diary,R.id.menu_task,R.id.menu_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // 动态修改左上角title
        //TODO : BUG，如果监听放在set之前，监听无效
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("navController", destination.label.toString());
            Log.e("navController", destination.toString());
        }
    }

    override fun getLayoutView(): View = createView(R.layout.activity_main)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


