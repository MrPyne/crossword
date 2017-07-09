package com.pyne.crossword.board

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.pyne.crossword.CrossWordApplication
import com.pyne.crossword.board.themes.IBoardTheme
import com.pyne.crossword.board.themes.IThemeFactory
import com.pyne.crossword.board.themes.LocalThemeFactory
import com.pyne.crossword.board.themes.SillyTheme

/**
 * Created by matts on 27/05/17.
 */
class Board : Disposable, GestureDetector.GestureListener {

    var theme: IBoardTheme = SillyTheme()
    var rows: Int = 20
    var cols: Int = 20
    var difficulty: Int = 0
    val wordPieces: ArrayList<WordPiece> = arrayListOf()
    val size: Vector2 = Vector2()
    var cellSize: Vector2 = Vector2()

    val positionsToCells: HashMap<Vector2, HashSet<CharacterCell>> = hashMapOf()
    var bigFont: BitmapFont = BitmapFont()
    var smallFont: BitmapFont = BitmapFont()

    init {
        setSize(500f, 500f)
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return true
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean {
        return true
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return true
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {

        val position = Vector2(MathUtils.floor(x / cellSize.x).toFloat(),
                MathUtils.floor(y / cellSize.y).toFloat())

        if (positionIsOnBoard(position)) {

            val charCells = positionsToCells[position]
            val firstCharCell = charCells?.first()

            firstCharCell?.cycleChar()

            if (firstCharCell != null) {
                /**
                 * Note: Since some cells share the same board location
                 * you must update the others to match.
                 */
                charCells?.forEach { updateSharedCellsChars(it, firstCharCell) }

                var completedWords = arrayListOf<WordPiece>()
                charCells?.forEach { findCompletedWords(it, completedWords) }

                completedWords.forEach { lockAllCellsFor(it) }

                return true
            }
        }

        return false
    }

    private fun lockAllCellsFor(it: WordPiece) {
        it.cells.forEach {
            getSharedCells(it)?.forEach { it.cycleLock = true }
        }
    }

    private fun getSharedCells(it: CharacterCell) = positionsToCells[it.boardPosition]

    private fun findCompletedWords(charCell: CharacterCell, completedWords: MutableList<WordPiece>) {
        if (charCell.parentWord?.checkIfComplete()!!) {
            charCell.parentWord?.complete = true
            completedWords.add(charCell.parentWord!!)
        }
    }

    private fun updateSharedCellsChars(charCell: CharacterCell, firstCharCell: CharacterCell) {
        charCell.drawnCharacter = firstCharCell.drawnCharacter
        charCell.redrawTexture()
    }

    fun draw(batch: SpriteBatch) {
        wordPieces.forEach { it.draw(batch, this) }
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

        while (true) {
            if (triesToFitNewWord > 100) return false
            wordInfo = theme.getRandomWordCluePair()
            wordPiece = WordPiece(wordInfo.key, wordInfo.value)
            if (canFitWordPiece(wordPiece)) {
                break
            } else {
                theme.putWordBack(wordInfo)
            }
            triesToFitNewWord++
        }

        wordPiece.wordNumber = wordPieces.size + 1
        wordPiece.generateCells(this)
        wordPieces.add(wordPiece)

        println("Adding : " + wordPiece)

        return true
    }

    fun addWord(wordPiece: WordPiece): Boolean {

        if (!canFitWordPiece(wordPiece)) return false

        wordPiece.generateCells(this)
        wordPieces.add(wordPiece)

        println("Adding : " + wordPiece)

        return true
    }

    fun addWord(wordPiece: WordPiece, xPos: Int, yPos: Int, vertical: Boolean): Boolean {

        wordPiece.orientationVertical = vertical
        wordPiece.boardStartY = yPos
        wordPiece.boardStartX = xPos

        wordPiece.generateCells(this)
        wordPieces.add(wordPiece)

        println("Adding : " + wordPiece)

        return true
    }


    fun canFitWordPiece(wordPiece: WordPiece): Boolean {
        if (wordPieces.size == 0) {
            wordPiece.boardStartX = 0
            wordPiece.boardStartY = rows / 2
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

    fun positionIsOnBoard(position: Vector2): Boolean = position.x in 0..cols && position.y in 0..rows

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

    override fun dispose() {
        wordPieces.forEach { it.dispose() }
    }

    class Builder {
        val board: Board = Board()
        var numWords: Int = 0
        var difficulty: Int = 0
        var themeFactory: IThemeFactory = LocalThemeFactory()
        val themes: ArrayList<String> = arrayListOf("Silly")
        var cols = 20
        var rows = 20
        var size: Vector2 = Vector2()

        fun withCols(cols: Int): Builder {
            this.cols = cols
            return this
        }

        fun withRows(rows: Int): Builder {
            this.rows = rows
            return this
        }

        fun withSize(size: Vector2): Builder {
            this.size = size
            return this
        }

        fun withThemeFactory(themeFactory: IThemeFactory): Builder {
            this.themeFactory = themeFactory
            return this
        }

        fun withThemes(vararg themes: String): Builder {
            this.themes.addAll(themes)
            return this
        }

        fun withNumberOfWords(wordNumber: Int): Builder {
            this.numWords = wordNumber
            return this
        }

        fun withDifficulty(difficulty: Int): Builder {
            this.difficulty = difficulty
            return this
        }

        fun buildBoard(): Board {
            val theme: IBoardTheme = themeFactory.create(themes)

            board.theme = theme
            board.difficulty = difficulty
            board.cols = cols
            board.rows = rows
            board.setSize(size.x, size.y)

            var fontBig: BitmapFont? = null
            var fontSmall: BitmapFont? = null
            var fontSizeBig = 20
            var fontSizeSmall = 14

            if (board.cellSize.x > 40) {
                fontSizeBig = 25
                fontSizeSmall = 14
            }
            if (board.cellSize.x > 60) {
                fontSizeBig = 46
                fontSizeSmall = 30
            }
            if (board.cellSize.x > 100) {
                fontSizeBig = 80
                fontSizeSmall = 46
            }

            fontBig = CrossWordApplication.instance
                    .fontGen
                    .createFont(Gdx.files
                            .internal("expresswayrg.ttf"), "word-normal", fontSizeBig)

            fontSmall = CrossWordApplication.instance
                    .fontGen
                    .createFont(Gdx.files
                            .internal("expresswayrg.ttf"), "word-normal", fontSizeSmall)

            board.smallFont = fontSmall
            board.bigFont = fontBig

            addPiecesToBoard()

//            board.addWord(WordPiece("hello2", "greeting"))
//            board.addWord(WordPiece("hello21", "greeting"))
//            board.addWord(WordPiece("hello22", "greeting"))
//            board.addWord(WordPiece("233", "greeting"))
//            board.addWord(WordPiece("31114", "greeting"))
//            board.addWord(WordPiece("3111", "greeting"))
//            board.addWord(WordPiece("em", "greeting"))
//            board.addWord(WordPiece("1win", "greeting"))

            cacheCellsToPosition()

            return board
        }

        private fun addPiecesToBoard() {
            for (wordIndex in 0..numWords) {
                board.addPieceToBoard()
            }
        }

        private fun cacheCellsToPosition() {
            for (wordPiece in board.wordPieces) {
                for (cell in wordPiece.cells) {

                    var cellsAtPosition = board.positionsToCells[cell.boardPosition]
                    if (null == cellsAtPosition) {
                        cellsAtPosition = hashSetOf()
                        board.positionsToCells.put(cell.boardPosition, cellsAtPosition)
                    }
                    cellsAtPosition.add(cell)

                }
            }
        }
    }

}