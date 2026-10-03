package com.example.alifvia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alifvia.ui.screen.DaftarProdukScreen
import com.example.alifvia.ui.screen.DetailProductScreen
import com.example.alifvia.ui.screen.HubungiKamiScreen
import com.example.alifvia.ui.theme.JualanTheme
import com.example.alifvia.ui.viewmodel.ProductViewModel

class HomeActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        enableEdgeToEdge()

        setContent {

            JualanTheme {

                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    val productViewModel:
            ProductViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "daftar_produk"
    ) {

        composable(
            route = "daftar_produk"
        ) {

            DaftarProdukScreen(
                navController = navController,
                viewModel = productViewModel,
                onContactUsClick = {
                    navController.navigate(
                        "hubungi_kami"
                    )
                }
            )
        }

        composable(
            route = "detail/{productId}",
            arguments = listOf(
                navArgument(
                    "productId"
                ) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val productId =
                backStackEntry.arguments
                    ?.getInt(
                        "productId"
                    )
                    ?: 0

            DetailProductScreen(
                productId = productId,
                navController = navController,
                viewModel = productViewModel
            )
        }

        composable(
            route = "hubungi_kami"
        ) {

            HubungiKamiScreen(
                navController = navController
            )
        }
    }
}