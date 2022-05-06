package net.starry.cleanarch.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import net.starry.cleanarch.R
import net.starry.cleanarch.databinding.ActivityMainBinding
import net.starry.cleanarch.presentation.base.BaseActivity
import net.starry.cleanarch.presentation.utils.toast

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private val navController by lazy { findNavController(R.id.fragment_host) }
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initNavigation()
    }

    private fun initNavigation() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        navHostFragment.navController.graph = graph

        val radius = resources.getDimension(R.dimen.radius_medium)
        (binding.navBottom.background as MaterialShapeDrawable).run {
            shapeAppearanceModel =
                shapeAppearanceModel.toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, radius)
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .build()

        }

        binding.navBottom.run {
            setupWithNavController(navController)
            itemIconTintList = null
        }

    }


}

