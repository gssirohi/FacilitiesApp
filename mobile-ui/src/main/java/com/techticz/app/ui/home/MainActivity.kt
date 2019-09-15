package com.techticz.app.ui.home

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.gssirohi.techticz.voicebook.ui.voicebook.VoiceBookAdapter
import com.gssirohi.techticz.voicebook.ui.voicebook.VoicebookActivity
import com.techticz.app.R
import com.techticz.app.ui.voicebook.VoiceBookBottomSheetFragment
import com.techticz.app.ui.voicebook.VoiceBookBottomSheetFragment.Companion.VIEW_MODE_CREATE
import com.techticz.database.model.VoiceBook
import com.techticz.domain.model.VoiceBooksRequest
import com.techticz.presentation.ViewModelFactory
import com.techticz.presentation.viewmodel.voicebook.VoiceBooksViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.container
import kotlinx.android.synthetic.main.content_main.error_view
import kotlinx.android.synthetic.main.content_main.loading_view
import org.buffer.android.boilerplate.presentation.data.ResourceState
import javax.inject.Inject

class MainActivity : AppCompatActivity(),VoiceBookAdapter.BookListner,VoiceBookBottomSheetFragment.BottomSheetCallBack {
    override fun onVoiceBookRequested(): VoiceBook {
        return VoiceBook(0,"",0,0)
    }

    override fun onVoiceBookSubmit(book: VoiceBook, viewMode: Int) {
        voicebookViewModel.createVoiceBook(book)
    }

    override fun onBookClicked(book: VoiceBook) {
        if(book.id == -1L){
            VoiceBookBottomSheetFragment.newInstance(VIEW_MODE_CREATE).show(supportFragmentManager, "dialog")
        } else {
            var intent = Intent(this, VoicebookActivity::class.java)
            intent.putExtra("voiceBookId", book.id)
            intent.putExtra("voiceBookColor", book.color)
            intent.putExtra("voiceBookName", book.title)
            startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var voicebookViewModel: VoiceBooksViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        voicebookViewModel = ViewModelProviders.of(this,viewModelFactory).get(VoiceBooksViewModel::class.java)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_share
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getVoiceBookViewModel().getVoiceBookCreatedSignal().observe(this, androidx.lifecycle.Observer {
            if (it != null) this.onCreateBookSignal(it.status, it.data, it.message)
        })
    }

    private fun onCreateBookSignal(status: ResourceState, data: Long?, message: String?) {
        when(status){
            ResourceState.LOADING->{
                //setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
                //setupScreenForSuccess()
                Snackbar.make(coordinatorLayout, "Voice book created.", Snackbar.LENGTH_LONG).show()
                voicebookViewModel.fetchVoiceBooks(VoiceBooksRequest())
            }
            ResourceState.ERROR->{
                //setupScreenForError("Voice book could not be created!!")
                // Snackbar.make(coordinatorLayout, "Voice memo could not be created!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun getVoiceBookViewModel(): VoiceBooksViewModel {
        return voicebookViewModel
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setupScreenForSuccess() {
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    fun setupScreenForError(message: String?) {
        loading_view.visibility = View.GONE
     //   container.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    fun setupScreenForLoadingState() {
        loading_view.visibility = View.VISIBLE
      //  container.visibility = View.GONE
        error_view.visibility = View.GONE
    }
}
