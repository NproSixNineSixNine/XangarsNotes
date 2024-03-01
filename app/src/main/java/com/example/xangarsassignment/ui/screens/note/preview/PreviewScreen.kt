
@file:OptIn(ExperimentalSnapperApi::class, ExperimentalMaterial3Api::class)

package com.example.xangarsassignment.ui.screens.note.preview

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.xangarsassignment.R
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import com.example.xangarsassignment.ui.component.PaperIconButton
import com.example.xangarsassignment.ui.screens.Destinations
import org.koin.androidx.compose.get



@Composable
fun PreviewScreen(navigateTo: (String) -> Unit, backPress: () -> Unit) {
    val lazyListState = rememberLazyListState()
    val previewViewModel  = get<PreviewViewModel>()
    val uiState by previewViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.selection) {
        lazyListState.scrollToItem(uiState.selection)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        LazyRow(
            state = lazyListState,
            flingBehavior = rememberSnapperFlingBehavior(lazyListState),
        ) {
            items(uiState.list) { item ->
                Image(
                    painter = rememberAsyncImagePainter(model = item.uri),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillMaxSize(),
                )
            }
        }
        val item = if (uiState.list.isNotEmpty()) {
            uiState.list[lazyListState.firstVisibleItemIndex]
        } else null

        TopAppBar(
            modifier = Modifier.statusBarsPadding(),
            title = { },
            navigationIcon = {
                PaperIconButton(id = R.drawable.ic_back) {
                    backPress.invoke()
                }
            },
            actions = {
                if (item?.isDoodle == true) {
                    PaperIconButton(
                        // id = if (item.isDoodle == true) R.drawable.ic_edit else R.drawable.ic_crop,
                        id = R.drawable.ic_edit
                    ) {
                        item.let {
                            if (it.isDoodle) {
                                previewViewModel.saveCurrentDoodleId(it.id)
                                navigateTo(Destinations.DOODLE_ROUTE)
                            } else {
                                Toast.makeText(
                                    context,
                                    "com.example.xangarsassignment.models.Image Editing not working",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                previewViewModel.saveCurrentImageId(it.id)
                                navigateTo(Destinations.IMAGE_ROUTE)
                            }
                        }

                    }
                }

            },
           // backgroundColor = MaterialTheme.colorScheme.background.copy(0.4f),
            //elevation = 0.dp
        )
    }

}