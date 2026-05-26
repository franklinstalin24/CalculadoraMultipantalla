package com.example.calculadoramultipantalla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() { //Cambié AppCompatActivity por ComponentActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Cambié setContentView por setContent
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun InputScreen(navController: NavController) { // Cambié el nombre de la función a InputScreen y agregué NavController como parámetro
    // Variables de estado para los campos de entrada
    var nombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

// Interfaz de usuario para ingresar datos
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        OutlinedTextField(
            value = nombre, onValueChange = { nombre = it },
            label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth() // Agregué un campo de texto para el nombre
        )
        Spacer(modifier = Modifier.height(8.dp)) // Agregué un espacio entre los campos de texto
        OutlinedTextField(
            value = peso, onValueChange = { peso = it },
            label = { Text("Peso (kg)") }, modifier = Modifier.fillMaxWidth() // Agregué un campo de texto para el peso
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = altura, onValueChange = { altura = it },
            label = { Text("Altura (m)") }, modifier = Modifier.fillMaxWidth()  // Agregué un campo de texto para la altura
        )
        Spacer(modifier = Modifier.height(16.dp)) // Agregué un espacio antes del botón

        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Calcular") }  //Boton para calcular el IMC, aún sin funcionalidad
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MaterialTheme {
        AppNavigation()
    }
}
