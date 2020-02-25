package com.example.films.utils

fun formatPlaytime(playtime: Int) : String{
    val builder = StringBuilder()

    val days = playtime / (24 * 60)
    val hours = (playtime - (days * 24 * 60)) / 60
    val minutes = playtime  - (days * 24 * 60) - (hours * 60)

    if(days > 0){
        builder.append(days)
        builder.append("d")
    }
    if(hours > 0){
        if(builder.isNotEmpty()) {
            builder.append(" ")
        }
        builder.append(hours)
        builder.append("h")
    }
    if(minutes > 0){
        if(builder.isNotEmpty()) {
            builder.append(" ")
        }
        builder.append(minutes)
        builder.append("m")
    }
    return builder.toString()
}