package com.example.newsapp.features.newslist

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.modals.Articles
import com.example.newsapp.features.auth.AuthActivity
import com.example.newsapp.localization.LocaleHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    newsViewModal: NewsListViewModal,
    onClicked: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var expanded by remember { mutableStateOf(false) }

    val selectedCategory by newsViewModal.selectedCategory.collectAsState()

    val newsList by newsViewModal.newsList.collectAsState()
    val filteredNewsList by newsViewModal.filteredNewsList.collectAsState()

    val searchText by newsViewModal.searchText.collectAsState()

    var isSignOut by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = selectedCategory) {
        newsViewModal.getNewsList(selectedCategory)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .padding(end = 100.dp)
                    .clickable(interactionSource = interactionSource, indication = null) {}
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Menu",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    MenuBarItem(
                        menu = "Language" ,
                        icon = R.drawable.ic_languge,
                        contentDescription = "Language",
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Dropdown Arrow"
                            )
                        },
                        onClick = {
                            expanded = !expanded
                        }
                    )
                    if (expanded) {
                        LanguageSelectionMenu(
                            onClicked = {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    val context = LocalContext.current
                    MenuBarItem(
                        modifier =Modifier,
                        menu = "SignOut",
                        icon = R.drawable.ic_logout,
                        contentDescription = "SignOut",
                        onClick = {
                            isSignOut = true
                        }
                    )
                    if (isSignOut) {
                        isSignOut = false
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(context,AuthActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }
                }
            }
        }
    )   {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "NewsApp",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.darkpink)
                        ) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_sidemenu),
                                contentDescription = "Side menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchText,
                        onValueChange = { newText ->
                            newsViewModal.updateText(newText)
                            newsViewModal.filteredNews(newText)
                        },
                        shape = CircleShape,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Icon",
                                tint = colorResource(id = R.color.darkpink)
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.search)
                            )
                        }
                    )
                    NewsTypesCard(
                        onCategoryClicked = {
                            coroutineScope.launch {
                                newsViewModal.updateSelectedCategory(it)
                            }
                        })
                    LazyColumn() {
                        items(filteredNewsList.size) {index ->
                            val article = filteredNewsList[index]
                            if (article?.urlToImage != null) {
                                NewsCardItems(
                                    articles = filteredNewsList.get(index),
                                    onCategoryClicked  = {onClicked(index)}
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun NewsTypesCard(
    onCategoryClicked: (String) -> Unit
) {
    val buttonLabels = listOf(
        R.string.science,
        R.string.sports,
        R.string.entertainment,
        R.string.technology,
        R.string.business,
        R.string.health
    )

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
            val categoryText = stringResource(id = buttonLabels[index])

            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    selectedIndex = index
                    onCategoryClicked(categoryText)
                          },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonColor,
                    contentColor = contentColor
                )
            ) {
                Text(text = stringResource(id = buttonLabels[index]))
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

@Composable
fun MenuBarItem(
    modifier: Modifier = Modifier,
    menu: String,
    icon: Int,
    contentDescription: String,
    trailingIcon: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),

    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier,
            text = menu,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
        trailingIcon?.invoke()
    }
}


@Composable
fun LanguageSelectionMenu(
    onClicked: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("Language") }
    val languageOptions = listOf("English", "Hindi")
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        languageOptions.forEach { language ->
            Text(
                text = language,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        onClicked()
                        selectedLanguage = language
                        if (selectedLanguage == "Hindi") {
                            val updatedContext = LocaleHelper.setLocale(context, "hi")
                            context.resources.updateConfiguration(
                                updatedContext?.resources?.configuration,
                                updatedContext?.resources?.displayMetrics
                            )
                            (context as? Activity)?.recreate()
                        } else {
                            val updatedContext = LocaleHelper.setLocale(context, "en")
                            context.resources.updateConfiguration(
                                updatedContext?.resources?.configuration,
                                updatedContext?.resources?.displayMetrics
                            )
                            (context as? Activity)?.recreate()
                        }

                    },
                fontSize = 16.sp
            )
        }
    }
}







