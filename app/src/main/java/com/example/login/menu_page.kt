package com.example.login


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit


class menu_page : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1

    fun accessGoogleFitData() {



        var email = SessionManager.mySessionData
        Log.i("Acceso","$email")

        val requestData = JsonObject()
        requestData.addProperty("correo_user", email)

        Log.i("Acceso","Llega hasta")

        val call: Call<JsonObject> = DatabasemsRetrofit.api_service_databasems.searchUserByMail(requestData)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonObject = response.body() // JSON de respuesta

                    val id = jsonObject?.get("id_user")?.toString()
                    val name = jsonObject?.get("nombre_user")?.toString()
                    val lastname = jsonObject?.get("apellido_user")?.toString()
                    val birtday = jsonObject?.get("fecha_nacimiento_user")?.toString()
                    val gender = jsonObject?.get("genero_user")?.toString()

                    UserTemporalData.id_user = id?.replace("\"", "")
                    UserTemporalData.nombre_user = name?.replace("\"", "")
                    UserTemporalData.apellido_user =lastname?.replace("\"", "")
                    UserTemporalData.fecha_nacimiento_user = birtday?.replace("\"", "")
                    UserTemporalData.genero_user = gender?.replace("\"", "")

                } else {
                    // Manejar el error de respuesta
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Manejar el error de la llamada
            }
        })



    }


    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        accessGoogleFitData()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navegation_drawer_open, R.string.avegation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)



        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        viewPager = findViewById(R.id.view_pager)
        val adapter = MyPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_1)
                    tab.setIcon(R.drawable.ic_sprint_48px)
                }
                1 -> {
                    tab.text = getString(R.string.tab_2)
                    tab.setIcon(R.drawable.ic_rewarded_ads_48px)
                }
            }
        }.attach()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val welcomeText = findViewById<TextView>(R.id.nav_header_TextView)
        welcomeText.text = "Bienvenido ${UserTemporalData.nombre_user.toString()} ${UserTemporalData.apellido_user.toString()}"
        when (item.itemId) {
            R.id.nav_item_one -> {Toast.makeText(this, "Registro", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, records_page::class.java)
                startActivity(intent)
            }
            R.id.nav_item_two -> {Toast.makeText(this, "Metas", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, goalsf_page::class.java)
                startActivity(intent)
            }
            R.id.nav_item_three -> {Toast.makeText(this, "Puntos", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, points_t_page::class.java)
                startActivity(intent)
            }
            R.id.nav_item_six -> {Toast.makeText(this, "Configuracion de Cuenta", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, account_page::class.java)
                startActivity(intent)
            }
            R.id.nav_item_seven -> {Toast.makeText(this, "Acceso a Soporte", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, support_page::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
