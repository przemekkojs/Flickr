package com.example.flicker.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.flicker.constants.BASE
import com.example.flicker.repos.FlickrResponse
import com.example.flicker.repos.Service
import com.example.flicker.ui.theme.FlickerTheme
import com.example.flicker.view.FlickrViewModel
import com.example.flicker.view.Gallery
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val service = retrofit.create(Service::class.java)
        val repo = FlickrResponse(service)
        val viewModel = FlickrViewModel(repo)

        setContent {
            Gallery(viewModel)
        }
    }
}