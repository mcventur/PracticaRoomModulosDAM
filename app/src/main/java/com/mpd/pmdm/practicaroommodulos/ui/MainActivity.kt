package com.mpd.pmdm.practicaroommodulos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Seteamos la toolbar con el componente de navegación, para que muestre el título de la ventana actual
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)

        //Seteamos las acciones de cada item
        binding.bottomNavigation.setOnItemSelectedListener {
            bottomBarOnClickItem(it)
        }
    }

    /**
     * Listener al hacer click en cada item del menú de la BottomBar
     */
    fun bottomBarOnClickItem(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    /**
     * Permite a los fragmentos cambiar el título predefinido de la ventana por otro personalizado
     */
    fun setTitulo(titulo: String){
        binding.topAppBar.setTitle(titulo)
    }

}