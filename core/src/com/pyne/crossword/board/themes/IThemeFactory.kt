package com.pyne.crossword.board.themes

/**
 * Created by matts on 27/05/17.
 */
interface IThemeFactory {
    fun create(themeStr: ArrayList<String>): IBoardTheme
}