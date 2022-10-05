package com.example.deliveryapp.activity_client_and_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.LoggedInViewModel
import com.example.deliveryapp.RestaurantsAdapter
import com.example.deliveryapp.databinding.ActivityClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityClient : AppCompatActivity() {
    private lateinit var binding: ActivityClientBinding
    private val viewModel: LoggedInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //u app baru cemo imati pristup korpi iz svakog fragmenta i vjerovatno imageview koji ce se mijenjati
        //prije nego sto otvorimo noovi fragment. podatke za korpu cemo dijeliti preko viewmodela
        val layoutInflater = LayoutInflater.from(this)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}