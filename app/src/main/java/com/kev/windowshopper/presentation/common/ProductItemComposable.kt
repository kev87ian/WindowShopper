package com.kev.windowshopper.presentation.common

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.kev.windowshopper.domain.model.Product

@Composable
fun ProductItemComposable(
    product: Product
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(300.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(product.productImageLink)
                        .build(),
                    contentDescription = "Product's Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )

                IconButton(onClick = {
                    Toast.makeText(context, "Clicked favorite", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Button"
                    )
                }
            }

            Text(
                text = product.productName,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                maxLines = 4,
                overflow = TextOverflow.Visible
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBarComposable(ratingValue = product.productRating)
                    Text(
                        text = " | ".plus(product.totalReviews).plus(" review(s)"),
                        fontSize = 14.sp
                    )
                }

           /*     Row {
                    Text(
                        text = product.productPrice,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(end = 8.dp)
                    )
//
                }*/
            }
        }
    }
}

@Composable
fun RatingBarComposable(ratingValue: Float) {
    var rating: Float by remember { mutableStateOf(ratingValue) }

    RatingBar(
        value = rating,
        style = RatingBarStyle.Fill(),
        onValueChange = {
            rating = it
        },
        spaceBetween = 1.5.dp,
        size = 16.dp,
        onRatingChanged = {
            Log.d("TAG", "onRatingChanged: $it")
        }

    )
}
