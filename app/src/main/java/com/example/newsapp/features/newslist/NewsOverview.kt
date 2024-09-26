package com.example.newsapp.features.newslist


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.data.modals.Articles

@Composable
fun NewsOverviewSreeen(
    viewModal: NewsViewModal,
    articleId: Int
) {

    val newsList by viewModal.newsList.collectAsState()


        LaunchedEffect(key1 = Unit) {
            viewModal.getNewsList()
        }

        newsList.getOrNull(articleId)?.let { article ->
            NewsItem(articles = article)
        }

}

@Composable
fun NewsItem(articles: Articles) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        articles.urlToImage?.let {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth(),
                model = articles.urlToImage,
                contentDescription = "News Image"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(15.dp)),
        ) {
            articles.publishedAt?.let {
                Text(
                    text = articles.publishedAt,
                    fontSize = 10.sp
                )
            }
            articles.title?.let {
                Text(
                    text = articles.title,
                    fontSize = 10.sp
                )
            }
            articles.author?.let {
                    Text(
                        text = "Published by  ${articles.author}",
                        fontSize = 10.sp
                    )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        articles.content?.let {
            Text(
                text = articles.content,
                fontSize = 10.sp
            )
        }
    }
}