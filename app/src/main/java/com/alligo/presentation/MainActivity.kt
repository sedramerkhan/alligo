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

        installSplashScreen().setKeepOnScreenCondition {
            Log.i("Splash", "onCreate: $isReady")
            !isReady
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}