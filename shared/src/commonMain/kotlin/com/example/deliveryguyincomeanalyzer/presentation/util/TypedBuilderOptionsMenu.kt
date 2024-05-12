package com.example.deliveryguyincomeanalyzer.presentation.util

data class TypedBuilderOptionsMenu(
    var extraOptions : List<String>,
    val deliversOptions : List<String>
){

}

fun getOptionsMenu():TypedBuilderOptionsMenu{
    val extraMenu : MutableList<String> = mutableListOf("0")
    for (i in 1..300){
        extraMenu.add("${(i*5)}$")
    }

    val deliversMenu : MutableList<String> = mutableListOf()
    for (i in 1..300){
        deliversMenu.add("$i")
    }

    return TypedBuilderOptionsMenu(extraMenu,deliversMenu)

}