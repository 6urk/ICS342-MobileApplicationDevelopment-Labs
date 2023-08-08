
package com.ics342.labs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ics342.labs.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "weather") {
                    composable("weather") {
                        WeatherScreen(navController)
                    }
                    composable("forecast") {
                        ForecastScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.city_name),
            style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier.padding(bottom = 20.dp).align(Alignment.CenterHorizontally)

        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 70.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.feels_like),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Image(
                painter = painterResource(R.drawable.sun_icon),
                contentDescription = stringResource(R.string.weather_icon),
                modifier = Modifier.size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.low),
            style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(start = 16.dp)
        )
        Text(
            text = stringResource(R.string.high),
            style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(start = 16.dp)
        )
        Text(
            text = stringResource(R.string.humidity),
            style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(start = 16.dp)
        )
        Text(
            text = stringResource(R.string.pressure),
            style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(start = 16.dp)
        )

        Button(
            onClick = { navController.navigate("forecast") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Forecast")
        }
    }
}

@Composable
@Preview
fun MainActivityPreview() {
    MyApplicationTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "weather") {
            composable("weather") {
                WeatherScreen(navController)
            }
            composable("forecast") {
                ForecastScreen()
            }
        }
    }
}