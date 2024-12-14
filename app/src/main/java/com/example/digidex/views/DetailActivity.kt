package com.example.digidex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.digidex.databinding.ActivityDetailBinding
import com.example.digidex.models.Digimon

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val digimon = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DIGIMON_DETAILS", Digimon::class.java)
        } else {
            intent.getParcelableExtra("DIGIMON_DETAILS")
        }

        digimon?.let {
            receiveDigimonDetails(it)
        }
    }

    private fun receiveDigimonDetails(digimon: Digimon) {
        binding.digimonDetailName.text = digimon.name
        binding.digimonDetailLevel.text = digimon.level
        Glide.with(this)
            .load(digimon.img)
            .into(binding.digimonDetailImage)
    }
}

