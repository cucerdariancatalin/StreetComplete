package de.westnordost.streetcomplete.quests.sidewalk

import androidx.appcompat.app.AlertDialog
import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.osm.sidewalk.Sidewalk
import de.westnordost.streetcomplete.osm.sidewalk.Sidewalk.NO
import de.westnordost.streetcomplete.osm.sidewalk.Sidewalk.SEPARATE
import de.westnordost.streetcomplete.osm.sidewalk.Sidewalk.YES
import de.westnordost.streetcomplete.osm.sidewalk.SidewalkSides
import de.westnordost.streetcomplete.quests.AStreetSideSelectFragment
import de.westnordost.streetcomplete.quests.AnswerItem
import de.westnordost.streetcomplete.quests.LastSelection

class AddSidewalkForm : AStreetSideSelectFragment<Sidewalk, SidewalkSides>() {

    override val items = arrayOf(YES, NO, SEPARATE).map { it.asStreetSideItem() }

    override val otherAnswers: List<AnswerItem> = listOf(
        AnswerItem(R.string.quest_sidewalk_answer_none) { noSidewalksHereHint() }
    )

    private fun noSidewalksHereHint() {
        activity?.let { AlertDialog.Builder(it)
            .setTitle(R.string.quest_sidewalk_answer_none_title)
            .setMessage(R.string.quest_side_select_interface_explanation)
            .setPositiveButton(android.R.string.ok, null)
            .show()
        }
    }

    override fun onClickOk(leftSide: Sidewalk?, rightSide: Sidewalk?) {
        applyAnswer(SidewalkSides(leftSide!!, rightSide!!))
    }

    override fun serializeAnswer(answer: LastSelection<Sidewalk>): String {
        return "${answer.left.value}#${answer.right.value}"
    }

    override fun deserializeAnswer(str: String): LastSelection<Sidewalk> {
        val arr = str.split('#')
        return LastSelection(
            items.find { it.value.toString() == arr[0] }!!,
            items.find { it.value.toString() == arr[1] }!!
        )
    }
}
