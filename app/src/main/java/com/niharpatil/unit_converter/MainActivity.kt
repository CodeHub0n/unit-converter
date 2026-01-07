package com.niharpatil.unit_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niharpatil.unit_converter.ui.theme.UNITConverterTheme

fun getFactor(unit: String): Double {
    return when (unit) {
        "Millimeter" -> 0.001
        "Centimeter" -> 0.01
        "Meter" -> 1.0
        "Feet" -> 0.3048
        else -> 1.0
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UNITConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    UnitConverter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

enum class ActiveField {
    FROM,
    TO
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {

    // -------- STATE --------

    var fromValue by remember { mutableStateOf("") }
    var toValue by remember { mutableStateOf("") }

    var fromUnit by remember { mutableStateOf("Meter") }
    var toUnit by remember { mutableStateOf("Centimeter") }

    var activeField by remember { mutableStateOf(ActiveField.FROM) }

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    fun convert() {
        if (activeField == ActiveField.FROM) {
            val value = fromValue.toDoubleOrNull() ?: return
            val result = value * getFactor(fromUnit) / getFactor(toUnit)
            toValue = "%.2f".format(result)
        } else {
            val value = toValue.toDoubleOrNull() ?: return
            val result = value * getFactor(toUnit) / getFactor(fromUnit)
            fromValue = "%.2f".format(result)
        }
    }

    // -------- UI --------

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column {

                // -------- FROM --------
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            value = fromValue,
                            onValueChange = {
                                activeField = ActiveField.FROM
                                fromValue = it
                                convert()
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            label = { Text("From") }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = fromUnit)

                        Box(
                            modifier = Modifier
                                .clickable { isFromExpanded = true }
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select unit"
                            )
                        }
                    }
                }

                fun swapUnits() {
                    val tempUnit = fromUnit
                    fromUnit = toUnit
                    toUnit = tempUnit

                    val tempValue = fromValue
                    fromValue = toValue
                    toValue = tempValue

                    activeField =
                        if (activeField == ActiveField.FROM) ActiveField.TO
                        else ActiveField.FROM
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalDivider(
                        thickness = 3.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = "Swap units",
                        modifier = Modifier.clickable { swapUnits() }
                    )
                }


                // -------- TO --------
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            value = toValue,
                            onValueChange = {
                                activeField = ActiveField.TO
                                toValue = it
                                convert()
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            label = { Text("To") }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = toUnit)

                        Box(
                            modifier = Modifier
                                .clickable { isToExpanded = true }
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select unit"
                            )
                        }
                    }
                }
            }
        }

        UnitDropdown(
            expanded = isFromExpanded,
            onDismiss = { isFromExpanded = false },
            onUnitSelected = {
                fromUnit = it
                convert()
            }
        )

        UnitDropdown(
            expanded = isToExpanded,
            onDismiss = { isToExpanded = false },
            onUnitSelected = {
                toUnit = it
                convert()
            }
        )
    }
}
@Composable
fun UnitDropdown(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onUnitSelected: (String) -> Unit ) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss ) {
        listOf("Millimeter", "Centimeter", "Meter", "Feet")
            .forEach { unit ->
            DropdownMenuItem(
            text = { Text(unit) },
            onClick = {
                onUnitSelected(unit)
                onDismiss()
            }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UNITConverterTheme {
        UnitConverter()
    }
}