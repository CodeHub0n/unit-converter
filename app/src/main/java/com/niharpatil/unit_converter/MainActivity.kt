package com.niharpatil.unit_converter

import android.os.Bundle
import android.text.style.IconMarginSpan
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.test.espresso.base.Default
import com.niharpatil.unit_converter.ui.theme.UNITConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UNITConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ){ innerPadding ->
                    UnitConverter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
        Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // here all the ui elements will be stacked below each other
        Text(text ="Unit Converter")
        Spacer(modifier = Modifier. height(16.dp))
        OutlinedTextField(value ="Enter the Value", onValueChange = {// here goes what should happen when the value of outlinedtextfield changes
            })
        Spacer(modifier = Modifier. height(16.dp))
        Row {
                // here all the UI elements will be stacked next to each other
                Box {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Select")
                        Icon(Icons.Default.ArrowDropDown, contentDescription ="")
                    }
                    DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {
                        DropdownMenuItem(text = { Text(text = "Centimeter") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text(text = "Meter") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text(text = "Feet") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text(text = "Millimeter") }, onClick = { /*TODO*/ })
                    }
                    }
            Spacer(modifier = Modifier. width(16.dp))

            Box {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Select")
                        Icon(Icons.Default.ArrowDropDown, contentDescription ="")
                    }
                DropdownMenu(expanded = false, onDismissRequest = { /*TODO*/ }) {
                    DropdownMenuItem(text = { Text(text = "Centimeter") }, onClick = { /*TODO*/ })
                    DropdownMenuItem(text = { Text(text = "Meter") }, onClick = { /*TODO*/ })
                    DropdownMenuItem(text = { Text(text = "Feet") }, onClick = { /*TODO*/ })
                    DropdownMenuItem(text = { Text(text = "Millimeter") }, onClick = { /*TODO*/ })
                }
                    }

                }
        Spacer(modifier = Modifier. height(16.dp))
        Text("Result:")
    }
}



@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}