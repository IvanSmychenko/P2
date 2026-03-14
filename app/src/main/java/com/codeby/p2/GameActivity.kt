package com.codeby.p2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeby.p2.ui.theme.P2Theme
import kotlin.math.roundToInt
import kotlin.random.Random

const val WIN_SCORES = 15 // порог очков для "победы"

class GameActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rnd = Random(435) // для случайного положения кактуса
        val userName = intent.getStringExtra("user") // имя пользователя из Интента
        setContent {
            P2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Game(userName!!, rnd)  // функция Игры
                }
            }
        }
    }
}

@Composable
fun Game(name: String, rnd: Random) {
    val offsetX = remember { mutableStateOf(0f) }  // сдвиг Дино
    val dinoWidthDP = 50.dp  // размер Дино
    val dinoWidth = with(LocalDensity.current) { dinoWidthDP.toPx() } // размер Дино в пикселях
    val cactusWidthDP = 30.dp  // резмер кактуса
    val cactusWidth =
        with(LocalDensity.current) { cactusWidthDP.toPx() }  // размер кактуса в пикселях
    val scores = remember { // объект-состояния с очками пользователя
        mutableStateOf(-1)
    }
    val size = remember {  // размер игрового поля
        mutableStateOf(IntSize.Zero)
    }

    val offsetXCactus = remember { mutableStateOf(size.value.width / 2) } // сдвиг кактуса

    val direction_right = remember {  // направление движения Дино (true - если направо)
        mutableStateOf(true)
    }

    //контейнер-столбец (аналог LinearLayout в вертикальной ориентации)
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hello, ${name}", fontSize = 32.sp) // виджет с текстом приветсвия
        Spacer(modifier = Modifier.padding(16.dp)) // отступ
        Text(text = "scores: ${scores.value}")  // виджет с набранными очками
        if (scores.value < WIN_SCORES) // если очки не достигли порога победы - играем
            Box(  // виджет-контейнер
                Modifier
                    .fillMaxSize()
                    .onSizeChanged { size.value = it; offsetXCactus.value = it.width / 2 },
                contentAlignment = Alignment.BottomStart
            ) {
                // виджет с картинкой Дино
                Image(painter = if (direction_right.value) painterResource(id = R.drawable.ic_dino) else painterResource(
                    id = R.drawable.ic_dino_left
                ), contentDescription = "dino",
                    modifier = Modifier
                        .pointerInput(Unit) {  // здесь обрабатывается перетаскивание Дино
                            detectDragGestures(onDragEnd = {
                                offsetX.value = 0f
                            }) { change, dragAmount ->
                                val it = dragAmount.x
                                offsetX.value += it
                                if (offsetX.value < 0) offsetX.value = 0f;
                                if (offsetX.value > size.value.width - dinoWidth) offsetX.value =
                                    size.value.width - dinoWidth
                                direction_right.value = it > 0
                                change.consumeAllChanges()
                            }
                        }
                        .size(dinoWidthDP)
                        .offset { IntOffset(offsetX.value.roundToInt(), 0) })
                val dinoCenter = offsetX.value + dinoWidth / 2
                // проверка на совпадение координат Дино и кактуса и увеличение очков
                if (dinoCenter > offsetXCactus.value && dinoCenter < offsetXCactus.value + cactusWidth) {
                    scores.value += 1
                    val r = size.value.width - cactusWidth.roundToInt()
                    // новое положение кактуса
                    if (r > 0) offsetXCactus.value = rnd.nextInt(0, r)
                } else {
                    // виджет с картинкой кактуса
                    Image(painter = painterResource(id = R.drawable.ic_cactus),
                        contentDescription = "cactus",
                        modifier = Modifier
                            .offset { IntOffset(offsetXCactus.value, 0) }
                            .size(cactusWidthDP))
                }
            } else { // если очки превысили порог победы, то игра заканчивается
            Text(text = "Game is over", fontSize = 32.sp, color = Color.DarkGray)
            val context = LocalContext.current
            // контейнер для кнопки внизу экрана
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = { // метод-обработчик кнопки ОК
                    val intent = Intent(context, WinActivity::class.java)
                    intent.putExtra("scores", scores.value)
                    intent.putExtra("user", name)
                    context.startActivity(intent)
                },
                    modifier = Modifier
                        .padding(bottom = 32.dp) // отступ 32dp от низа
                ) {
                    Text(text = "OK")
                }

            }

        }
    }
}


@Preview(showBackground = true) // функция ниже позвоялет разработчику увидеть как будет выглядеть UI
@Composable
fun DefaultPreview() {
    P2Theme {
        Game("Android", Random(333))
    }
}