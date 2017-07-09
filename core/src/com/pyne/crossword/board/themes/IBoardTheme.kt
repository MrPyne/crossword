package com.pyne.crossword.board.themes

/**
 * Created by matts on 27/05/17.
 */
interface IBoardTheme {
    fun getRandomWordCluePair(): Map.Entry<String, String>
    fun putWordBack(wordInfo: Map.Entry<String, String>)
}