package com.example.catify.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.catify.R
import com.example.catify.model.CatUIModel
import com.example.catify.model.ImageSize
import com.example.catify.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers


@Composable
fun CatScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel
) {
    val catPagingData = vm.homeScreenState.collectAsLazyPagingItems()

    CatsList(
        modifier = modifier,
        lazyPagingItems = catPagingData
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatsList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<CatUIModel>
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(
            count = lazyPagingItems.itemCount)
        { index ->
            val catItem = lazyPagingItems[index]
            if (catItem != null) {
                Cat(cat = catItem)
            }
        }

        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(
                        span = StaggeredGridItemSpan.FullLine,
                        contentType = Unit
                    ) {
                        ProgressBar()
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = lazyPagingItems.loadState.refresh as LoadState.Error
                    item(
                        span = StaggeredGridItemSpan.FullLine
                    ) {
                        ErrorMessage(
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item(
                        span = StaggeredGridItemSpan.FullLine,
                        contentType = Unit
                    ) {
                        ProgressBar()
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = lazyPagingItems.loadState.append as LoadState.Error
                    item(
                        span = StaggeredGridItemSpan.FullLine
                    ) {
                        ErrorMessage(
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }

    }
}

@Composable
private fun ProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(vertical = 20.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = message
        )
        Button(
            modifier = Modifier.padding(vertical = 10.dp),
            onClick = onClickRetry
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun Cat(
    modifier: Modifier = Modifier,
    cat: CatUIModel
) {
    Column(
        modifier = modifier
            .aspectRatio(
                cat.size.width.toFloat() / cat.size.height.toFloat()
            )
            .border(
                width = 1.dp,
                color = Color.LightGray
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current

        val request: ImageRequest = ImageRequest.Builder(context)
            .data(cat.imageUrl)
            .size(
                width = cat.size.width,
                height = cat.size.height
            )
            .crossfade(durationMillis = 500)
            .diskCachePolicy(CachePolicy.ENABLED)
            .fetcherDispatcher(Dispatchers.IO)
            .build()
        context.imageLoader.enqueue(request)


        AsyncImage(
            model = request,
            contentDescription = "Kitty Image",
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Fit,
            modifier = Modifier
        )
    }
}