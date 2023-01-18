package com.example.cacsapplication

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador.run{
                add(BEntrenador(1, "Adrian", "a@a.com"))
                add(BEntrenador(2, "Vicente", "b@b.com"))
                add(BEntrenador(3, "Carolina", "c@c.com"))
            }
        }
    }
}