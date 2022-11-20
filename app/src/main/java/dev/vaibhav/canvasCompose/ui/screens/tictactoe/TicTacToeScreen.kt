package dev.vaibhav.canvasCompose.ui.screens.tictactoe

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TicTacToeScreen(
    modifier: Modifier
) {
    val game = remember { Game() }
    val gameState by game.gameState.collectAsState()
    val scope = rememberCoroutineScope()
    var result by remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TicTacToe(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f),
            specs = TicTacToeSpecs(
                moveWidth = 4.dp,
                crossColor = Color.Red,
                roundColor = Color.Green,
                dividerWidth = 14f,
                dividerColor = Color.Black
            ),
            gameState = gameState,
            canMove = game::canMove,
            isGameRunning = result == null,
            onMove = { pos, move ->
                game.move(pos, move)?.let {
                    result = getResultString(it)
                    scope.launch {
                        delay(5000L)
                        game.resetState()
                        result = null
                    }
                }
            }
        )

        result?.let {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

}

private fun getResultString(result: Game.Result) = when (result) {
    Game.Result.Draw -> "Game Draw"
    Game.Result.PlayerOWon -> "Player O Won"
    Game.Result.PlayerXWon -> "Player X Won"
}

