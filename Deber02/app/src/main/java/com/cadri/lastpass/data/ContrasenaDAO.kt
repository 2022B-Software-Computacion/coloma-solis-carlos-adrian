package com.cadri.lastpass.data

import android.util.Log

object ContrasenaDAO {
    private val contrasenas = mutableListOf<Contrasena>()

    init{
        contrasenas.apply {
            add(Contrasena("Facebook", "adrian", "123456", "https://www.facebook.com"))
            add(Contrasena("Instagram", "adrian", "123456", "https://www.instagram.com"))
            add(Contrasena("Twitter", "adrian", "123456", "https://www.twitter.com"))
        }
    }
    fun addContrasena(contrasena: Contrasena) {
        contrasenas.add(contrasena)
    }

    fun getContrasenas(): List<Contrasena> {

        return contrasenas
    }
}