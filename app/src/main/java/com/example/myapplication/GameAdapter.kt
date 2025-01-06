package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.GameItemBinding

class GameAdapter(private var list: MutableList<Item>): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    var onGameResult: ((String) -> Unit)? = null
    var onButtonClicked: ((Pair<Int, String>) -> Unit)? = null

    private var xPlayerMove = true
    private var oPlayerMove = false
    private var gameEnded = false
    private var moveCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = list[position]
        holder.onBind(item)
    }

    inner class GameViewHolder(private val binding: GameItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Item) {
            binding.button.text = item.text
            binding.button.setOnClickListener {
                // if game is finished, stop listener
                if (gameEnded) return@setOnClickListener

                // apply X/O only if that button is empty
                if (binding.button.text == "") {
                    if (xPlayerMove) {
                        // pass id and text value to GameFragment to update the list
                        onButtonClicked?.invoke(Pair(item.id, "X"))
                        binding.button.text = "X"
                        changePlayersTurn()
                        moveCount++
                    }
                    else {
                        // pass id and text value to GameFragment to update the list
                        onButtonClicked?.invoke(Pair(item.id, "O"))
                        binding.button.text = "O"
                        changePlayersTurn()
                        moveCount++
                    }
                }

                // check for either player win
                if (checkForWin("X")) {
                    onGameResult?.invoke("X")
                    gameEnded = true
                }
                if(checkForWin("O")) {
                    onGameResult?.invoke("O")
                    gameEnded = true
                }

                // check for draw according to move count and list size to determine game level
                when(list.size) {
                    9 -> {
                        if (moveCount == 9 && !checkForWin("X") && !checkForWin("O")) {
                            onGameResult?.invoke("draw")
                            gameEnded = true
                        }
                    }
                    16 -> {
                        if (moveCount == 16 && !checkForWin("X") && !checkForWin("O")) {
                            onGameResult?.invoke("draw")
                            gameEnded = true
                        }
                    }
                    25 -> {
                        if (moveCount == 25 && !checkForWin("X") && !checkForWin("O")) {
                            onGameResult?.invoke("draw")
                            gameEnded = true
                        }
                    }
                }
            }

        }
    }

    private fun changePlayersTurn() {
        if (xPlayerMove) {
            xPlayerMove = false
            oPlayerMove = true
        }
        else {
            xPlayerMove = true
            oPlayerMove = false
        }
    }

    // function for checking win, rows/columns/diagonals variables are created using button IDs and positions
    private fun checkForWin(player: String) : Boolean{
        when(list.size) {
            9 -> {
                val rows = listOf(
                    listOf(1,2,3),
                    listOf(4,5,6),
                    listOf(7,8,9)
                )
                val columns = listOf(
                    listOf(1,4,7),
                    listOf(2,5,8),
                    listOf(3,6,9)
                )
                val diagonals = listOf(
                    listOf(1,5,9),
                    listOf(3,5,7)
                )

                for (row in rows) {
                    if (list.find { it.id == row[0] }?.text == player &&
                        list.find { it.id == row[1] }?.text == player &&
                        list.find { it.id == row[2] }?.text == player) {
                        return true
                    }
                }

                for (column in columns) {
                    if (list.find { it.id == column[0] }?.text == player &&
                        list.find { it.id == column[1] }?.text == player &&
                        list.find { it.id == column[2] }?.text == player) {
                        return true
                    }
                }

                for (diagonal in diagonals) {
                    if (list.find { it.id == diagonal[0] }?.text == player &&
                        list.find { it.id == diagonal[1] }?.text == player &&
                        list.find { it.id == diagonal[2] }?.text == player) {
                        return true
                    }
                }

                return false
            }
            16 -> {
                val rows = listOf(
                    listOf(1, 2, 3, 4),
                    listOf(5, 6, 7, 8),
                    listOf(9, 10, 11, 12),
                    listOf(13, 14, 15, 16)
                )
                val columns = listOf(
                    listOf(1, 5, 9, 13),
                    listOf(2, 6, 10, 14),
                    listOf(3, 7, 11, 15),
                    listOf(4, 8, 12, 16)
                )
                val diagonals = listOf(
                    listOf(1, 6, 11, 16),
                    listOf(4, 7, 10, 13)
                )

                for (row in rows) {
                    if (list.find { it.id == row[0] }?.text == player &&
                        list.find { it.id == row[1] }?.text == player &&
                        list.find { it.id == row[2] }?.text == player &&
                        list.find { it.id == row[3] }?.text == player) {
                        return true
                    }
                }

                for (column in columns) {
                    if (list.find { it.id == column[0] }?.text == player &&
                        list.find { it.id == column[1] }?.text == player &&
                        list.find { it.id == column[2] }?.text == player &&
                        list.find { it.id == column[3] }?.text == player) {
                        return true
                    }
                }

                for (diagonal in diagonals) {
                    if (list.find { it.id == diagonal[0] }?.text == player &&
                        list.find { it.id == diagonal[1] }?.text == player &&
                        list.find { it.id == diagonal[2] }?.text == player &&
                        list.find { it.id == diagonal[3] }?.text == player) {
                        return true
                    }
                }

                return false
            }
            25 -> {
                val rows = listOf(
                    listOf(1, 2, 3, 4, 5),
                    listOf(6, 7, 8, 9, 10),
                    listOf(11, 12, 13, 14, 15),
                    listOf(16, 17, 18, 19, 20),
                    listOf(21, 22, 23, 24, 25)
                )
                val columns = listOf(
                    listOf(1, 6, 11, 16, 21),
                    listOf(2, 7, 12, 17, 22),
                    listOf(3, 8, 13, 18, 23),
                    listOf(4, 9, 14, 19, 24),
                    listOf(5, 10, 15, 20, 25)
                )
                val diagonals = listOf(
                    listOf(1, 7, 13, 19, 25),
                    listOf(5, 9, 13, 17, 21)
                )

                for (row in rows) {
                    if (list.find { it.id == row[0] }?.text == player &&
                        list.find { it.id == row[1] }?.text == player &&
                        list.find { it.id == row[2] }?.text == player &&
                        list.find { it.id == row[3] }?.text == player &&
                        list.find { it.id == row[4] }?.text == player) {
                        return true
                    }
                }

                for (column in columns) {
                    if (list.find { it.id == column[0] }?.text == player &&
                        list.find { it.id == column[1] }?.text == player &&
                        list.find { it.id == column[2] }?.text == player &&
                        list.find { it.id == column[3] }?.text == player &&
                        list.find { it.id == column[4] }?.text == player) {
                        return true
                    }
                }

                for (diagonal in diagonals) {
                    if (list.find { it.id == diagonal[0] }?.text == player &&
                        list.find { it.id == diagonal[1] }?.text == player &&
                        list.find { it.id == diagonal[2] }?.text == player &&
                        list.find { it.id == diagonal[3] }?.text == player &&
                        list.find { it.id == diagonal[4] }?.text == player) {
                        return true
                    }
                }

                return false
            }
            else -> return false
        }
    }
}