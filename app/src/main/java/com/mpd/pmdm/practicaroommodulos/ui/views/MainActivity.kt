package com.mpd.pmdm.practicaroommodulos.ui.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.mpd.pmdm.practicaroommodulos.R
import com.mpd.pmdm.practicaroommodulos.databinding.ActivityMainBinding
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModel
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.ModulosViewModelFactory
import com.mpd.pmdm.practicaroommodulos.ui.viewmodel.UserPreferences


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    //Usaremos esta variable para referenciar el menú y cambiar el icono del modo Noche cuando toque
    private var miMenu: Menu? = null
    private var nightMode: Boolean = false

    private val viewModel: ModulosViewModel by viewModels {
        ModulosViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        viewModel.preferencias.observe(this) {
            updateNighModeStatus(it)
        }

    }

    /**
     * A partir del estado de la preferencia correspondiente
     * Actualiza el estado del icono que hace de conmutador del Night Mode
     * y aplica el NightMode o el modo por defecto
     */
    private fun updateNighModeStatus(it: UserPreferences) {
        nightMode = it.nightMode
        if (it.nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        //Lanzamos el refresco del menú con esta llamada. Se ejecutará onPrepareOptionsMenu()
        invalidateOptionsMenu()
        Log.d("MainActivity", "Icono: ${miMenu?.findItem(R.id.toogleNightItem)?.icon}")

    }

    /**
     * Para que los items del menú queden ligados a los destinos de navegación
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_menu, menu)
        miMenu = menu
        return true
    }

    /**
     * Tras llamar a invalidateOptionsMenu() desde el observador updateNightModeStatus() se lanza este hook
     * Aquí es donde se debe actualizar el icono y otras opciones de menú
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val itemNightMode = menu?.findItem(R.id.toogleNightItem)

        if (nightMode) {
            itemNightMode?.setIcon(R.drawable.baseline_wb_sunny_24)
            //Actualizamos también el color para que haya contraste y se vea bien
            itemNightMode?.icon?.setTint(Color.WHITE)

        } else {
            itemNightMode?.setIcon(R.drawable.baseline_mode_night_24)
            //itemNightMode?.icon?.setTint(Color.BLACK)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        Si el item pulsado es el conmutador del NightMode, lanzamos la actualización del DataStore
        que a su vez debería lanzar la función observadora configurada
         */
        if (item.itemId == R.id.toogleNightItem) {

            //actualizamos la preferencia del datastore
            viewModel.toogleNightMode()
            return true
        } else {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}