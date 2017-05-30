package com.pyne.crossword.board.themes

import java.util.*

/**
 * Created by matts on 27/05/17.
 */
class SillyTheme: IBoardTheme{

    val sillyWordToClueMap: Map<String, String>

    init {
        sillyWordToClueMap = hashMapOf()
        sillyWordToClueMap["Abibliophovia"] = "The fear of runing out of reading material."
        sillyWordToClueMap["Bloviate"] = "To speak pompously or brag."
        sillyWordToClueMap["Collop"] = "A slice of meat of fold of flab."
        sillyWordToClueMap["Doozy"] = "Something really great."
        sillyWordToClueMap["Fatuous"] = "Unconsciously foolish."
        sillyWordToClueMap["Firkin"] = "A quarter barrel or small cask."
        sillyWordToClueMap["Gaberlunzie"] = "A wandering beggar."
        sillyWordToClueMap["Godwottery"] = "Nonsense, balderdash."
        sillyWordToClueMap["Hobbledehoy"] = "An awkward or ill-mannered young boy."
        sillyWordToClueMap["Lickspittle"] = "A servile person, a toady."
        sillyWordToClueMap["Malarkey"] = "Nonsense, balderdash."
        sillyWordToClueMap["Nincompoop"] = "A foolish person."
        sillyWordToClueMap["Oocephalus"] = "An egghead."
    }

    override fun getRandomWordCluePair(): Map.Entry<String, String> {
        return sillyWordToClueMap.entries.elementAt(Random().nextInt(sillyWordToClueMap.size))
    }

}