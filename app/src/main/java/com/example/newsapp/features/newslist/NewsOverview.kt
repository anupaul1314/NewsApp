package com.example.newsapp.features.newslist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.data.modals.Articles
import com.example.newsapp.R

@Composable
fun NewsOverviewSreeen(
    viewModal: NewsListViewModal,
    articleId: Int,
    category: String,
    onBackButtonClicked: () -> Unit
) {

    val newsList by viewModal.newsList.collectAsState()

    LaunchedEffect(key1 = category) {
            viewModal.getNewsList(category)
    }

    newsList.getOrNull(articleId)?.let { article ->
        NewsItem(articles = article,onBackButtonClicked)
    }

}

@Composable
fun NewsItem(
    articles: Articles,
    onCicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onCicked()
                    },
                imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "", tint = colorResource(
                    id = R.color.darkpink
                )
            )
            articles.author?.let {
                Text(
                    text = articles.author,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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
        articles.title?.let {
            Text(
                text = articles.title,
                fontSize = 25.sp,
                color = colorResource(id = R.color.darkpink),
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            articles.author?.let {
                Text(
                    text = "Published by  ${articles.author}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            articles.publishedAt?.let {
                Text(
                    text = articles.publishedAt,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        articles.content?.let {
            Text(
                text = articles.content.substring(0,articles.content.length-25),
                fontSize = 15.sp
            )
        }
    }
}