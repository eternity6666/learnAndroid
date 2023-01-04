package com.yzh.demoapp.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yzh.demoapp.R

@Composable
fun CreatorHomePageListJustWatchView() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        border = BorderStroke(4.dp, Color.White.copy(alpha = 0.3f)),
        elevation = 4.dp
    ) {
        Text(
            text = stringResource(id = R.string.creator_just_watched),
            letterSpacing = 2.sp,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .offset(y = (-1.2f).dp)
        )
    }
}

@Preview
@Composable
fun Preview_CreatorHomePageListJustWatchView() {
    CreatorHomePageListJustWatchView()
}