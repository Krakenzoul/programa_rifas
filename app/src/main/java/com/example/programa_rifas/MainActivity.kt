package com.example.programa_rifas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.programa_rifas.navegacion.Navigation
import com.example.programa_rifas.ui.theme.RifasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RifasTheme {
                Navigation()
            }
        }
    }
}
