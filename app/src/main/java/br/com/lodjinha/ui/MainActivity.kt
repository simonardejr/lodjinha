package br.com.lodjinha.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.lodjinha.R
import br.com.lodjinha.databinding.ActivityMainBinding
import br.com.lodjinha.ui.components.filterbottomsheet.FilterBottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface NavigationDelegate {
    fun setToolbarTitle(title: String)
}


class MainActivity : AppCompatActivity(), NavigationDelegate {

    private lateinit var binding: ActivityMainBinding

    private val navigator by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationView()

        setupNavigation()
    }

    private fun setupNavigation() {
        navigator.addOnDestinationChangedListener { _, destination, _ ->

            // Set navigation view lock
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


            // Add font to toolbar title
            for (i in 0 until binding.topAppBar.childCount) {
                val view = binding.topAppBar.getChildAt(i)
                if (view is android.widget.TextView && view.text == destination.label) {
                    val typeface = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.pacificoregular)
                    view.typeface = typeface
                    break
                }
            }

            binding.topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.filter -> {

                        val bottomSheetDialogFragment: BottomSheetDialogFragment = FilterBottomSheetDialog()

                        bottomSheetDialogFragment.show(
                            supportFragmentManager, bottomSheetDialogFragment.tag
                        )

                        true
                    }
                    else -> false
                }
            }
            when(destination.id) {
                R.id.mainFragment -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    binding.topAppBar.menu.findItem(R.id.filter).apply {
                        isVisible = false
                    }
                }
                R.id.productsListFragment -> {
                    binding.topAppBar.menu.findItem(R.id.filter).apply {
                        isVisible = true
                    }
                }
                else -> {
                    binding.topAppBar.menu.findItem(R.id.filter).apply {
                        isVisible = false
                    }
                }
            }

        }
    }

    private fun setupNavigationView() {
        binding.topAppBar.setupWithNavController(navigator, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navigator)
    }

    override fun setToolbarTitle(title: String) {
        binding.topAppBar.title = title
    }
}