package com.example.amonguswiki.colors

import com.example.amonguswiki.favorites.Article

class Color( name: String,  html: String,  img: String,  fav: Boolean=false):
    Article(name, html, img, fav)
         {

        companion object {


            fun generateColors(): List<Color> {
                val color1 = Color(
                    "Rojo",
                    "file:///android_asset/src/colors/rojo.html",
                    "https://firebasestorage.googleapis.com/v0/b/among-us-8a1d9.appspot.com/o/img%2Fcolors%2Frojo.png?alt=media&token=e301b0aa-1499-4fd3-b056-25de3532c686"
                )
                val color2 = Color(
                    "Azul",
                    "file:///android_asset/src/colors/azul.html",
                    "https://firebasestorage.googleapis.com/v0/b/among-us-8a1d9.appspot.com/o/img%2Fcolors%2Fazul.png?alt=media&token=72ca9f4b-6dcc-43e1-88f2-76b85b2472e5"
                )
                val color3 = Color(
                    "Verde",
                    "file:///android_asset/src/colors/verde.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/7/72/Green.png/revision/latest/scale-to-width-down/140?cb=20201013061349"
                )

                val color4 = Color(
                    "Rosa",
                    "file:///android_asset/src/colors/rosa.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/5/50/Pink.png/revision/latest/scale-to-width-down/140?cb=20201013061359"
                )

                val color5 = Color(
                    "Naranja",
                    "file:///android_asset/src/colors/naranja.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/4/43/Orange.png/revision/latest/scale-to-width-down/140?cb=20201013061422"
                )

                val color6 = Color(
                    "Amarillo",
                    "file:///android_asset/src/colors/amarillo.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/9/92/Yellow.png/revision/latest/scale-to-width-down/140?cb=20201013061436"
                )

                val color7 = Color(
                    "Negro",
                    "file:///android_asset/src/colors/negro.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/7/71/Black.png/revision/latest/scale-to-width-down/140?cb=20201013061451"
                )

                val color8 = Color(
                    "Blanco",
                    "file:///android_asset/src/colors/blanco.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/8/80/White.png/revision/latest/scale-to-width-down/140?cb=20201013061502"
                )

                val color9 = Color(
                    "Morado",
                    "file:///android_asset/src/colors/morado.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/3/31/Purple.png/revision/latest/scale-to-width-down/140?cb=20201013061517"
                )


                val color10 = Color(
                    "Marrón",
                    "file:///android_asset/src/colors/marron.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/0/06/Brown.png/revision/latest/scale-to-width-down/140?cb=20201013061535"
                )

                val color11 = Color(
                    "Cian",
                    "file:///android_asset/src/colors/cian.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/a/ab/Cyan.png/revision/latest/scale-to-width-down/140?cb=20201013061549"
                )

                val color12 = Color(
                    "Lima",
                    "file:///android_asset/src/colors/lima.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/3/34/Lime.png/revision/latest/scale-to-width-down/140?cb=20201013061604"
                )

                val color13 = Color(
                    "Verde Fuerte",
                    "file:///android_asset/src/colors/verdefuerte.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/2/22/Fortegreen.png/revision/latest/scale-to-width-down/148?cb=20201020202250"
                )

                val color14 = Color(
                    "Canela",
                    "file:///android_asset/src/colors/canela.html",
                    "https://static.wikia.nocookie.net/among-us-wiki/images/8/87/Tan.png/revision/latest/scale-to-width-down/148?cb=20201013062221"
                )

                return listOf(color1,color2,color3,color4,color5,color6,color7, color8, color9, color10, color11, color12, color13, color14)
            }
        }
    }
