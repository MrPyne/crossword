package com.pyne.crossword.board

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.pyne.crossword.board.themes.IBoardTheme
import com.pyne.crossword.board.themes.IThemeFactory
import com.pyne.crossword.board.themes.LocalThemeFactory
import com.pyne.crossword.board.themes.SillyTheme

/**
 * Created by matts on 27/05/17.
 */
class Board {

    var theme: IBoardTheme = SillyTheme()
    val rows: Int = 20
    val cols: Int = 20
    val wordPieces: ArrayList<WordPiece> = arrayListOf()
    val size: Vector2 = Vector2()
    var cellSize: Vector2 = Vector2()

    init {
        setSize(500f, 500f)
    }

    fun draw(batch: SpriteBatch) {
        wordPieces.forEach { it.draw(batch) }
    }

    fun setSize(x: Float, y: Float) {
        size.x = x
        size.y = y
        cellSize.x = size.x / cols
        cellSize.y = size.y / rows
    }

    fun addPieceToBoard(): Boolean {

        var wordInfo: Map.Entry<String, String>
        var wordPiece: WordPiece
        var triesToFitNewWord: Int = 0
        do {
            if (triesToFitNewWord > 100) return false
            wordInfo = theme.getRandomWordCluePair()
            wordPiece = WordPiece(wordInfo.key, wordInfo.value)
            triesToFitNewWord++
        } while (!canFitWordPiece(wordPiece))

        wordPiece.cellYSize = cellSize.y
        wordPiece.cellXSize = cellSize.x
        wordPieces.add(wordPiece)

        return true
    }


    fun canFitWordPiece(wordPiece: WordPiece): Boolean {
        if (wordPieces.size == 0) {
            wordPiece.boardStartX = 10
            wordPiece.boardStartY = 10
            return true
        }

        wordPieces.forEach { thisWordPiece ->
            run {

                if (thisWordPiece.connectWord(wordPiece)
                        && wordIsOnBoard(wordPiece)
                        && noWordStrLike(wordPiece)
                        && noBadCollisions(wordPiece)
                        ) {
                    return true
                }

            }
        }

        return false
    }

    fun wordIsOnBoard(wordPiece: WordPiece): Boolean {
        return wordPiece.boardStartX > 0 && wordPiece.boardEndX() < cols
                && wordPiece.boardStartY > 0 && wordPiece.boardEndY() < rows
    }

    fun noWordStrLike(wordPiece: WordPiece): Boolean {
        return !wordPieces.any { thisWordPiece -> thisWordPiece.wordStr.equals(wordPiece.wordStr) }
    }

    fun noBadCollisions(wordPiece: WordPiece): Boolean {
        return !wordPieces.any { thisWordPiece -> thisWordPiece.collidesBadlyWith(wordPiece) }
    }

    class BoardBuilder {
        val board: Board = Board()
        var wordNumber: Int = 0
        var themeFactory: IThemeFactory = LocalThemeFactory()
        val themes: ArrayList<String> = arrayListOf("Silly")

        fun withSize(size: Vector2): BoardBuilder {
            board.setSize(size.x, size.y)
            return this
        }

        fun withThemeFactory(themeFactory: IThemeFactory): BoardBuilder {
            this.themeFactory = themeFactory
            return this
        }

        fun withThemes(vararg themes: String): BoardBuilder {
            this.themes.addAll(themes)
            return this
        }

        fun withNumberOfWords(wordNumber: Int): BoardBuilder {
            this.wordNumber = wordNumber
            return this
        }

        fun buildBoard(): Board {
            val theme: IBoardTheme = themeFactory.create(themes)

            board.theme = theme

            for (wordIndex in 1..wordNumber) {
                board.addPieceToBoard()
            }

            return board
        }

    }
}