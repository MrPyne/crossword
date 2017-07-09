package com.pyne.crossword.board

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.pyne.crossword.CrossWordApplication
import java.io.File
import java.util.*

/**
 * Created by matts on 11/06/17.
 */
class CharacterCell : Disposable {

    var character: Char = ' '
    var drawnCharacter: Char = ' '
    var characterIndex: Int = 0
    var wordNumber: Int = 0
    var boardPosition: Vector2 = Vector2()
    var size: Vector2 = Vector2()
    private var bigFont: BitmapFont = BitmapFont()
    private var smallFont: BitmapFont = BitmapFont()
    var characterChoosies: ArrayList<Char> = arrayListOf()
    var numCharsToChoose: Int = 0
    var parentWord: WordPiece? = null
    var cycleLock: Boolean = false

    val texture: Texture by lazy {
        createTexture()
    }

    private fun createTexture(): Texture {
        val pixmap: Pixmap = createPixmapAndDraw()

        val texture: Texture = Texture(pixmap)
        pixmap.dispose()

        return texture
    }

    private fun createPixmapAndDraw(): Pixmap {
        val pixmap: Pixmap = Pixmap(size.x.toInt(), size.y.toInt(), Pixmap.Format.RGB888)
        pixmap.setColor(Color.WHITE)

        drawChar(pixmap)

        if (characterIndex == 0) {
            drawWordNumber(pixmap)
        }
        return pixmap
    }

    fun redrawTexture(){
        val pixmap: Pixmap = createPixmapAndDraw()

        texture.draw(pixmap, 0, 0)

        pixmap.dispose()
    }

    fun drawChar(pixmap: Pixmap) {
        var data: BitmapFont.BitmapFontData = bigFont.data
        val fontPixmap: Pixmap = Pixmap(Gdx.files.internal(data.getImagePath(0)))
        val glyph: BitmapFont.Glyph = data.getGlyph(drawnCharacter.toLowerCase())
        val xPos = size.x.toInt() * 8 / 10 - glyph.width
        val yPos = size.y.toInt() * 8 / 10 - glyph.height
        pixmap.drawPixmap(fontPixmap,
                xPos,
                yPos,
                glyph.srcX,
                glyph.srcY,
                glyph.width,
                glyph.height)

        pixmap.drawRectangle(2, 2, size.x.toInt() - 4, size.y.toInt() - 4)
        pixmap.drawRectangle(3, 3, size.x.toInt() - 6, size.y.toInt() - 6)
        pixmap.drawRectangle(4, 4, size.x.toInt() - 8, size.y.toInt() - 8)

        fontPixmap.dispose()
    }

    private fun drawWordNumber(pixmap: Pixmap) {
        val data: BitmapFont.BitmapFontData = smallFont.data
        val fontPixmap: Pixmap = Pixmap(Gdx.files.internal(data.getImagePath(0)))
        var xPos = 8
        var yPos = 8

        wordNumber.toString().toCharArray().forEach { char ->
            run {
                val glyph: BitmapFont.Glyph = data.getGlyph(char)

                pixmap.drawPixmap(fontPixmap,
                        xPos,
                        yPos,
                        glyph.srcX,
                        glyph.srcY,
                        glyph.width,
                        glyph.height)

                xPos += glyph.width
            }
        }
        fontPixmap.dispose()
    }

    fun cycleChar() {
        if(! parentWord?.complete!!
                && ! cycleLock) {
            val indexChoose = characterChoosies.indexOf(drawnCharacter)
            if (-1 == indexChoose || characterChoosies.size <= indexChoose + 1) {
                drawnCharacter = characterChoosies[0]
            } else {
                drawnCharacter = characterChoosies[indexChoose + 1]
            }
            redrawTexture()
        }
    }

    fun isCorrect() : Boolean {
        return drawnCharacter == character
    }

    override fun dispose() {
        texture.dispose()
    }

    class Builder {
        private var character: Char = ' '
        private var characterIndex: Int = 0
        private var wordNumber: Int = 0
        private var boardX: Int = 0
        private var boardY: Int = 0
        private var cellSize: Vector2 = Vector2()
        private var numCharsToChoose: Int = 0
        private var bigFont: BitmapFont = BitmapFont()
        private var smallFont: BitmapFont = BitmapFont()

        fun withCharacter(char: Char): Builder {
            this.character = char
            return this
        }

        fun withBigFont(bigFont: BitmapFont): Builder{
            this.bigFont = bigFont
            return this
        }

        fun withSmallFont(smallFont: BitmapFont): Builder{
            this.smallFont = smallFont
            return this
        }

        fun withCharacterIndex(characterIndex: Int): Builder {
            this.characterIndex = characterIndex
            return this
        }

        fun withBoardX(boardX: Int): Builder {
            this.boardX = boardX
            return this
        }

        fun withBoardY(boardY: Int): Builder {
            this.boardY = boardY
            return this
        }

        fun withCellSize(cellSize: Vector2): Builder {
            this.cellSize = cellSize
            return this
        }

        fun withWordNumber(wordNumber: Int): Builder {
            this.wordNumber = wordNumber
            return this
        }

        fun withNumCharsToChoose(numCharsToChoose: Int): Builder {
            this.numCharsToChoose = numCharsToChoose
            return this
        }

        fun build(): CharacterCell {
            val cell = CharacterCell()

            cell.boardPosition.x = boardX.toFloat()
            cell.boardPosition.y = boardY.toFloat()
            cell.bigFont = bigFont
            cell.smallFont = smallFont
            cell.size = cellSize
            cell.characterIndex = characterIndex
            cell.wordNumber = wordNumber
            cell.character = character
            cell.numCharsToChoose = numCharsToChoose

            populateRandomCharacterChoosies(cell)

            return cell
        }

        private fun populateRandomCharacterChoosies(cell: CharacterCell) {
            val chooses: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            cell.characterChoosies.add(character)
            for (i in 1..cell.numCharsToChoose) {

                var randomCharIndex = Random().nextInt(chooses.length - 1)
                var randomChooseChar: Char = chooses[randomCharIndex]
                while (cell.characterChoosies.contains(randomChooseChar)) {
                    randomCharIndex = Random().nextInt(chooses.length - 1)
                    randomChooseChar = chooses[randomCharIndex]
                }

                cell.characterChoosies.add(randomChooseChar)
            }
        }
    }

}