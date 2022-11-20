package dev.vaibhav.canvasCompose.ui.screens.tictactoe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.vaibhav.canvasCompose.ui.screens.tictactoe.Game.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


data class TicTacToeSpecs(
    val moveWidth: Dp,
    val crossColor: Color,
    val roundColor: Color,
    val dividerWidth: Float,
    val dividerColor: Color
)

@Preview
@Composable
fun TicTacToePreview() {
    var gameState by remember {
        mutableStateOf(
            arrayOf(
                charArrayOf('E', 'E', 'E'),
                charArrayOf('E', 'E', 'E'),
                charArrayOf('E', 'E', 'E'),
            )
        )
    }
    TicTacToe(
        specs = TicTacToeSpecs(
            moveWidth = 4.dp,
            crossColor = Color.Red,
            roundColor = Color.Green,
            dividerWidth = 14f,
            dividerColor = Color.Black
        ),
        gameState = gameState,
        canMove = { gameState[it.first][it.second] == 'E' },
    ) { pos, move ->
        gameState = gameState.copyOf().apply {
            this[pos.first][pos.second] = move.short
        }
    }
}

@Composable
fun TicTacToe(
    modifier: Modifier = Modifier.size(300.dp),
    specs: TicTacToeSpecs,
    initialPlayer: Player = Player.X,
    gameState: Array<CharArray>,
    isGameRunning:Boolean = true,
    canMove: (Pair<Int, Int>) -> Boolean,
    onMove: (Pair<Int, Int>, Player) -> Unit,
) {
    val scope = rememberCoroutineScope()
    BoxWithConstraints(modifier = modifier) {
        val size = constraints.maxHeight.toFloat()
        val boxes = getBoxes(size, specs.dividerWidth)

        var currentPlayer by remember { mutableStateOf(initialPlayer) }
        var animatables = remember { emptyAnimatables() }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures {
                    findBox(boxes, it)?.let { pos ->
                        if (!isGameRunning || !canMove(pos)) return@detectTapGestures
                        onMove(pos, currentPlayer)
                        scope.animate(animatables[pos.first][pos.second])
                        currentPlayer = !currentPlayer
                    }
                }
            }
        ) {
            drawDividers(specs.dividerWidth, boxes, specs.dividerColor)
            gameState.forEachIndexed { row, moves ->
                moves.forEachIndexed { col, move ->
                    if (move != 'E') {
                        drawMove(boxes[row][col], specs, animatables[row][col].value, move)
                    }
                }
            }
        }


    }
}

private fun DrawScope.drawMove(
    box: Rect,
    specs: TicTacToeSpecs,
    percent: Float,
    move: Char
) {
    val innerBox = box.deflate(50f)
    when (move) {
        Player.X.short -> drawCross(innerBox, specs.crossColor, specs.moveWidth.toPx(), percent)
        Player.O.short -> drawRound(innerBox, specs.roundColor, specs.moveWidth.toPx(), percent)
    }
}

private fun DrawScope.drawCross(
    box: Rect,
    color: Color,
    width: Float,
    percent: Float,
) {
    val leftPath = Path().apply {
        moveTo(box.topLeft.x, box.topLeft.y)
        lineTo(box.bottomRight.x, box.bottomRight.y)
    }
    val rightPath = Path().apply {
        moveTo(box.bottomLeft.x, box.bottomLeft.y)
        lineTo(box.topRight.x, box.topRight.y)
    }
    val (leftOutPath, rightOutPath) = Path() to Path()
    PathMeasure().apply {
        setPath(leftPath, false)
        getSegment(0f, length * percent, leftOutPath)
    }
    PathMeasure().apply {
        setPath(rightPath, false)
        getSegment(0f, length * percent, rightOutPath)
    }
    drawPath(
        color = color,
        path = leftOutPath,
        style = Stroke(width),
    )
    drawPath(
        color = color,
        path = rightOutPath,
        style = Stroke(width),
    )
}

private fun DrawScope.drawRound(
    box: Rect,
    color: Color,
    width: Float,
    percent: Float,
) {
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 360 * percent,
        useCenter = false,
        style = Stroke(width),
        topLeft = box.topLeft,
        size = box.size
    )
}

private fun emptyAnimatables() = arrayOf(
    arrayOf(Animatable(0f), Animatable(0f), Animatable(0f)),
    arrayOf(Animatable(0f), Animatable(0f), Animatable(0f)),
    arrayOf(Animatable(0f), Animatable(0f), Animatable(0f))
)

fun CoroutineScope.animate(animatable: Animatable<Float, AnimationVector1D>) = launch {
    animatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(500, easing = LinearEasing)
    )
}


private fun findBox(boxes: List<List<Rect>>, pos: Offset): Pair<Int, Int>? {
    for (i in 0..2) {
        boxes[i].indexOfFirst { it.contains(pos) }.also {
            if (it != -1) return i to it
        }
    }
    return null
}

private fun getBoxes(size: Float, dividerWidth: Float): List<List<Rect>> {
    val boxes = mutableListOf<MutableList<Rect>>().apply {
        add(mutableListOf())
        add(mutableListOf())
        add(mutableListOf())
    }
    val boxSize = (size - 2 * dividerWidth) / 3f
    val sizeWithDivider = (size + dividerWidth) / 3f
    for (i in 0..2) {
        for (j in 0..2) {
            val boxTopLeftX = j * sizeWithDivider
            val boxTopLeftY = i * sizeWithDivider
            Rect(
                offset = Offset(boxTopLeftX, boxTopLeftY),
                size = Size(boxSize, boxSize)
            ).also {
                boxes[i].add(it)
            }
        }
    }
    return boxes
}

private fun DrawScope.drawDividers(
    width: Float,
    boxes: List<List<Rect>>,
    color: Color
) {
    drawDivider(boxes[0][0].topRight, boxes[2][0].bottomRight, width, color)
    drawDivider(boxes[0][1].topRight, boxes[2][1].bottomRight, width, color)
    drawDivider(boxes[0][0].bottomLeft, boxes[0][2].bottomRight, width, color)
    drawDivider(boxes[1][0].bottomLeft, boxes[1][2].bottomRight, width, color)
}

private fun DrawScope.drawDivider(
    lineStart: Offset,
    lineEnd: Offset,
    width: Float,
    color: Color
) {
    drawLine(
        color = color,
        start = lineStart,
        end = lineEnd,
        strokeWidth = width
    )
}