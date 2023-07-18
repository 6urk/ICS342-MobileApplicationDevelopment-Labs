package com.ics342.labs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ForecastScreen(forecastItems: List<Person>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(forecastItems) { forecast ->
                ForecastRow(forecast)
            }
        }
    }
}

@Composable
fun ForecastRow(forecast: Person) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "ID: ${forecast.id}")
        Text(text = "Given Name: ${forecast.givenName}")
        Text(text = "Family Name: ${forecast.familyName}")
        Text(text = "Age: ${forecast.age}")
    }
}