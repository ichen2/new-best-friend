/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.NewBestFriendTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewBestFriendTheme() {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val phoebe = Puppy(
        name = "Phoebe",
        image = painterResource(R.drawable.phoebe),
        description = "Phoebe is a cool dog.",
        breed = "Labrador Retriever",
        age = 6
    )
    val finn = Puppy(
        name = "Finn",
        image = painterResource(R.drawable.finn),
        description = "Finn likes to bark. Alot.",
        breed = "Dutch Shepherd",
        age = 4
    )
    val puppies = listOf(phoebe, finn, phoebe, finn, phoebe, finn, phoebe, finn, phoebe)
    var current = remember { mutableStateOf<Puppy?>(null) }

    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colors.background).verticalScroll(rememberScrollState(), enabled = current.value == null),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header()
        puppies.map { puppy -> PuppyRow(puppy, current) }
    }
    if (current.value != null) {
        PuppyCard(current)
    }
}

@Composable
fun Header() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(12.dp, 4.dp)
    ) {
        Text(
            "new best friend",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h2 + TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun PuppyRow(puppy: Puppy, current: MutableState<Puppy?>) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colors.background, MaterialTheme.colors.secondary),
                    startY = 200f
                )
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = puppy.image,
            contentScale = ContentScale.Crop,
            contentDescription = puppy.name,
            modifier = Modifier.size(64.dp).clip(shape = CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column() {
            Text(
                puppy.name,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h3 + TextStyle(fontWeight = FontWeight.SemiBold)
            )
            Text(
                puppy.description,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier
                .clickable(enabled = current.value == null, onClick = { current.value = puppy })
                .padding(32.dp)
                .size(32.dp),
            painter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.open)),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentDescription = "Open Window"
        )
    }
}

@Composable
fun PuppyCard(current: MutableState<Puppy?>) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(32.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.background)
    ) {
        Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    "Meet ${current.value!!.name}!",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h3 + TextStyle(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .clickable(onClick = { current.value = null })
                        .size(30.dp),
                    painter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.close)),
                    contentDescription = "Close Window"
                )
            }
            Spacer(Modifier.height(16.dp))
            Image(
                painter = current.value!!.image,
                contentScale = ContentScale.Crop,
                contentDescription = "A puppy",
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.weight(.5f))
            Text(
                "Age: ${current.value!!.age}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1
            )
            Text(
                "Breed: ${current.value!!.breed}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1
            )
            Text(
                current.value!!.description,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1
            )
            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    NewBestFriendTheme {
        MainScreen()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    NewBestFriendTheme(darkTheme = true) {
        MainScreen()
    }
}
