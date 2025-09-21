package com.example.mydomik

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseScreen(viewModel: HouseViewModel = viewModel()) {
    val context = LocalContext.current
    val preferences = remember { HousePreferences(context) }
    val wallColor by viewModel.wallColor
    val roofColor by viewModel.roofColor  // Assume similar for roof

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar
        TopAppBar(title = { Text("Мой Домик") }) {
            Button(onClick = { /* Reset */ viewModel.reset() }) {
                Text("Сбросить")
            }
        }

        // Canvas for house
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, rotationDelta ->
                            scale *= zoom
                            rotation += rotationDelta
                            offset += pan
                        }
                        detectTapGestures { offset ->
                            // Handle tap to select element
                        }
                    }
            ) {
                drawHouse(
                    wallColor = Color(android.graphics.Color.parseColor(wallColor)),
                    roofColor = Color(android.graphics.Color.parseColor(roofColor)),
                    scale = scale,
                    rotation = rotation,
                    offset = offset
                )
            }
        }

        // Bottom panel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Colors */ }) { Text("🎨") }
            Button(onClick = { /* Walls */ }) { Text("🏠") }
            Button(onClick = { /* Windows */ }) { Text("🪟") }
            Button(onClick = { /* Door */ }) { Text("🚪") }
        }
    }
}

private fun DrawScope.drawHouse(
    wallColor: Color,
    roofColor: Color,
    scale: Float,
    rotation: Float,
    offset: Offset
) {
    val center = Offset(size.width / 2, size.height / 2)
    translate(center.x + offset.x, center.y + offset.y) {
        rotate(rotation) {
            scale(scale) {
                // Draw walls (rectangle 200x150)
                drawRect(
                    color = wallColor,
                    topLeft = Offset(-100f, 0f),
                    size = Size(200f, 150f)
                )
                
                // Draw roof (triangle approx)
                drawPath(
                    color = roofColor,
                    path = /* Simple triangle path for roof */
                ) { /* Implement path */ }
                
                // Draw windows and door similarly
            }
        }
    }
}

// Note: Full implementation of drawPath and reset would be expanded
