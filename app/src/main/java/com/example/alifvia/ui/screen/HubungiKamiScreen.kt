package com.example.alifvia.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alifvia.R
import com.example.alifvia.ui.theme.JualanTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubungiKamiScreen(
    navController: NavController?
) {

    var email by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var problemType by rememberSaveable {
        mutableStateOf("Pilih Tipe Pesan")
    }

    var isAgreed by rememberSaveable {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val isEmailValid =
        email.contains("@") &&
                email.isNotBlank()

    val isMessageValid =
        message.length >= 10

    val isFormValid =
        isEmailValid &&
                isMessageValid &&
                isAgreed &&
                problemType != "Pilih Tipe Pesan"

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember {
        androidx.compose.material3.SnackbarHostState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hubungi Kami"
                    )
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
        }
    ) { paddingValues ->

        StatelessFormHubungiKami(
            modifier = Modifier.padding(
                paddingValues
            ),
            email = email,
            onEmailChange = {
                email = it
            },
            message = message,
            onMessageChange = {
                message = it
            },
            problemType = problemType,
            onProblemTypeChange = {
                problemType = it
            },
            isAgreed = isAgreed,
            onAgreedChange = {
                isAgreed = it
            },
            imageUri = imageUri,
            onImagePicked = {
                imageUri = it
            },
            isEmailValid = isEmailValid,
            isMessageValid = isMessageValid,
            isFormValid = isFormValid,
            onSubmit = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Pesan berhasil dikirim"
                    )
                }
            }
        )
    }
}

@Composable
fun StatelessFormHubungiKami(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    problemType: String,
    onProblemTypeChange: (String) -> Unit,
    isAgreed: Boolean,
    onAgreedChange: (Boolean) -> Unit,
    imageUri: Uri?,
    onImagePicked: (Uri?) -> Unit,
    isEmailValid: Boolean,
    isMessageValid: Boolean,
    isFormValid: Boolean,
    onSubmit: () -> Unit
) {

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            onImagePicked(uri)
        }

    var expanded by remember {
        mutableStateOf(false)
    }

    val options = listOf(
        "Pertanyaan",
        "Keluhan",
        "Saran"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hubungi Kami",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(
                Alignment.Start
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = {
                Text(
                    text = "Email Anda"
                )
            },
            isError =
                email.isNotEmpty() &&
                        !isEmailValid,
            supportingText = {
                if (
                    email.isNotEmpty() &&
                    !isEmailValid
                ) {
                    Text(
                        text = "Format email tidak valid"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = problemType,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = "Tipe Pesan"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                options.forEach { option ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option
                            )
                        },
                        onClick = {
                            onProblemTypeChange(
                                option
                            )

                            expanded = false
                        }
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pilih Tipe Pesan"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            label = {
                Text(
                    text = "Pesan"
                )
            },
            isError =
                message.isNotEmpty() &&
                        !isMessageValid,
            supportingText = {
                if (
                    message.isNotEmpty() &&
                    !isMessageValid
                ) {
                    Text(
                        text =
                            "Pesan minimal 10 karakter"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedButton(
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts
                            .PickVisualMedia
                            .ImageOnly
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Upload"
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "Unggah Bukti"
            )
        }

        if (imageUri != null) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme
                            .surfaceVariant
                )
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "File"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            "File terpilih: " +
                                    imageUri.lastPathSegment
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isAgreed,
                onCheckedChange = onAgreedChange
            )

            Text(
                text =
                    "Saya menyetujui syarat dan ketentuan"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onSubmit,
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                horizontalArrangement =
                    Arrangement.Center,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(
                        id = R.drawable.send_icon
                    ),
                    contentDescription = "Kirim"
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Kirim Pesan"
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun HubungiKamiScreenPreview() {
    JualanTheme {
        HubungiKamiScreen(
            navController = null
        )
    }
}