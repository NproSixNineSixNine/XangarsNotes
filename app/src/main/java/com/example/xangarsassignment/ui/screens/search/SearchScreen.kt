@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.xangarsassignment.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.xangarsassignment.R
import com.example.xangarsassignment.ui.component.NotesListComponent
import com.example.xangarsassignment.ui.component.PaperIconButton
import com.example.xangarsassignment.ui.screens.Destinations
import com.example.xangarsassignment.ui.screens.home.HomeUiState
import com.example.xangarsassignment.ui.screens.home.HomeViewModel
import org.koin.androidx.compose.get

@Composable
fun SearchScreen(navController: NavController) {
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current
    val focus = remember { mutableStateOf(true) }
    val homeViewModel  = get<HomeViewModel>()
    val description = rememberSaveable {
        mutableStateOf("")
    }
    val scrollState = rememberLazyListState()

    LaunchedEffect(navController) {
        focus.value = true
        inputService?.showSoftwareKeyboard()
        focusRequester.requestFocus()
    }
    val resultList =
        homeViewModel.getAllNotesByDescription(description.value).collectAsState(initial = listOf())

    Scaffold(topBar = {
        TextField(
            value = description.value,
            singleLine = true,
            onValueChange = {
                description.value = it
            },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            leadingIcon = {
                PaperIconButton(id = R.drawable.ic_back) {
                    navController.navigateUp()
                }
            },
            trailingIcon = {
                if (description.value.isNotEmpty()) {
                    PaperIconButton(id = R.drawable.ic_x) {
                        description.value = ""
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions { inputService?.hideSoftwareKeyboard() },

            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focus.value != focusState.isFocused) {
                        focus.value = focusState.isFocused
                        if (!focusState.isFocused && focus.value) {
                            inputService?.hideSoftwareKeyboard()
                            navController.navigateUp()
                        }
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background
            )
        )
    }) { paddingValues ->
        NotesListComponent(
            MaterialTheme.colorScheme.onPrimary,
            HomeUiState(resultList.value, false),
            scrollState,
            paddingValues) {
            focus.value = false
            homeViewModel.saveCurrentNote(it.note.noteId)
            navController.navigate(Destinations.NOTE_ROUTE)
        }
    }
}