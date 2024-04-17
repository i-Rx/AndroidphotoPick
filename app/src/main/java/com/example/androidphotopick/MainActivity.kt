package com.example.androidphotopick

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.androidphotopick.ui.theme.AndroidPhotoPickTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPhotoPickTheme {

                val imageUri = remember {
                    mutableStateOf<Uri?>(null)
                }
                val imageUris = remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }
                val lanucherForSingleMeadia = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickVisualMedia()
                ) {uri: Uri? ->
                    imageUri.value =uri

                }

                val lanucherForMultipleMeadia = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickMultipleVisualMedia()
                ) {
                    imageUris.value = it

                }
                LazyColumn {
                    item {
                        Row(Modifier.padding(top =50.dp)) {
                            Button(onClick =  {
                                lanucherForSingleMeadia.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                                )
                            }){
                                Text(text = "Select Single Image")
                        }
                            Button(onClick =  {
                                lanucherForMultipleMeadia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }){
                                Text(text = "Select Multiple Images")
                            }
                         }
                    }
                    item {
                        AsyncImage(model = imageUri.value, contentDescription = null, modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                    }
                    items(imageUris.value) {
                        AsyncImage(model = it, contentDescription = null, modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                    }
                }

            }
        }
    }


    fun checkLoctaionPremissiom() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        }
    }
}

