package com.pyne.crossword.board.themes

import java.util.*

/**
 * Created by matts on 27/05/17.
 */
class MultiTheme : IBoardTheme {

    val themes: HashSet<IBoardTheme> = hashSetOf()

    fun clearTheme() {
        themes.clear()
    }

    fun addTheme(theme: IBoardTheme) {
        themes.add(theme)
    }

    override fun getRandomWordCluePair(): Map.Entry<String, String> {
        return themes.elementAt(Random().nextInt(themes.size))
                .getRandomWordCluePair()
    }
}