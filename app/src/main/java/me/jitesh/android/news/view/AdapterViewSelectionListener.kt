package me.jitesh.android.news.view

import android.view.View
import android.widget.AdapterView

class AdapterViewSelectionListener(
    private val onSelected: (parent: AdapterView<*>?, position: Int?) -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onSelected.invoke(parent, position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelected.invoke(parent, null)
    }
}