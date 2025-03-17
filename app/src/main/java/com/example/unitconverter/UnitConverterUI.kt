package com.example.unitconverter

import android.icu.text.AlphabeticIndex.Bucket.LabelType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text
import kotlin.math.roundToInt


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConverterUI() {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutputExpanded by remember { mutableStateOf(false) }
    var inputConversionFactor by remember { mutableStateOf(1.0) }
    var outputConversionFactor by remember { mutableStateOf(1.0) }


    fun conversion(){
        val input=inputValue.toDoubleOrNull() ?:0.0
        val result = (input*inputConversionFactor/outputConversionFactor*100).roundToInt()/100.0
        outputValue=result.toString()
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Text(text="Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
            )


        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value=inputValue,
            onValueChange = {
                inputValue=it
            },
            label = {
                Text(text = "Enter Value")

            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownButton(
                label=inputUnit,
                expanded = isInputExpanded,
                onExpandedChanges = {isInputExpanded=it},
                onOptionSelected = {unit,factor->
                    inputUnit=unit
                    inputConversionFactor=factor
                    conversion()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DropdownButton(
                label=outputUnit,
                expanded = isOutputExpanded,
                onExpandedChanges = {isOutputExpanded=it},
                onOptionSelected = {unit,factor->
                    outputUnit=unit
                    outputConversionFactor=factor
                    conversion()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Result : $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
fun DropdownButton(
    label:String,
    expanded:Boolean,
    onExpandedChanges : (Boolean)-> Unit,
    onOptionSelected:(String,Double)->Unit
) {

    Box() {

        Button(
            onClick = {
                onExpandedChanges(!expanded)
            },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text=label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded=expanded,
            onDismissRequest = {
                onExpandedChanges(false)
            }
        ) {
            listOf(
                "Centimeters" to 0.01,
                "Meters" to 1.0,
                "Feet" to 0.3048,
                "Millimeters" to 0.001,
            ).forEach {
                (unit, factor)->
                DropdownMenuItem(
                    text={
                        Text(text=unit)
                    },
                    onClick = {
                        onExpandedChanges(false)
                        onOptionSelected(unit,factor)
                    }
                )

            }

            }
        }
    }