package com.example.weatherforcast.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.weatherforcast.R
import com.example.weatherforcast.components.InfoBox
import com.example.weatherforcast.data.DataOrExcaption
import com.example.weatherforcast.model.Current
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.model.Hour
import com.example.weatherforcast.model.Weather
import com.example.weatherforcast.navigation.WeatherScreens
import com.example.weatherforcast.screen.main.ViewModel
import com.example.weatherforcast.util.DayFormater
import com.example.weatherforcast.util.formatDate
import com.example.weatherforcast.util.formatDateTime
import com.example.weatherforcast.util.getTime
import com.example.weatherforcast.widgets.WeatherAppBar
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset


@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: ViewModel = hiltViewModel(),
    city: String?
) {
    Log.d("Main Screen" , "City $city")
    Surface(modifier = Modifier) {
        Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            val WeatherData = produceState<DataOrExcaption<Weather, Boolean, Exception>>(
                initialValue = DataOrExcaption(loding = true)){
                value = viewModel.getWeatherData(city = city.toString())
            }.value

            if(WeatherData.loding == true){
                CircularProgressIndicator()
            }
            else if(WeatherData.data != null){
                MainScaffold(weather = WeatherData.data!!, navController = navController)
            }
        }
    }
}

@Composable
fun MainScaffold(weather : Weather, navController: NavController) {

   Scaffold(topBar = {
       WeatherAppBar(
           title = "${weather.location.name}, ${weather.location.region}",
           navController = navController,
           onAddActionClicked = {
                      navController.navigate(WeatherScreens.SearchScreen.name)
           },
           elevation = 5.dp,
           favorite = Favorite("${weather.location.name}",
             "${weather.location.country}")
       )
   }) {
       MainContent(data = weather)
   }
}

@Composable
fun MainContent(data: Weather) {
   
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = formatDate(data.current.last_updated_epoch),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(250.dp),

        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                
                getWeatherImage(imageUrl = data.current.condition.icon, size = 100.dp)

                Text(
                    text = data.current.temp_c.toString() + "\u2103",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold,
                )

                Text(
                    text = data.current.condition.text,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold,

                )

            }
        }

        Divider(color = Color.LightGray,
            thickness = 2.dp
        )



        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


            HourlyForcast2(data = data.forecast.forecastday[0].hour)
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                Surface(shape = RoundedCornerShape(35.dp),
                    color = Color.LightGray) {
                    Text(text = "This Week",
                        style = TextStyle(fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            Forcast(data = data)
        }
    }
 }
@Composable
fun getWeatherImage(imageUrl : String, size : Dp){
    Image(painter = rememberImagePainter("https:$imageUrl"),
        contentDescription = "Image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(size))
}

@Composable
fun Forcast(data: Weather){
    val forcastList = data.forecast.forecastday
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)){

        items(items = forcastList, itemContent = { data ->
           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.padding(4.dp)
           ) {
               Text(text = DayFormater(data.date_epoch),
                       style = TextStyle(fontSize = 14.sp,
                   fontWeight = FontWeight.SemiBold),
               color = Color.LightGray)

               getWeatherImage(imageUrl = data.day.condition.icon,60.dp)

               Text(text = "${data.day.avgtemp_c }℃ ",
                   style = TextStyle(fontSize = 14.sp,
                       fontWeight = FontWeight.Bold
               ))
           }
        })

    }
    
    OtherInformation(data = data.current)
    
}

@Composable
fun HourlyForcast(data : List<Hour> ){
    val hourList = data
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)){
        items(items = hourList, itemContent = { data ->
            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)) {

                getWeatherImage(data.condition.icon, size = 65.dp)
                Text(text = formatDateTime(data.time_epoch),
                        style = TextStyle(fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold),
                        color = Color.LightGray
                )
                Text(text = "${data.temp_c}℃",
                        style = TextStyle(fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold)
                )
            }
        })
    }
}



@Composable
fun HourlyForcast2(data : List<Hour> ){
    val hourList = data
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)){
        items(items = hourList, itemContent = { data ->
            val currTime = remember {
                mutableStateOf(LocalTime.now())
            }

            if(getTime(data.time_epoch) >= currTime.value){

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)) {

                    getWeatherImage(data.condition.icon, size = 65.dp)
                    Text(text = formatDateTime(data.time_epoch),
                        style = TextStyle(fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold),
                        color = Color.LightGray
                    )
                    Text(text = "${data.temp_c}℃",
                        style = TextStyle(fontSize = 14.sp,
                            fontWeight = FontWeight.Bold)
                    )
                }
            }
        })
    }
}




@Composable
fun OtherInformation(data : Current){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),
             shape = RoundedCornerShape(15.dp),
            elevation = 5.dp) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceAround) {
                InfoBox(
                    modifier = Modifier,
                    icon =R.drawable.thermometer,
                    value = "${data.feelslike_c}℃",
                    desc = "Feels Like"
                )

                InfoBox(
                    modifier = Modifier,
                    icon = R.drawable.force,
                    value = "${data.wind_kph} km/h",
                    desc = " ${data.wind_dir}"
                )

                InfoBox(
                    icon = R.drawable.humidity,
                    value = "${data.humidity}% ",
                    desc = "Humidity"
                )
            }

            Row(modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceAround) {
                InfoBox(
                    icon =R.drawable.ultraviolet,
                    value = "${data.uv}",
                    desc = "UV"
                )

                InfoBox(
                    icon = R.drawable.eye,
                    value = "${data.vis_km} km",
                    desc = "Visibility"
                )

                InfoBox(
                    icon = R.drawable.pressure,
                    value = "${data.pressure_in} hPa",
                    desc = "Air pressure"
                )
            }
        }
    }
}



































