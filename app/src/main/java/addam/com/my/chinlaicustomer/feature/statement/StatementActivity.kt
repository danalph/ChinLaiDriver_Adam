package addam.com.my.chinlaicustomer.feature.statement

import addam.com.my.chinlaicustomer.AppPreference
import addam.com.my.chinlaicustomer.R
import addam.com.my.chinlaicustomer.core.BaseActivity
import addam.com.my.chinlaicustomer.databinding.ActivityStatementBinding
import addam.com.my.chinlaicustomer.databinding.NavHeaderDashboardBinding
import addam.com.my.chinlaicustomer.rest.model.StatementListResponse
import addam.com.my.chinlaicustomer.utilities.KeyboardManager
import addam.com.my.chinlaicustomer.utilities.observe
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_statement.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_statement.*
import javax.inject.Inject


class StatementActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    StatementAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModel: StatementViewModel

    @Inject
    lateinit var appPreference: AppPreference

    lateinit var binding: ActivityStatementBinding
    lateinit var adapter: StatementAdapter
    private var model = arrayListOf<StatementListResponse.Data.Statement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statement)
        binding.viewModel = viewModel

        val headerBind: NavHeaderDashboardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_dashboard,binding.navView, false)
        binding.navView.addHeaderView(headerBind.root)
        headerBind.name = viewModel.name.get().toString()
        headerBind.isSalesPerson = appPreference.getSalesId() != "0"

        setSupportActionBar(toolbar)
        setupView()
        setupObserver()
        setupRecyclerView()

    }

    private fun setupObserver() {
        viewModel.statementListItem.observe(this) {
            it ?: return@observe
            adapter.run {
                models.clear()
                models.addAll(it)
                notifyDataSetChanged()
            }
        }

        viewModel.statementUrl.observe(this){
            it?: return@observe
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(it)
            startActivity(i)
        }
    }

    private fun setupRecyclerView() {
        statement_list.layoutManager = LinearLayoutManager(baseContext)

        adapter = StatementAdapter(model, this)
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
        setupNavigationLayout(nav_view, appPreference)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        setNavigation(item, appPreference, this@StatementActivity::class.java.simpleName)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemDownload(position: Int, item: StatementListResponse.Data.Statement) {
        viewModel.getDownload(item)
    }
}
