package com.example.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.TicTacToeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeAppTheme {
                TicTacToeScreen()
            }

        }
    }
}

@Composable
fun TicTacToeScreen() {
    val context = LocalContext.current
    var currentPlayer by remember { mutableStateOf("X") }
    val boardState = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }

    fun checkWinner(): String? {
        val lines = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
            arrayOf(0, 4, 8), arrayOf(2, 4, 6)
        )
        for (line in lines) {
            val (a, b, c) = line
            if (boardState[a].isNotEmpty() && boardState[a] == boardState[b] && boardState[a] == boardState[c]) {
                return boardState[a]
            }
        }
        if (boardState.none { it.isEmpty() }) return "Draw"
        return null
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Current Player: $currentPlayer",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )

        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Button(
                        onClick = {
                            if (boardState[index] == "" && checkWinner() == null) {
                                boardState[index] = currentPlayer
                                val winner = checkWinner()
                                if (winner != null) {
                                    val message = if (winner == "Draw") "It's a Draw!" else "Player $winner Wins!"
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                } else {
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            }
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    ) {
                        Text(text = boardState[index], style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }

        Button(
            onClick = {
                for (i in boardState.indices) boardState[i] = ""
                currentPlayer = "X"
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Play Again")
        }
    }
}

