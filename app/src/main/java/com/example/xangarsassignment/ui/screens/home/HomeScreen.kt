@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.xangarsassignment.ui.screens.home


import com.example.xangarsassignment.models.NoteWithDoodleAndImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.xangarsassignment.ui.screens.Destinations
import org.koin.androidx.compose.get
import com.example.xangarsassignment.R
import com.example.xangarsassignment.ui.component.NotesListComponent
import com.example.xangarsassignment.ui.component.PaperIconButton
import io.ak1.rangvikalp.colorArray

val fabShape = RoundedCornerShape(30)

const val darkerToneIndex = 7
const val lighterToneIndex = 1

@Composable
fun HomeScreen(isDark: Boolean, scrollState: LazyListState, navigateTo: (String) -> Unit) {
    val homeViewModel = get<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(isDark, uiState, scrollState, {
        homeViewModel.saveCurrentNote(it.note.noteId)
        navigateTo(Destinations.NOTE_ROUTE)
    }, {
        homeViewModel.saveCurrentNote()
        navigateTo(Destinations.NOTE_ROUTE)
    }, navigateTo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isDark: Boolean,
    uiState: HomeUiState,
    scrollState: LazyListState,
    saveNote: (NoteWithDoodleAndImage) -> Unit,
    openNewNote: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val randomInt = get<Int>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val headerColor = colorArray[randomInt][if (isDark) lighterToneIndex else darkerToneIndex]
    val tintColor = colorArray[randomInt][if (isDark) darkerToneIndex else lighterToneIndex]
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displaySmall,
                    color = headerColor,
                    fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 16.dp)
                        .fillMaxWidth()
                )
            }, actions = {
                PaperIconButton(
                    id = R.drawable.ic_search,
                ) { navigateTo(Destinations.SEARCH_ROUTE) }
            }, scrollBehavior = scrollBehavior)
        },
        content = { paddingValues ->
            NotesListComponent(
                headerColor,
                uiState,
                scrollState,
                paddingValues,
                saveNote
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = openNewNote,
                shape = fabShape,
                containerColor = headerColor
            ) {
                Icon(imageVector = Icons.Rounded.Add,
                    tint = Color.White,
                    contentDescription = "")
            }
        })
}

