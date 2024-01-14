package com.example.flicker.repos;

import com.example.flicker.model.Response;
import retrofit2.http.GET


interface Service {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPhotos(): Response
}

class FlickrResponse (private val service: Service) {
    suspend fun getPhotos(): Response {
        val response: Response?

        try {
            response = service.getPhotos()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }

        return response
    }
}
