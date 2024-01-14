package com.example.flicker.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.flicker.constants.MARGIN_MID
import com.example.flicker.constants.PHOTO_HEIGHT
import com.example.flicker.model.Photo
import com.example.flicker.model.Response
import com.example.flicker.repos.FlickrResponse
import kotlinx.coroutines.launch

class FlickrViewModel (private val repo: FlickrResponse) : ViewModel() {
    var photos = mutableStateOf<Response?>(null)
    var loading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            loading.value = true
            photos.value = repo.getPhotos()
            loading.value = false
        }
    }
}

@Composable
fun Gallery(viewModel: FlickrViewModel) {
    val response: Response? = viewModel.photos.value
    val loading = viewModel.loading.value

    Column {
        if (!loading) {
            val photos = response!!.items

            LazyVerticalGrid (
                columns = GridCells.Fixed(count = 3)
            ) {
                items(photos.size) { it ->
                    ImageCard (photos[it])
                }
            }
        }
    }
}

@Composable
fun ImageCard (photo: Photo) {
    Image(
        painter = rememberAsyncImagePainter(
            model = photo.media.m
        ),
        modifier = Modifier
            .width(PHOTO_HEIGHT)
            .height(PHOTO_HEIGHT)
            .padding(MARGIN_MID),

        contentDescription = photo.title,
        contentScale = ContentScale.Crop
    )
}
