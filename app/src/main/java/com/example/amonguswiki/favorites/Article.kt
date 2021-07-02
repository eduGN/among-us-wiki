package com.example.amonguswiki.favorites

import java.io.Serializable

open class Article( val name: String, var html: String, val img: String, var fav: Boolean=false): Serializable
