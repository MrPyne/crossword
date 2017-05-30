package com.pyne.crossword.board.themes

/**
 * Created by matts on 27/05/17.
 */
class LocalThemeFactory: IThemeFactory {

    override fun create(themeStrs: ArrayList<String>): IBoardTheme {
        val theme: MultiTheme = MultiTheme()

        themeStrs.forEach { themeStr -> theme.addTheme(findLocalTheme(themeStr)) }

        return theme
    }

    fun findLocalTheme(themeStr: String): IBoardTheme{
        return when(themeStr){
            "Silly" -> SillyTheme()
            else -> SillyTheme()
        }
    }

}