package me.jitesh.android.news.view

import android.content.Context
import android.widget.ArrayAdapter
import me.jitesh.android.news.model.Country

class CountryListAdapter(context: Context, objects: List<Country>) :
    ArrayAdapter<Country>(context, android.R.layout.simple_spinner_item, objects)