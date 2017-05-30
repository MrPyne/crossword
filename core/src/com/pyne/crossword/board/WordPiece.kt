package com.pyne.crossword.board

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import java.util.*

/**
 * Created by matts on 27/05/17.
 */
class WordPiece(wordStr: String = "", wordClue: String = "") {

    var wordStr: String = wordStr
    var wordClue: String = wordClue
    var orientationVertical: Boolean = false
    var boardStartX: Int = 0
    var boardStartY: Int = 0
    var cellXSize: Float = 0f
    var cellYSize: Float = 0f

    val texture: Texture by lazy {
        createTexture()
    }


    fun createTexture(): Texture {
        val width: Int = when (orientationVertical) {
            true -> cellXSize.toInt()
            else -> (cellXSize * wordStr.length).toInt()
        }

        val height: Int = when (orientationVertical) {
            true -> (cellYSize * wordStr.length).toInt()
            else -> cellYSize.toInt()
        }

        val pixmap: Pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)

        val font: BitmapFont = BitmapFont()
        val data: BitmapFont.BitmapFontData = font.data
        val fontPixmap: Pixmap = Pixmap(Gdx.files.internal(data.imagePaths[0]))
        var charCountDrawn: Int = 1

        wordStr.forEach { char ->
            kotlin.run {
                val glyph: BitmapFont.Glyph = data.getGlyph(char.toLowerCase())
                if (orientationVertical) {
                    pixmap.drawPixmap(fontPixmap,
                            (cellXSize.toInt() - glyph.width) / 2,
                            (cellYSize.toInt() * charCountDrawn - glyph.height) / 2,
                            glyph.srcX, glyph.srcY, glyph.width, glyph.height)
                } else {
                    pixmap.drawPixmap(fontPixmap,
                            (cellXSize.toInt() * charCountDrawn - glyph.width) / 2,
                            (cellYSize.toInt() - glyph.height) / 2,
                            glyph.srcX, glyph.srcY, glyph.width, glyph.height)
                }
                charCountDrawn++
            }
        }
        val texture: Texture = Texture(pixmap);

//        val textureRegion: TextureRegion = TextureRegion(texture)
//        textureRegion.flip(false, true)

        return texture
    }


    fun connectWord(wordPiece: WordPiece): Boolean {
        val wordConnectAt: ArrayList<Pair<Int, Int>> = arrayListOf()

        for (i in 0..wordStr.length - 1) {
            for (j in 0..wordPiece.wordStr.length - 1) {
                if (wordStr[i].equals(wordPiece.wordStr[j])) {
                    wordConnectAt.add(Pair(i, j))
                }
            }
        }

        if (!wordConnectAt.isEmpty()) {
            wordPiece.orientationVertical = !orientationVertical
            val connection: Pair<Int, Int> = wordConnectAt[Random().nextInt(wordConnectAt.size)]
            if (orientationVertical) {
                wordPiece.boardStartX = boardStartX - connection.second
                wordPiece.boardStartY = connection.first + boardStartY
            } else {
                wordPiece.boardStartY = boardStartY - connection.second
                wordPiece.boardStartX = connection.first + boardStartX
            }
            return true
        }

        return false
    }

    fun collidesBadlyWith(wordPiece: WordPiece): Boolean {

        if (wordPiece.orientationVertical == orientationVertical) {
            if (wordPiece.boardStartX == boardStartX) {
                return !verticalBadCollisionCheck(wordPiece)
            }
        } else {
            if (wordPiece.boardStartY == boardStartY) {
                return !horizontalBadCollisionCheck(wordPiece)
            }
        }


        return false
    }

    private fun verticalBadCollisionCheck(wordPiece: WordPiece): Boolean {
        return (boardStartY..boardEndY())
                .intersect(wordPiece.boardStartY..wordPiece.boardEndY())
                .any { sharedLocY ->
                    !wordStr[sharedLocY - boardStartY]
                            .equals(wordPiece.wordStr[sharedLocY - wordPiece.boardStartY])
                }
    }

    private fun horizontalBadCollisionCheck(wordPiece: WordPiece): Boolean {
        return (boardStartX..boardEndX())
                .intersect(wordPiece.boardStartX..wordPiece.boardEndX())
                .any { sharedLocX ->
                    !wordStr[sharedLocX - boardStartX]
                            .equals(wordPiece.wordStr[sharedLocX - wordPiece.boardStartX])
                }
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

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, boardStartX * cellXSize, boardStartY * cellYSize)
    }
}