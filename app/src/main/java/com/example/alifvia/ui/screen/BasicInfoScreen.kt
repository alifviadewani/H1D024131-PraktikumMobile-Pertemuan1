package com.example.alifvia.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alifvia.R

@Composable
fun BasicInfoScreen(
    onNavigateToContact: () -> Unit
) {
    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.mipmap.ic_launcher_foreground
                    ),
                    contentDescription = "Logo Aplikasi",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Tentang Jualan",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {

                Text(
                    text = "Misi Kami:",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Menawarkan UMKM Lokal",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {
                    onNavigateToContact()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Hubungi Kami",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}