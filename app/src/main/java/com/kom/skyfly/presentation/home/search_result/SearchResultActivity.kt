package com.kom.skyfly.presentation.home.search_result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivitySearchResultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultActivity : AppCompatActivity() {
    private val binding : ActivitySearchResultBinding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private val searchResultViewModel: SearchResultViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }


}
