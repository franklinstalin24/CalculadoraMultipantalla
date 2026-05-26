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
fun AppNavigation() { // Función para manejar la navegación entre pantallas
    val navController = rememberNavController() // Creé un NavController para manejar la navegación entre pantallas
    NavHost(navController = navController, startDestination = "input_screen") { // Definí las rutas para cada pantalla
        composable("input_screen") { InputScreen(navController) }
        composable(
            route = "result_screen/{nombre}/{imc}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType }, // Agregué un argumento para el nombre
                navArgument("imc") { type = NavType.FloatType } // Agregué un argumento para el IMC
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "" // Obtuve el nombre desde los argumentos de la ruta
            val imc = backStackEntry.arguments?.getFloat("imc") ?: 0f // Obtuve el IMC desde los argumentos de la ruta
            ResultScreen(navController, nombre, imc // Pasé el NavController, el nombre y el IMC a la pantalla de resultados
            )
        }
    }
}

@Composable
fun InputScreen(navController: NavController) { // Cambié el nombre de la función a InputScreen y agregué NavController como parámetro
    // Variables de estado para los campos de entrada
    var nombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) } // Variable de estado para mostrar el mensaje de error

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

        // Botón para calcular el IMC
        Button(onClick = {
            val p = peso.toFloatOrNull()
            val a = altura.toFloatOrNull()
            if (p != null && a != null && p > 0 && a > 0) { // Validé que el peso y la altura sean números válidos y mayores que cero
                val imc = p / (a * a) // Calculé el IMC usando la fórmula: peso / (altura * altura)
                val encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8.toString())
                navController.navigate("result_screen/$encodedNombre/$imc") // Navegué a la pantalla de resultados pasando el nombre y el IMC como argumentos en la ruta

            } else { showError = true }
        }, modifier = Modifier.fillMaxWidth()) { Text("Calcular") }

        if (showError) {
            Text("Por favor, ingresa valores válidos", color = Color.Red)
        }
    }
}


@Composable
fun ResultScreen(navController: NavController, nombre: String, imc: Float) { // Cambié el nombre de la función a ResultScreen y agregué NavController, nombre e imc como parámetros
    // Lógica para determinar la categoría del IMC y el color correspondiente
    val (categoria, color) = when {
        imc < 18.5 -> "Bajo peso" to Color.Red
        imc < 25.0 -> "Peso normal" to Color.Green
        imc < 30.0 -> "Sobrepeso" to Color(0xFFFFA500)
        else -> "Obesidad" to Color.Red
    }
    // Interfaz de usuario para mostrar el resultado del IMC
    Column(modifier = Modifier.padding(16.dp)) { // Interfaz de usuario para mostrar el resultado del IMC
        Text("Hola $nombre, tu resultado es:") // Mostré un mensaje de bienvenida con el nombre del usuario
        Text("IMC: ${"%.1f".format(imc)}", style = MaterialTheme.typography.headlineMedium)
        Text(text = categoria, color = color, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Volver") } // Agregué un botón para volver a la pantalla de entrada
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MaterialTheme {
        AppNavigation()
    }
}
