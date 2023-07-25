package com.yzh.demoapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yzh.demoapp.mockdata.randomString

data class TestData(
    val name: String,
    val type: Boolean = name.length % 2 == 0,
)

@Preview
@Composable
fun Column_Preview() {
    val list = List(20) {
        TestData(randomString())
    }
    MaterialTheme {
        LazyColumnTest(list)
    }
}

@Composable
fun LazyColumnTest(
    list: List<TestData>
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(list, contentType = { it.name.length % 3 }) {
            Card(
                shape = RoundedCornerShape(
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                ),
                elevation = cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                when (it.name.length % 3) {
                    1 -> Text(
                        text = it.name,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                            .padding(20.dp)
                    )
                    2 -> Text(
                        text = it.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                            .padding(10.dp)
                    )
                    else -> Text(
                        text = it.name,
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.Red.copy(alpha = 0.2f))
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}
