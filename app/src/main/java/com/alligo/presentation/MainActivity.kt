package  com.alligo.presentation

import android.os.Bundle
import android.util.Log
import com.alligo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.alligo.R

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    var isReady = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isReady = savedInstanceState?.getBoolean("is ready", false) == true
        installSplashScreen().setKeepOnScreenCondition {
            savedInstanceState?.putBoolean("is ready", isReady)
            Log.i("Splash", "onCreate: $isReady")
            !isReady
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        decideNavigation()
    }


    private fun decideNavigation() {
        val token = BaseApplication.appPreferences.token // Get token from shared preferences

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment?
        val navHostFragment = binding.mainActivityContainer.getFragment<NavHostFragment?>()
        Log.i("splash", "${navHostFragment == null}")

        navHostFragment?.let {
            val inflater = it.navController.navInflater
            val graph = inflater.inflate(R.navigation.main_navigation)

            if (token.isEmpty()) {
                graph.setStartDestination(R.id.mainContainerFragment)
            } else {
                graph.setStartDestination(R.id.cartFragment)
            }

            val navController = it.navController
            navController.setGraph(graph, intent.extras)

        }


        Log.i("splash", "$token")


        isReady = true // Allow splash screen to be removed
    }
}