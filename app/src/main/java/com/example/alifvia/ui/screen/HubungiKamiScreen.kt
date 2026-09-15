package com.example.alifvia.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alifvia.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubungiKamiScreen(
    navController: NavController?
) {

    var emailText by remember {
        mutableStateOf("")
    }

    var messageText by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Hubungi Kami")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.back_icon
                            ),
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hubungi Kami",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(
                value = emailText,
                onValueChange = {
                    emailText = it
                },
                label = {
                    Text("Email Anda")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(
                value = messageText,
                onValueChange = {
                    messageText = it
                },
                label = {
                    Text("Pesan")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Pesan Terkirim"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Text(
                        text = "Kirim Pesan"
                    )
                }
            }
        }
    }
}