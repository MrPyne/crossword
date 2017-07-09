package com.pyne.crossword.board

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import java.util.*


/**
 * Created by matts on 27/05/17.
 */
class WordPiece(wordStr: String = "", wordClue: String = "") : Disposable {

    var wordStr: String = wordStr
    var wordClue: String = wordClue
    var wordNumber: Int = 0
    var orientationVertical: Boolean = false
    var boardStartX: Int = 0
    var boardStartY: Int = 0
    var cells: ArrayList<CharacterCell> = arrayListOf()
    var complete: Boolean = false

    fun checkIfComplete(): Boolean {
        return cells.all { cell -> cell.isCorrect() }
    }

    fun connectWord(wordPiece: WordPiece): Boolean {
        val wordConnectAt: ArrayList<Pair<Int, Int>> = arrayListOf()

        for (i in 0..wordStr.length - 1) {
            for (j in 0..wordPiece.wordStr.length - 1) {
                if (!(i == 0 && j == 0) && wordStr[i].equals(wordPiece.wordStr[j])) {
                    wordConnectAt.add(Pair(i, j))
                }
            }
        }

        if (!wordConnectAt.isEmpty()) {
            wordPiece.orientationVertical = !orientationVertical
            val connection: Pair<Int, Int> = wordConnectAt[Random().nextInt(wordConnectAt.size)]
            if (wordPiece.orientationVertical) {
                wordPiece.boardStartX = boardStartX + connection.first
                wordPiece.boardStartY = boardStartY - connection.second
            } else {
                wordPiece.boardStartY = boardStartY + connection.first
                wordPiece.boardStartX = boardStartX - connection.second
            }

            return true
        }

        return false
    }

    fun collidesBadlyWith(wordPiece: WordPiece): Boolean {

        for (charIndex in 0..wordPiece.wordStr.length - 1) {
            for (thisCharIndex in 0..wordStr.length - 1) {
                if (sharesBoardPosition(wordPiece, charIndex, thisCharIndex)
                        && (!hasSameCharAt(wordPiece, charIndex, thisCharIndex)
                        || sameInitialBoardPosition(wordPiece))) {
                    return true
                }
            }
        }

        return false
    }

    fun sameInitialBoardPosition(wordPiece: WordPiece): Boolean {
        return boardStartX == wordPiece.boardStartX && boardStartY == wordPiece.boardStartY
    }

    private fun hasSameCharAt(wordPiece: WordPiece, charIndex: Int, thisCharIndex: Int) = wordPiece.wordStr[charIndex].equals(wordStr[thisCharIndex])

    private fun sharesBoardPosition(wordPiece: WordPiece, charIndex: Int, thisCharIndex: Int) = wordPiece.boardPositionOf(charIndex).equals(boardPositionOf(thisCharIndex))

    fun boardYOf(index: Int): Int {
        if (orientationVertical) {
            return boardStartY + index
        }
        return boardStartY
    }

    fun boardXOf(index: Int): Int {
        if (orientationVertical) {
            return boardStartX
        }
        return boardStartX + index
    }

    fun boardPositionOf(index: Int): Pair<Int, Int> {
        return Pair(boardXOf(index), boardYOf(index))
    }

    fun boardEndX(): Int {
        if (orientationVertical) {
            return boardStartX
        }
        return boardStartX + wordStr.length - 1
    }

    fun boardEndY(): Int {
        if (orientationVertical) {
            return boardStartY + wordStr.length - 1
        }
        return boardStartY
    }

    fun generateCells(board: Board) {
        cells = arrayListOf()
        for (i in 0..wordStr.length - 1) {

            cells.add(CharacterCell.Builder()
                    .withCellSize(board.cellSize)
                    .withBoardX(boardXOf(i))
                    .withBoardY(boardYOf(i))
                    .withCharacterIndex(i)
                    .withWordNumber(wordNumber)
                    .withCharacter(wordStr[i])
                    .withNumCharsToChoose(board.difficulty)
                    .withBigFont(board.bigFont)
                    .withSmallFont(board.smallFont)
                    .build())

            cells.forEach { it.parentWord = this }
        }
    }

    fun draw(batch: SpriteBatch, board: Board) {
        var charCountDrawn: Int = 1
        cells.forEach { cell ->
            kotlin.run {
                var xPos = 0f
                var yPos = cell.size.y * (cells.size - charCountDrawn)
                if (!orientationVertical) {
                    xPos = cell.size.x * (charCountDrawn - 1)
                    yPos = 0f
                }
                batch.draw(cell.texture,
                        xPos + (boardStartX * board.cellSize.x),
                        yPos + (board.cols - boardEndY() - 1) * board.cellSize.y)
                charCountDrawn++
            }
        }
    }

    override fun toString(): String {
        return "WordPiece(wordStr='$wordStr'" +
                ", wordClue='$wordClue'" +
                ", orientationVertical=$orientationVertical" +
                ", boardStartX=$boardStartX" +
                ", boardStartY=$boardStartY" +
                ", boardEndX=${boardEndX()}" +
                ", boardEndY=${boardEndY()}" +
                ", cells=${cells.size})"
    }

    override fun dispose() {
        cells.forEach { it.dispose() }
    }

}