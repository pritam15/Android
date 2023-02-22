package com.example.jettipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipcalculator.Util.calculateTip
import com.example.jettipcalculator.Util.totalPerPerson
import com.example.jettipcalculator.components.InputTextField
import com.example.jettipcalculator.ui.theme.JetTipCalculatorTheme
import com.example.jettipcalculator.ui.theme.Shapes
import com.example.jettipcalculator.widgets.RoundButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipCalculatorTheme {
                // A surface container using the 'background' color from the theme
              MyApp {
                  TipCalculator()
              }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(modifier = Modifier,
            color = MaterialTheme.colors.background) {
        content
    }
}

@Preview
@Composable
fun TipCalculator(){
    Surface(modifier = Modifier.padding(12.dp)) {
        Column(){ mainContent() }
    }
}


@Composable
fun TopHeader(totalPerPerson : Double = 0.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
//        .clip(shape = RoundedCornerShape(12.dp))
        color = Color(0xFFE9D7F7)
        ) {
            Column( modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                val text = "%.2f".format(totalPerPerson)
                Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
                Text(text = "$$text", style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
               )
            }
    }
}

@Preview
@Composable
fun mainContent(){

    billForm(){ billAmt ->
        Log.d("AMT", billAmt)
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun billForm(
    onValueChange : (String) -> Unit = {}){


    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    var noOfPerson by remember{
        mutableStateOf(1)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var sliderPositionValue by remember {
        mutableStateOf(0f)
    }

    var tipPercentState = (sliderPositionValue * 100).toInt()


    var tipAmount by remember{
        mutableStateOf(0.0)
    }

    var amtPerPerson by remember {
        mutableStateOf(0.0)
    }

   TopHeader(amtPerPerson)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(9.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {



        Column() {

            InputTextField(valueState = totalBillState,
                labelID = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    onValueChange(totalBillState.value.trim())
                    keyboardController?.hide()

            })
            
            if(validState){

                Row(modifier = Modifier.padding(horizontal = 9.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.width(120.dp))

                    Row(modifier = Modifier.padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.End) {

                        RoundButton(imageVector = Icons.Default.Remove,
                            onClick = {

                                if(noOfPerson > 1){
                                    noOfPerson--
                                    amtPerPerson = totalPerPerson(totalBillAmount = totalBillState.value.toDouble(),
                                        tipPercentage = tipPercentState, spliteBy = noOfPerson )
                                }

                            })

                        Text(text = "${noOfPerson}",
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(horizontal = 9.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold)

                        RoundButton(imageVector = Icons.Default.Add,
                            onClick = {
                                noOfPerson++
                                amtPerPerson = totalPerPerson(totalBillAmount = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentState, spliteBy = noOfPerson )
                            })

                    }
                }

                Row(modifier = Modifier.padding(horizontal = 9.dp, vertical = 14.dp)){

                    Text(text = "Tip", modifier =
                    Modifier.align(alignment = Alignment.CenterVertically),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.width(150.dp))

                    Text(text = "$$tipAmount",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                            )
                }

               Column(modifier = Modifier.fillMaxWidth()) {
                   Text(text = "$tipPercentState %",
                       modifier = Modifier
                           .align(alignment = Alignment.CenterHorizontally)
                           .padding(vertical = 14.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)

                   Slider(value = sliderPositionValue, onValueChange = { newValue ->
                       sliderPositionValue  = newValue

                       tipAmount = calculateTip(totalBillAmount = totalBillState.value.toDouble(),
                           tipPercentage = tipPercentState)

                       amtPerPerson = totalPerPerson(totalBillAmount = totalBillState.value.toDouble(),
                                        tipPercentage = tipPercentState, spliteBy = noOfPerson )





                   },
                   modifier = Modifier.padding(horizontal = 9.dp),
                   steps = 5)


               }

            }
            else{
                Box(){}
            }

        }


    }

}









//
//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        Text(text = "Hello World")
    }
}