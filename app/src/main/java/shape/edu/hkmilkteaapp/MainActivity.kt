package shape.edu.hkmilkteaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MainFragment()).commit()
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navigationView)

        val user = Firebase.auth.currentUser
        val email = user?.email
        val navHeader = navView.getHeaderView(0)
        val navHeaderEmail = navHeader.findViewById<TextView>(R.id.textViewUserEmail)
        navHeaderEmail.text = "$email"

        toggle =ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){
                R.id.navHome -> {
                    replaceFragment(MainFragment(), it.title.toString())
                }
                R.id.navTutorialTools -> {
                    replaceFragment(ToolsFragment(), it.title.toString())
                }
                R.id.navTutorialProcedures -> {
                    replaceFragment(ProceduresFragment(), it.title.toString())
                }
                R.id.navTutorialVideos -> {
                    replaceFragment(VideoFragment(), it.title.toString())
                }
                R.id.navNotes -> {
                    replaceFragment(NotesFragment(), it.title.toString())
                }
                R.id.navMap -> {
                    replaceFragment(MapsFragment(), it.title.toString())
                }
                R.id.navLogout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
            }

            true

        }
    }

    private fun replaceFragment(fragment: Fragment,title : String) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}