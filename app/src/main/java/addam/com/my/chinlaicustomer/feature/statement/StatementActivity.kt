package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.core.Router
import addam.com.my.chinlaicustomer.databinding.ActivityStatementBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.StatementResponseModel
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_statement.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_statement.*
import javax.inject.Inject

class StatementActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    StatementAdapter.OnItemClickListener, StatementAdapter.OnItemSelectListener {
    @Inject
    lateinit var viewModel: StatementViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityStatementBinding
    lateinit var adapter: StatementAdapter
    lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statement)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()

        setSupportActionBar(toolbar)
        setupView()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        statement_list.layoutManager = LinearLayoutManager(baseContext)

        adapter = StatementAdapter(viewModel.setDummyModels(), this, this)
        statement_list.adapter = adapter

    }


    private fun setupView(){
        KeyboardManager.hideKeyboard(this)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.my_statement)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        snackbar = Snackbar.make(layout_statement, getString(R.string.download_multiple), Snackbar.LENGTH_INDEFINITE)
        val view = snackbar.view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(resources.getColor(R.color.black, null))
        }else view.setBackgroundColor(resources.getColor(R.color.black))
        val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        text.setTextColor(Color.parseColor("#ffffff"))
        snackbar.setAction(R.string.download) {
            viewModel.getDownloads(adapter.models)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.btn_browse_product -> {
                startActivity(this@StatementActivity, Router.getClass(Router.Destination.DASHBOARD), clearHistory = true)
            }
            R.id.btn_my_order -> {

            }
            R.id.btn_my_invoice -> {

            }
            R.id.btn_my_statement -> {
            }
            R.id.profile -> {
                startActivity(this@StatementActivity, Router.getClass(Router.Destination.PROFILE))
            }
            R.id.logout -> {
                appPreference.setLoggedIn(false)
                startActivity(this@StatementActivity, Router.getClass(Router.Destination.LOGIN), clearHistory = true)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statement, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_delete){
            val iterate = adapter.models.toList()
            for (model in iterate){
                if(model.isSelected){
                    adapter.removeItemAt(iterate.indexOf(model))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemDownload(position: Int, item: StatementResponseModel) {
        viewModel.getDownload(item)
    }

    override fun onItemSelect(position: Int, item: StatementResponseModel) {
        var selected = 0
        val iterate = adapter.models.toList()
        for (model in iterate){
            if (model.isSelected){
                selected += 1
            }
        }
        if (selected > 1){
            snackbar.show()
        }else snackbar.dismiss()
    }
}
