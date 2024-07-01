package com.manish.moviesearch.views.activiity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cuteanimals.R
import com.example.cuteanimals.databinding.ActivityMainBinding
import com.manish.moviesearch.core.utils.UIState
import com.manish.moviesearch.views.viewmodel.MovieViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        collector()
        setupUI()
    }

    private fun setupUI() {
        binding.searchBtn.setOnClickListener {
            if(binding.searchEt.text.toString().isNotEmpty()) {
                viewModel.fetchMovie(binding.searchEt.text.toString())
            }
        }
    }

    private fun collector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainItem.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.GONE
                            binding.title.visibility = View.VISIBLE
                            binding.title.text = it.data.Title
                        }

                        is UIState.Failure -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.VISIBLE
                            binding.title.visibility = View.GONE
                            binding.error.text = it.throwable.toString()
                        }

                        is UIState.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.error.visibility = View.GONE
                            binding.title.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}