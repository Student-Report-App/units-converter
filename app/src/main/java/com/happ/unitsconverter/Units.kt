package com.happ.unitsconverter

import androidx.compose.runtime.Composable

val lengthUnits = mapOf(
    "km" to 1000.0,
    "m" to 1.0,
    "cm" to 0.01,
    "mm" to 0.001,
    "µm" to 1e-6,
    "nm" to 1e-9,
    "ft" to 0.3048,
    "in" to 0.0254,
    "yd" to 0.9144,
    "mi" to 1609.34,
    "nmi" to 1852.0
)

val volumeUnits = mapOf(
    "L" to 1.0,
    "mL" to 0.001,
    "m³" to 1000.0,
    "cm³" to 0.000001,
    "mm³" to 0.000000001
)

@Composable
fun LengthConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Length",
        units = lengthUnits,
        initialUnit = "m",
        onDismiss = onDismiss
    )
}

@Composable
fun VolumeConverterApp(onDismiss: () -> Unit) {
    ConverterApp(
        title = "Volume",
        units = volumeUnits,
        initialUnit = "L",
        onDismiss = onDismiss
    )
}
