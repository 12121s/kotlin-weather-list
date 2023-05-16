package com.illis.weatherlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.illis.weatherlist.databinding.ActivityMainBinding
import com.illis.weatherlist.ui.UiState
import com.illis.weatherlist.ui.adapter.CityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private val adapter by lazy { CityAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        setRecyclerView()
        fetchWeatherList()
    }

    private fun setRecyclerView() {
        binding.wCategoryRecyclerView.adapter = adapter
    }

    private fun fetchWeatherList() {
        lifecycleScope.launch {
            viewModel.weatherList
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { state ->
                    binding.progressBar.visibility = if (state == UiState.Loading) View.VISIBLE else View.GONE
                    when (state) {
                        is UiState.Success -> {
                            adapter.submitList(state.data)
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@MainActivity, state.error?.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
        }
    }
}