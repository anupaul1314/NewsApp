package com.example.newsapp.features.newslist

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.modals.Articles
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewsListScreen(
    viewModal: NewsViewModal,
    onClicked: (Int) -> Unit,
    onIconClicked: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val textFilledState = rememberSaveable { mutableStateOf("") }

    val selectedCategory by viewModal.selectedCategory.collectAsState()

    val newsList by viewModal.newsList.collectAsState()

    LaunchedEffect(key1 = selectedCategory) {
        viewModal.getNewsListByCategory(selectedCategory)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { },
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Notifications Icon",
                            tint = colorResource(id = R.color.darkpink)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search"
                        )
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedIconButton(
                    modifier = Modifier
                        .size(50.dp),
                    onClick = { onIconClicked() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.darkpink)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Notifications Icon",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
            NewsTypesCard(
                onCategoryClicked = {
                    coroutineScope.launch {
                        viewModal.updateSelectedCategory(it)
                    }
                })
            LazyColumn() {
                items(newsList.size) {index ->
                    val article = newsList[index]
                    NewsCardItems(
                        articles = newsList.get(index),
                        onCategoryClicked  = {onClicked(index)}
                    )
                }
            }
        }
//        Box(
//            modifier = Modifier
//                .padding(bottom = 40.dp)
//                .height(60.dp)
//                .width(230.dp)
//                .align(Alignment.BottomCenter)
//                .clip(RoundedCornerShape(30.dp))
//                .background(color = colorResource(id = R.color.lightpink))
//                .fillMaxSize()
//        ) {
//            Row(
//                modifier = Modifier
//                    .padding(start = 10.dp, end = 10.dp)
//                    .fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Icon(
//                    modifier = Modifier.clickable {
//                        onIconClicked
//                    },
//                    imageVector = Icons.Outlined.Home, contentDescription = "", tint = colorResource(
//                        id = R.color.white
//                    )
//                )
//                Icon(
//                    imageVector = Icons.Outlined.Home, contentDescription = "", tint = colorResource(
//                        id = R.color.white
//                    )
//                )
//                Icon(
//                    imageVector = Icons.Outlined.Home, contentDescription = "", tint = colorResource(
//                        id = R.color.white
//                    )
//                )
//            }
//        }
    }
}

@Composable
fun NewsTypesCard(
    onCategoryClicked: (String) -> Unit
) {

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
                    onCategoryClicked(buttonLabels[index])
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
    articles: Articles,
    onCategoryClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onCategoryClicked()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            articles.urlToImage?.let {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = articles.urlToImage,
                    contentDescription = "News Image",
                    contentScale = ContentScale.FillBounds
                )
            }
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
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    articles.author?.let {
                        Text(
                            text = articles.author,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white)
                        )
                    }

                    articles.publishedAt?.let {
                        Text(
                            text = articles.publishedAt,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white)
                        )
                    }
                }

            }
        }

    }
    Spacer(modifier = Modifier.height(15.dp))
}
