package com.pyne.crossword.board.themes

import java.util.*
import kotlin.collections.HashMap

/**
 * Created by matts on 27/05/17.
 */
class SillyTheme: IBoardTheme{


    val sillyWordToClueMap: Map<String, String>
    val freeWordToClueMap: Map<String, String>

    init {
        sillyWordToClueMap = hashMapOf()
        sillyWordToClueMap["Abibliophovia"] = "The fear of runing out of reading material."
        sillyWordToClueMap["Absquatulate"] = "To leave or abscond with something."
        sillyWordToClueMap["Allegator"] = "Some who alleges."
        sillyWordToClueMap["Anencephalous"] = "Lacking a brain."
        sillyWordToClueMap["Bloviate"] = "To speak pompously or brag."
        sillyWordToClueMap["Batrachomyomachy"] = "Making a mountain out of a molehill."
        sillyWordToClueMap["Billingsgate"] = "Loud, raucous profanity."
        sillyWordToClueMap["Blunderbuss"] = "A gun with a flared muzzle or disorganized activity."
        sillyWordToClueMap["Borborygm"] = "A rumbling of the stomach."
        sillyWordToClueMap["Boustrophedon"] = "	A back and forth pattern."
        sillyWordToClueMap["Bowyang"] = "A strap that holds the pants legs in place."
        sillyWordToClueMap["Brouhaha"] = "An uproar."
        sillyWordToClueMap["Bumbershoot"] = "An umbrella."
        sillyWordToClueMap["Collop"] = "A slice of meat of fold of flab."
        sillyWordToClueMap["Callipygian"] = "Having an attractive rear end or nice buns."
        sillyWordToClueMap["Canoodle"] = "To hug and kiss."
        sillyWordToClueMap["Cantankerous"] = "Testy, grumpy."
        sillyWordToClueMap["Cockalorum"] = "A small, haughty man."
        sillyWordToClueMap["Cockamamie"] = "Absurd, outlandish."
        sillyWordToClueMap["Codswallop"] = "Nonsense, balderdash."
        sillyWordToClueMap["Collywobbles"] = "Butterflies in the stomach."
        sillyWordToClueMap["Comeuppance"] = "Just reward, just deserts."
        sillyWordToClueMap["Crapulence"] = "Discomfort from eating or drinking too much."
        sillyWordToClueMap["Crudivore"] = "An eater of raw food."
        sillyWordToClueMap["Discombobulate"] = "Something really great."
        sillyWordToClueMap["Doozy"] = "To confuse."
        sillyWordToClueMap["Donnybrook"] = "An melee, a riot."
        sillyWordToClueMap["Dudgeon"] = "A bad mood, a huff."
        sillyWordToClueMap["Ecdysiast"] = "An exotic dancer, a stripper."
        sillyWordToClueMap["Eructation"] = "A burp, belch."
        sillyWordToClueMap["Fard"] = "Face-paint, makeup."
        sillyWordToClueMap["Fartlek"] = "An athletic training regime."
        sillyWordToClueMap["Filibuster"] = "Refusal to give up the floor in a debate to prevent a vote."
        sillyWordToClueMap["Flibbertigibbet"] = "Nonsense, balderdash."
        sillyWordToClueMap["Flummox"] = "To exasperate."
        sillyWordToClueMap["Folderol"] = "Nonsense."
        sillyWordToClueMap["Formication"] = "The sense of ants crawling on your skin."
        sillyWordToClueMap["Furbelow"] = "A fringe or ruffle."
        sillyWordToClueMap["Furphy"] = "A portable water-container."
        sillyWordToClueMap["Gaberlunzie"] = "A wandering beggar."
        sillyWordToClueMap["Gastromancy"] = "Telling fortune from the rumblings of the stomach."
        sillyWordToClueMap["Gobbledygook"] = "Nonsense, balderdash."
        sillyWordToClueMap["Gobemouche"] = "A highly gullible person."
        sillyWordToClueMap["Gongoozle"] = "To stare at, kibitz."
        sillyWordToClueMap["Gonzo"] = "Far-out journalism."
        sillyWordToClueMap["Goombah"] = "An older friend who protects you."
        sillyWordToClueMap["Hoosegow"] = "A jail or prison."
        sillyWordToClueMap["Hootenanny"] = "A country or folk music get-together."
        sillyWordToClueMap["Jackanapes"] = "A rapscallion, hooligan."
        sillyWordToClueMap["Kerfuffle"] = "Nonsense, balderdash."
        sillyWordToClueMap["Hemidemisemiquaver"] = "A musical timing of 1/64."
        sillyWordToClueMap["Klutz"] = "An awkward, stupid person."
        sillyWordToClueMap["Fatuous"] = "Unconsciously foolish."
        sillyWordToClueMap["Firkin"] = "A quarter barrel or small cask."
        sillyWordToClueMap["Gaberlunzie"] = "A wandering beggar."
        sillyWordToClueMap["Godwottery"] = "Nonsense, balderdash."
        sillyWordToClueMap["Hobbledehoy"] = "An awkward or ill-mannered young boy."
        sillyWordToClueMap["Lickspittle"] = "A servile person, a toady."
        sillyWordToClueMap["Malarkey"] = "Nonsense, balderdash."
        sillyWordToClueMap["Nincompoop"] = "A foolish person."
        sillyWordToClueMap["Oocephalus"] = "An egghead."
        sillyWordToClueMap["Lagopodous"] = "Like a rabbit's foot."
        sillyWordToClueMap["Logorrhea"] = "Loquaciousness, talkativeness."
        sillyWordToClueMap["Lollygag"] = "To move slowly, fall behind."
        sillyWordToClueMap["Maverick"] = "A loner, someone outside the box."
        sillyWordToClueMap["Mollycoddle"] = "To treat too leniently."
        sillyWordToClueMap["Mugwump"] = "An independent politician who does not follow any party."
        sillyWordToClueMap["Ornery"] = "Mean, nasty, grumpy."
        sillyWordToClueMap["Pandiculation"] = "A full body stretch."
        sillyWordToClueMap["Panjandrum"] = "Someone who thinks himself high and mighty."
        sillyWordToClueMap["Pettifogger"] = "A person who tries to befuddle others with his speech."
        sillyWordToClueMap["Quean"] = "A disreputable woman."
        sillyWordToClueMap["Rambunctious"] = "Aggressive, hard to control."
        sillyWordToClueMap["Ranivorous"] = "Frog-eating"
        sillyWordToClueMap["Rigmarole"] = "Nonsense, unnecessary complexity."
        sillyWordToClueMap["Shenanigan"] = "A prank, mischief."
        sillyWordToClueMap["Sialoquent"] = "Spitting while speaking."
        sillyWordToClueMap["Skedaddle"] = "To hurry somewhere."
        sillyWordToClueMap["Skullduggery"] = "No good, underhanded dealing."
        sillyWordToClueMap["Slangwhanger"] = "A loud abusive speaker or obnoxious writer."
        sillyWordToClueMap["Smellfungus"] = "A perpetual pessimist."
        sillyWordToClueMap["Snickersnee"] = "A long knife."
        sillyWordToClueMap["Snollygoster"] = "A person who can't be trusted."
        sillyWordToClueMap["Snool"] = "A servile person."
        sillyWordToClueMap["Tatterdemalion"] = "A child in rags."
        sillyWordToClueMap["Troglodyte"] = "Someone or something that lives in a cave."
        sillyWordToClueMap["Turdiform"] = "Having the form of a lark."
        sillyWordToClueMap["Unremacadamized"] = "Having not been repaved with macadam."
        sillyWordToClueMap["Vomitory"] = "An exit or outlet."
        sillyWordToClueMap["Wabbit"] = "Exhausted, tired, worn out."
        sillyWordToClueMap["Widdershins"] = "In a contrary or counterclockwise direction."
        sillyWordToClueMap["Yahoo"] = "A rube, a country bumpkin."

        freeWordToClueMap = HashMap(sillyWordToClueMap)
    }

    override fun getRandomWordCluePair(): Map.Entry<String, String> {
        return freeWordToClueMap.entries.elementAt(Random().nextInt(freeWordToClueMap.size))
    }

    override fun putWordBack(wordInfo: Map.Entry<String, String>) {
        freeWordToClueMap.plus(wordInfo.toPair())
    }
}