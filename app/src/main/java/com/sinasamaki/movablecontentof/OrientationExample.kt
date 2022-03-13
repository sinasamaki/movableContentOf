package com.sinasamaki.movablecontentof

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrientationExample() {
    var isRow by remember {
        mutableStateOf(true)
    }

    val boxes = remember {
        movableContentOf {
            LetterBox(letter = 'A')
            LetterBox(letter = 'B')
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isRow = !isRow }) {
            Text(text = "Switch")
        }
        if (isRow) {
            Row(
                Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                boxes()
            }
        } else {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                boxes()
            }
        }
    }
}

// WITHOUT MOVABLE CONTENT OF

//@Composable
//fun OrientationExample() {
//    var isRow by remember {
//        mutableStateOf(true)
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(onClick = { isRow = !isRow }) {
//            Text(text = "Switch")
//        }
//        if (isRow) {
//            Row(
//                Modifier.weight(1f),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                LetterBox(letter = 'A')
//                LetterBox(letter = 'B')
//            }
//        } else {
//            Column(
//                Modifier.weight(1f),
//                verticalArrangement = Arrangement.Center
//            ) {
//                LetterBox(letter = 'A')
//                LetterBox(letter = 'B')
//            }
//        }
//    }
//}

@Composable
fun LetterBox(
    modifier: Modifier = Modifier,
    letter: Char
) {

    SideEffect {
        Log.d("RECOMPOSE", "Recomposing $letter . . . ")
    }

    Box(
        modifier = modifier
            .padding(5.dp)
            .size(150.dp)
            .background(
                if (letter == 'A') Color(0xFFD13F3F) else Color(0xFF3F7BD6),
                shape = RoundedCornerShape(30.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            style = TextStyle(color = Color.White, fontSize = 80.sp, )
        )
    }

}