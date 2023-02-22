package com.example.jettipcalculator.widgets


import android.graphics.drawable.Icon
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val iconSizeModifier : Modifier = Modifier.size(40.dp)
@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick : () -> Unit,
    tint : Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.background,
    elevation : Dp = 4.dp){
        
    Card(modifier = Modifier
        .clip(shape = CircleShape)
        .padding(4.dp)
        .clickable { onClick.invoke() }
        .then(iconSizeModifier),
        backgroundColor = backgroundColor,
        shape = CircleShape,
        elevation = elevation) {
            Icon(imageVector = imageVector, contentDescription = "Icon",
                tint = tint)

    }

}