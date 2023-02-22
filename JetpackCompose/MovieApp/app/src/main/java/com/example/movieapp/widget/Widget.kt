package com.example.movieapp.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies

@Preview
@Composable
fun MovieCard(movie : Movie = getMovies()[0], onItemClick : (Movie) -> Unit = {}){

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clickable { onItemClick(movie) },
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(modifier = Modifier.padding(9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier
                .size(150.dp)
                .padding(12.dp),
                shape = RectangleShape,
                elevation = 6.dp,
            ) {
                
                Image(painter = rememberAsyncImagePainter(model = movie.images[0]),
                    contentDescription = "Poster",
                    contentScale = ContentScale.Crop)
//                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Account Icon")
            }
          Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 8.dp)) {

              // title
              Text(buildAnnotatedString {
                  withStyle( style = SpanStyle( color = Color.DarkGray,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold)){
                      append(movie.title)
                  }
              })


              // director
              Text(buildAnnotatedString {
                  withStyle( style = SpanStyle(color = Color.DarkGray,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold
                  )) {
                      append("director: ")
                  }

                  withStyle( style = SpanStyle( color = Color.DarkGray,
                      fontSize = 18.sp
                  )){
                      append(movie.director)
                  }
              })


              // year
              Text(buildAnnotatedString {
                  withStyle( style = SpanStyle(color = Color.DarkGray,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold
                  )) {
                      append("year : ")
                  }

                  withStyle( style = SpanStyle( color = Color.DarkGray,
                      fontSize = 18.sp
                  )){
                      append(movie.year)
                  }
              })

              // rating
              Text(buildAnnotatedString {
                  withStyle( style = SpanStyle(color = Color.DarkGray,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold
                  )) {
                      append("rating : ")
                  }

                  withStyle( style = SpanStyle( color = Color.DarkGray,
                      fontSize = 18.sp
                  )){
                      append(movie.rating)
                  }
              })


                AnimatedVisibility(visible = expanded) {
                    Column(verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(8.dp)) {

                        Text(buildAnnotatedString {
                            withStyle( style = SpanStyle(color = Color.DarkGray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )) {
                                append("actor : ")
                            }

                            withStyle( style = SpanStyle( color = Color.DarkGray,
                                fontSize = 18.sp
                            )){
                                append(movie.actors)
                            }
                        })

                        Text(buildAnnotatedString {
                            withStyle( style = SpanStyle(color = Color.DarkGray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )) {
                                append("gener : ")
                            }

                            withStyle( style = SpanStyle( color = Color.DarkGray,
                                fontSize = 18.sp
                            )){
                                append(movie.genre)
                            }
                            })
                        
                        Text( buildAnnotatedString {
                            withStyle( style = SpanStyle(color = Color.DarkGray,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                            )){
                                append("plot: ")
                            }
                            withStyle( style = SpanStyle( color = Color.DarkGray,
                                            fontSize = 13.sp
                                          )){
                                append(movie.plot)
                            }
                        })
                    }
                }


              Icon(imageVector = if(expanded)Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                  contentDescription = "drop down arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        }
                    , tint = Color.DarkGray,
                    )
          }
        }
    }
}