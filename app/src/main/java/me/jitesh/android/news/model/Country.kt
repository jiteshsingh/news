package me.jitesh.android.news.model

data class Country(val name: String, val code: String) : Comparable<Country> {
    override fun compareTo(other: Country): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return name
    }
}