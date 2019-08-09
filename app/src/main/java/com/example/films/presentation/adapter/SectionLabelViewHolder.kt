package com.example.films.presentation.adapter

import android.view.View
import com.example.films.presentation.adapter.items.AdapterItem
import com.example.films.presentation.adapter.items.SectionLabelItem
import kotlinx.android.synthetic.main.row_section_label.view.*

class SectionLabelViewHolder(itemView: View) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val labelItem = item as SectionLabelItem
        itemView.textLabel.setText(labelItem.label)
    }
}
