package com.example.deliveryapp.activity_client_and_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.deliveryapp.viewmodels.LoggedInViewModel
import com.example.deliveryapp.databinding.ActivityClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityClient : AppCompatActivity() {
    private lateinit var binding: ActivityClientBinding
    private val viewModel: LoggedInViewModel by viewModels()
    private var idClient: Int? = null
    private var nameTown: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //u app baru cemo imati pristup korpi iz svakog fragmenta i vjerovatno imageview koji ce se mijenjati
        //prije nego sto otvorimo noovi fragment. podatke za korpu cemo dijeliti preko viewmodela

        var intent = intent


        idClient = intent.getIntExtra("idClient", 0)
        nameTown = intent.getStringExtra("nameTown")

        val layoutInflater = LayoutInflater.from(this)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getIdClient(): Int?{
        return idClient
    }

    fun getNameTown(): String?{
        return nameTown
    }
}