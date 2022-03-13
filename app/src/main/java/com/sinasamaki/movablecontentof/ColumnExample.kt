package com.sinasamaki.movablecontentof

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnExample() {
    val list = remember {
        mutableStateListOf<String>().apply {
            for (i in 0..20) add("Counter ${'A' + i}")
        }
    }

    val listComposables = list.movable {
        Counter(text = it)
    }

    Column {
        Button(onClick = {
            list.removeFirstOrNull()
        }) {
            Text(text = "Remove first")
        }
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .weight(1f)
        ) {
            list.forEach {
                listComposables(it)
            }
        }
    }
}

// WITHOUT MOVABLE CONTENT OF

//@Composable
//fun ColumnExample() {
//    val list = remember {
//        mutableStateListOf<String>().apply {
//            for (i in 0..20) add("Counter ${'A' + i}")
//        }
//    }
//
//    Column {
//        Button(onClick = {
//            list.removeFirstOrNull()
//        }) {
//            Text(text = "Remove first")
//        }
//        Column(
//            modifier = Modifier
//                .verticalScroll(state = rememberScrollState())
//                .weight(1f)
//        ) {
//            list.forEach {
//                Counter(text = it)
//            }
//        }
//    }
//}

@Composable
fun Counter(text: String) {

    var counter by remember {
        mutableStateOf(0)
    }

    SideEffect {
        Log.d("RECOMPOSE", "Recomposing $text ($counter)")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Box(modifier = Modifier.weight(1f))
        Button(onClick = { counter-- }) { Text(text = "-") }
        Text(
            text = "$counter",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = { counter++ }) { Text(text = "+") }
    }
}

@Composable
fun <T> List<T>.movable(
    transform: @Composable (item: T) -> Unit
): @Composable (item: T) -> Unit {
    val composedItems = remember(this) { mutableMapOf<T, @Composable () -> Unit>() }
    return { item: T ->
        composedItems.getOrPut(item) {
            movableContentOf { transform(item) }
        }.invoke()
    }
}
