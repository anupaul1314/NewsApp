package com.example.newsapp.features.newslist

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.data.modals.Articles

@Composable
fun NewsScreen(viewModal: NewsViewModal) {
    val textFilledState = remember { mutableStateOf("") }
    val newsList by viewModal.newsList.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModal.getNewsList()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.2f),
                    value = textFilledState.value,
                    onValueChange = { newText ->
                        textFilledState.value = newText
                    },
                    shape = CircleShape,
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Notifications Icon",
                            tint = colorResource(id = R.color.darkpink)
                        )
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedIconButton(
                    modifier = Modifier
                        .size(50.dp),
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.darkpink)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications Icon",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
            NewsTypesCard()
            LazyColumn() {
                items(newsList.size) {
                    NewsCardItems(
                        articles = newsList.get(it))
                }
            }
        }
    }

}

@Composable
fun NewsTypesCard() {
    val buttonLabels = listOf("Science", "Sports", "Technology", "Entertainment", "Business", "Health")
    var selectedIndex by remember { mutableStateOf(0) }
    LazyRow {
        items(buttonLabels.size) {index->
            val buttonColor = if (selectedIndex==index) {
                colorResource(id = R.color.darkpink)
            } else {
                colorResource(id = R.color.white)
            }
            val contentColor = if (selectedIndex==index) {
                Color.White
            } else {
                Color.Black
            }
            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                          selectedIndex = index
                          },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonColor,
                    contentColor = contentColor
                )
            ) {
                Text(text = buttonLabels[index])
            }
        }
    }
}

@Composable
fun NewsCardItems(
    articles: Articles
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.darkpink)
        ),
        elevation = CardDefaults.cardElevation(
            focusedElevation = 50.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            articles.title?.let {
                Text(
                    text = articles.title,
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.white)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                articles.author?.let {
                    Text(
                        text = articles.author,
                        fontSize = 8.sp,
                        color = colorResource(id = R.color.white)
                    )
                }
                
                articles.publishedAt?.let {
                    Text(
                        text = articles.publishedAt,
                        fontSize = 8.sp,
                        color = colorResource(id = R.color.white)
                    )
                }
            }

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
