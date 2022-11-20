package dev.vaibhav.canvasCompose.ui.screens.tictactoe

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class Game {

    sealed class Player(val short: Char) {
        object X : Player('X')
        object O : Player('O')

        operator fun not(): Player = if (this is X) O else X
    }

    sealed interface Result{
        object PlayerXWon:Result
        object PlayerOWon:Result
        object Draw:Result
    }

    var gameState = MutableStateFlow(emptyGameState())

    private fun emptyGameState() = arrayOf(
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E'),
    )

    fun canMove(pos:Pair<Int, Int>) = gameState.value[pos.first][pos.second] == 'E'

    fun move(pos:Pair<Int, Int>, move:Player):Result? {
        gameState.update{
            it.copyOf().apply {
                this[pos.first][pos.second] = move.short
            }
        }
        return if(hasWon(player = move)) move.getWinner()
        else if(isBoardFull()) Result.Draw
        else null
    }

    fun resetState(){
        gameState.update { emptyGameState() }
    }

    private fun hasWon(player: Player):Boolean{
        gameState.value.forEach { row->
            if(row.all { it == player.short }) return true
        }
        for(column in 0..2){
            gameState.value.map { it[column] }.also { chars->
                if(chars.all { it == player.short }) return true
            }
        }
        gameState.value.mapIndexed { index, chars -> chars[index] }.also { chars->
            if(chars.all { it == player.short }) return true
        }
        gameState.value.mapIndexed { index, chars -> chars[2-index] }.also { chars->
            if(chars.all { it == player.short }) return true
        }
        return false
    }

    fun isBoardFull() = gameState.value.all { chars->
        chars.all { it!='E' }
    }

    fun isGameEmpty() = gameState.value.all { chars->
        chars.all { it =='E' }
    }

    private fun Player.getWinner() = when(this){
        Player.O -> Result.PlayerOWon
        Player.X -> Result.PlayerXWon
    }

}

