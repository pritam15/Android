package com.example.jettrivia.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jettrivia.model.Questions
import com.example.jettrivia.model.QuestionsItem
import com.example.jettrivia.screen.QuestionViewModel
import com.example.jettrivia.util.AppColors


@Composable
fun Question(viewModel : QuestionViewModel){
    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }

    if(viewModel.data.value.loding == true){
        CircularProgressIndicator()
    }
    else{
       val question = try {
           questions?.get(questionIndex.value)
       }
       catch (e : Exception){
           null
       }
        if (question != null) {
            DisplayQuestion(question = question,questionIndex,viewModel){
                questionIndex.value++
            }
        }

    }
}


//
//@Preview
@Composable
fun DisplayQuestion(

    question : QuestionsItem,
    questionIndex : MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClick : (Int) -> Unit = {

    }

){
    val choicesState = remember(question) {
        question.incorrectAnswers.toMutableList()
    }

    val answerState = remember(question) {
            mutableStateOf<Int?>(null)
    }

    val correctAnswer = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswer.value = choicesState[it] == question.correctAnswer
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f), 0f)
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp),
        color = AppColors.mDarkPurple) {
        
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {


            if(questionIndex.value  > 3) ShowProgressBar(questionIndex.value)
            QuestionsTracker(Counter = questionIndex.value, outOf = viewModel.getTotalCount())
            DrawDivider(pathEffect = pathEffect)


            
            Column{
                Text(text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColors.mOffWhite
                )

                // Choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors.mOffDarkPurple,
                                    AppColors.mOffDarkPurple
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        )
                        .background(color = Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically){

                        RadioButton(selected = answerState.value == index,
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if(correctAnswer.value == true &&
                                    index == answerState.value){
                                    Color.Green.copy(0.2f)
                                }
                                else{
                                    Color.Red.copy(0.2f)
                                }
                            )) // End Radio button

                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                        color = if(correctAnswer.value == true
                                            && index == answerState.value){
                                            Color.Green
                                        }else if(correctAnswer.value == false
                                            &&  index == answerState.value){
                                            Color.Red
                                        }else{
                                            AppColors.mOffWhite
                                        },
                                    fontSize = 17.sp

                            )){
                                append(answerText)
                            }
                        }
                        
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }
                }
                Button(onClick = { onNextClick(questionIndex.value) },
                        modifier = Modifier
                            .padding(3.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(34.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = AppColors.mLightBlue
                            )) {
                            
                            Text(text = "Next", modifier = Modifier.padding(4.dp),
                                    color = AppColors.mOffWhite,
                                    fontSize = 17.sp)

                        }   


            }
        }
    }
}


@Preview
@Composable
fun QuestionsTracker(Counter: Int = 10,
                    outOf : Int = 100){
    
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(color = AppColors.mLightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 27.sp)){
                append("Question $Counter/")

                withStyle(style = SpanStyle(color = AppColors.mLightGray,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Light)){
                        append("$outOf")
                }
            }

        }
    }, modifier = Modifier.padding(20.dp))
    
}


@Composable
fun DrawDivider(pathEffect: PathEffect){
    
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)){
        drawLine(color = AppColors.mLightGray,
                start = Offset(x = 0f , y = 0f),
                end = Offset(size.width, y = 0f),
                pathEffect = pathEffect
                )
    }
}

@Preview
@Composable
fun ShowProgressBar(score: Int = 12){

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val profressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }
 
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors =
                listOf(AppColors.mLightPurple, AppColors.mLightPurple)
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        
        Button( contentPadding = PaddingValues(1.dp),
                 onClick = {},
                modifier = Modifier
                    .fillMaxWidth(profressFactor)
                    .background(brush = gradient),
                enabled = false,
                elevation = null,
                colors = buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent
                )

        ) {
            Text(text = (score * 10).toString(),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(23.dp))
                        .fillMaxHeight(0.87f)
                        .fillMaxWidth()
                        .padding(6.dp),
                    color = AppColors.mOffWhite,
                    textAlign = TextAlign.Center)
        }

    }
}