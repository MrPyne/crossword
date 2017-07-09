package com.pyne.crossword.board.themes

import java.util.*

/**
 * Created by matts on 27/05/17.
 */
class MultiTheme : IBoardTheme {

    val themes: HashSet<IBoardTheme> = hashSetOf()
    private var lastUsedTheme: IBoardTheme = this

    fun clearTheme() {
        themes.clear()
    }

    fun addTheme(theme: IBoardTheme) {
        themes.add(theme)
    }

    override fun getRandomWordCluePair(): Map.Entry<String, String> {
        lastUsedTheme = themes.elementAt(Random().nextInt(themes.size))
        return lastUsedTheme.getRandomWordCluePair()
    }

    override fun putWordBack(wordInfo: Map.Entry<String, String>) {
        lastUsedTheme.putWordBack(wordInfo)
    }

}