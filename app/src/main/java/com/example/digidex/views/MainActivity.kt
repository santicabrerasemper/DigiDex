package com.example.digidex

import DigimonViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digidex.core.RetrofitClient
import com.example.digidex.databinding.ActivityMainBinding
import com.example.digidex.repositories.DigimonRepository
import com.example.digidex.viewmodels.DigimonViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var digimonAdapter: DigimonAdapter

    private val digimonViewModel: DigimonViewModel by viewModels {
        DigimonViewModelFactory(DigimonRepository(RetrofitClient.webService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        loadDigimonList()
        setUpObservers()
        setUpSearchBar()
    }

    private fun setUpSearchBar() {
        binding.textInputEditText.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            digimonAdapter.filter(query)
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            digimonViewModel.digimonList.collect { digimonList ->
                digimonAdapter.submitOriginalList(digimonList)
            }
        }

        lifecycleScope.launch {
            digimonViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        digimonAdapter = DigimonAdapter { digimon ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DIGIMON_DETAILS", digimon)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = digimonAdapter
    }

    private fun loadDigimonList() {
        digimonViewModel.loadAllDigimon()
    }
}
